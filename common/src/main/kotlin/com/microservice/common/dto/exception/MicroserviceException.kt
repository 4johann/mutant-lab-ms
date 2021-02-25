package com.microservice.common.dto.exception

import org.springframework.http.HttpStatus

data class MicroserviceException(
    val code: String,
    override val message: String = "",
    val status: HttpStatus
) : RuntimeException()
