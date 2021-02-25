package com.microservice.core.mapper

import com.microservice.common.dto.response.MutantStatResponse

object MutantStatMapper {
    fun buildMutantStatResponse(numberOfMutants: Long, numberOfHumans: Long): MutantStatResponse {
        val ratio = numberOfMutants.toDouble().div(numberOfHumans.toDouble().plus(numberOfMutants.toDouble()))
        return MutantStatResponse(
            countMutantDna = numberOfMutants,
            countHumanDna = numberOfHumans,
            ratio = if (ratio.isNaN()) 0.00 else ratio
        )
    }
}
