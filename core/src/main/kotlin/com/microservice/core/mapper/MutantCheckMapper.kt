package com.microservice.core.mapper

import com.microservice.domain.entity.DnaEntity

object MutantCheckMapper {
    fun buildMutantCheckEntity(dnaKey: String, isMutant: Boolean): DnaEntity {
        return DnaEntity(
            dna = dnaKey,
            isMutant = isMutant
        )
    }
}
