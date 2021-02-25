package com.microservice.core.service

import com.microservice.common.dto.response.MutantStatResponse
import com.microservice.core.mapper.MutantStatMapper
import com.microservice.domain.repository.DnaRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class MutantStatService {

    @Autowired
    private lateinit var dnaRepository: DnaRepository

    fun getMutantStats(): MutantStatResponse {
        val numberOfMutants = dnaRepository.countByIsMutant(true)
        val numberOfHumans = dnaRepository.countByIsMutant(false)
        return MutantStatMapper.buildMutantStatResponse(numberOfMutants, numberOfHumans)
    }
}
