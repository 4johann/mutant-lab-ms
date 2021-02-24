package com.microservice.app.controller

import com.microservice.common.route.InternalRoute.HEALTH
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping(value = [(HEALTH)])
class HealthCheck {

    @GetMapping
    fun getHealthCheck(): ResponseEntity<Void> = ResponseEntity(HttpStatus.OK)
}
