package com.microservice.common.environment

import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Configuration

@Configuration("Environment")
class Environment {
    companion object {
        lateinit var SWAGGER_HOST: String
    }

    @Value("\${ms.environment.swagger-host}")
    fun setHostName(environmentKey: String) {
        SWAGGER_HOST = environmentKey
    }
}
