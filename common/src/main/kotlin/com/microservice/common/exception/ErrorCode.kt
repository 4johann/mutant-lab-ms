package com.microservice.common.exception

object ErrorCode {
    private const val MICROSERVICE_EXCEPTION = "kotlin-template-ms"

    const val INTERNAL_SERVER_ERROR = "${MICROSERVICE_EXCEPTION}.internal_server_error"
}
