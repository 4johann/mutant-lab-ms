package com.microservice.core

import com.microservice.common.exception.ErrorCode
import com.microservice.common.exception.dto.MicroserviceException
import com.microservice.common.exception.dto.MicroserviceExceptionResponse
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.context.request.WebRequest
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler

@ControllerAdvice
class ExceptionHandler: ResponseEntityExceptionHandler() {

    @ExceptionHandler(MicroserviceException::class)
    fun microserviceException(exception: MicroserviceException, request: WebRequest): ResponseEntity<MicroserviceExceptionResponse> {
        val response = MicroserviceExceptionResponse(exception.code, exception.message, exception.cause?.message)

        return ResponseEntity.status(exception.status).body(response)
    }

    @ExceptionHandler(Exception::class)
    fun exception(exception: Exception): ResponseEntity<MicroserviceExceptionResponse> {
        val response = MicroserviceExceptionResponse(
            code = ErrorCode.INTERNAL_SERVER_ERROR,
            message = exception.message ?: "An unexpected error has occurred",
            cause = exception.cause?.message
        )

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response)
    }
}