package com.microservice.common.exception

object ErrorCode {
    private const val MICROSERVICE_EXCEPTION = "mutant-lab-ms"

    const val INTERNAL_SERVER_ERROR = "$MICROSERVICE_EXCEPTION.internal_server_error"
    const val INVALID_DNA = "$MICROSERVICE_EXCEPTION.invalid_dna"
}
