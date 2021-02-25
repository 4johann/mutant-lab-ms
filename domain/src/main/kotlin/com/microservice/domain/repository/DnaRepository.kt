package com.microservice.domain.repository

import com.microservice.domain.entity.DnaEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface DnaRepository : JpaRepository<DnaEntity, Long> {
    fun findAllByDna(dna: String): List<DnaEntity>
    fun countByIsMutant(isMutant: Boolean): Long
}
