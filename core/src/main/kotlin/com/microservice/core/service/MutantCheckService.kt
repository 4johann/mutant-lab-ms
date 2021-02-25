package com.microservice.core.service

import com.microservice.common.constant.Mutant.MIN_NUMBER_OF_MUTANT_SEQUENCES
import com.microservice.common.constant.Mutant.NUCLEOBASE_MUTANT_SEQUENCE
import com.microservice.common.dto.exception.MicroserviceException
import com.microservice.common.dto.request.IsMutantDnaRequest
import com.microservice.common.enum.Dna
import com.microservice.common.exception.ErrorCode.INVALID_DNA
import com.microservice.core.mapper.MutantCheckMapper
import com.microservice.core.util.CacheUtils
import com.microservice.domain.repository.DnaRepository
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.redis.core.StringRedisTemplate
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import java.sql.Timestamp
import java.time.LocalDateTime

@Service
class MutantCheckService {

    private val logger = LoggerFactory.getLogger(MutantCheckService::class.java)

    @Autowired
    private lateinit var dnaRepository: DnaRepository

    @Autowired
    private lateinit var stringRedisTemplate: StringRedisTemplate

    fun checkIfAHumanIsMutant(isMutantDnaRequest: IsMutantDnaRequest): HttpStatus {
        val dnaMatrix = getDnaMatrix(isMutantDnaRequest.dna)
        val dnaKey = CacheUtils.getDnaKey(isMutantDnaRequest.dna)
        logger.info(
            "MutantCheckService::checkIfAHumanIsMutant - DNA to verify  - DnaKey[{}] - Timestamp[{}]",
            dnaKey,
            Timestamp.valueOf(LocalDateTime.now())
        )
        var isMutant: Boolean
        val isMutantCache = stringRedisTemplate.opsForValue().get(dnaKey)
        if (isMutantCache.isNullOrEmpty()) {
            val dna = dnaRepository.findAllByDna(dnaKey)
            if (dna.isNullOrEmpty()) {
                isMutant = isMutant(dnaMatrix)
                dnaRepository.save(MutantCheckMapper.buildMutantCheckEntity(dnaKey, isMutant))
            } else {
                isMutant = dna.first().isMutant
            }
            stringRedisTemplate.opsForValue().set(dnaKey, isMutant.toString())
        } else {
            logger.warn(
                "MutantCheckService::checkIfAHumanIsMutant - Duplicate DNA verification - DnaKey[{}] - Timestamp[{}]",
                dnaKey,
                Timestamp.valueOf(LocalDateTime.now())
            )
            isMutant = isMutantCache.toBoolean()
        }

        return if (isMutant) HttpStatus.NO_CONTENT else HttpStatus.FORBIDDEN
    }

    private fun getDnaMatrix(dna: List<String>): Array<CharArray> {
        var dnaMatrix = Array<CharArray>(dna.size) { _ -> CharArray(dna.size, { _ -> Dna.A.nucleobase }) }
        var isNucleobase = true
        var iteration = 0
        dna.map {
            var row = it.toCharArray()
            row.map {
                if (!it.equals(Dna.A.nucleobase).or(it.equals(Dna.C.nucleobase).or(it.equals(Dna.G.nucleobase)).or(it.equals(Dna.T.nucleobase)))) {
                    isNucleobase = false
                    return@map
                }
            }
            if (row.size.equals(dna.size) && isNucleobase) {
                dnaMatrix.set(iteration, row)
                ++iteration
            } else {
                logger.error(
                    "MutantCheckService::checkIfAHumanIsMutant - DNA is invalid - Timestamp[{}]",
                    Timestamp.valueOf(LocalDateTime.now())
                )
                throw MicroserviceException(INVALID_DNA, "DNA is invalid", HttpStatus.BAD_REQUEST)
            }
        }

        return dnaMatrix
    }

    private fun isMutant(dna: Array<CharArray>): Boolean {
        var numberOfMutantSequences = 0
        var numberOfHits = 0
        for ((i, chain) in dna.withIndex()) {
            for ((j, nucleobase) in chain.withIndex()) {
                val canICheckHorizontallyFoward = dna.size.minus(j) >= NUCLEOBASE_MUTANT_SEQUENCE
                val canICheckHorizontallyBackward = j >= NUCLEOBASE_MUTANT_SEQUENCE
                val canICheckVertically = dna.size.minus(i) >= NUCLEOBASE_MUTANT_SEQUENCE
                if (canICheckHorizontallyFoward) {
                    for (index in j + 1 until j + NUCLEOBASE_MUTANT_SEQUENCE) {
                        if (nucleobase.equals(chain[index])) {
                            ++numberOfHits
                        } else {
                            numberOfHits = 0
                            break
                        }
                    }
                    if (isMutantSequence(numberOfHits)) {
                        ++numberOfMutantSequences
                    }
                    numberOfHits = 0
                }
                if (canICheckVertically) {
                    for (index in i + 1 until i + NUCLEOBASE_MUTANT_SEQUENCE) {
                        if (nucleobase.equals(dna[index][j])) {
                            ++numberOfHits
                        } else {
                            break
                        }
                    }
                    if (isMutantSequence(numberOfHits)) {
                        ++numberOfMutantSequences
                    }
                    numberOfHits = 0
                }
                if (canICheckHorizontallyFoward.and(canICheckVertically)) {
                    var horizontalIndex = j + 1
                    for (verticalIndex in i + 1 until i + NUCLEOBASE_MUTANT_SEQUENCE) {
                        if (nucleobase.equals(dna[verticalIndex][horizontalIndex])) {
                            ++numberOfHits
                        } else {
                            break
                        }
                        ++horizontalIndex
                    }
                    if (isMutantSequence(numberOfHits)) {
                        ++numberOfMutantSequences
                    }
                    numberOfHits = 0
                }
                if (canICheckHorizontallyBackward.and(canICheckVertically)) {
                    var horizontalIndex = j - 1
                    for (verticalIndex in i + 1 until i + NUCLEOBASE_MUTANT_SEQUENCE) {
                        if (nucleobase.equals(dna[verticalIndex][horizontalIndex])) {
                            ++numberOfHits
                        } else {
                            break
                        }
                        --horizontalIndex
                    }
                    if (isMutantSequence(numberOfHits)) {
                        ++numberOfMutantSequences
                    }
                    numberOfHits = 0
                }
            }
        }

        return numberOfMutantSequences >= MIN_NUMBER_OF_MUTANT_SEQUENCES
    }

    private fun isMutantSequence(numberOfHits: Int): Boolean {
        var isMutantSequence = false
        if (numberOfHits.equals(NUCLEOBASE_MUTANT_SEQUENCE - 1)) {
            isMutantSequence = true
        }
        return isMutantSequence
    }
}
