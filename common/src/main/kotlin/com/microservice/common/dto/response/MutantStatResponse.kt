package com.microservice.common.dto.response

import com.fasterxml.jackson.databind.PropertyNamingStrategy
import com.fasterxml.jackson.databind.annotation.JsonNaming

@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy::class)
data class MutantStatResponse(
    val countMutantDna: Long,
    val countHumanDna: Long,
    val ratio: Double
)
