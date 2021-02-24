package com.microservice.common.exception.dto

import org.springframework.http.HttpStatus

data class MicroserviceException(
    val code: String,
    override val message: String = "",
    val status: HttpStatus
) : RuntimeException()