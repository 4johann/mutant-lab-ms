package com.microservice.common.dto.exception

data class MicroserviceExceptionResponse(
    val code: String,
    val message: String = "Unexpected error, contact support.",
    val cause: String? = null
)
