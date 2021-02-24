package com.microservice.common.exception.dto

data class MicroserviceExceptionResponse(
    val code: String,
    val message: String = "Unexpected error, contact support.",
    val cause: String? = null
)
