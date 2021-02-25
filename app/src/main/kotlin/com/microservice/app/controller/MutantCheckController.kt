package com.microservice.app.controller

import com.microservice.common.dto.request.IsMutantDnaRequest
import com.microservice.common.route.InternalRoute.MUTANTS
import com.microservice.core.service.MutantCheckService
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.sql.Timestamp
import java.time.LocalDateTime

@RestController
@RequestMapping
class MutantCheckController {

    private val logger = LoggerFactory.getLogger(MutantCheckController::class.java)

    @Autowired
    private lateinit var mutantCheckService: MutantCheckService

    @PostMapping(value = [MUTANTS])
    fun checkIfAHumanIsMutant(
        @RequestBody isMutantDnaRequest: IsMutantDnaRequest
    ): ResponseEntity<Void> {
        logger.info(
            "MutantCheckController::checkIfAHumanIsMutant - START - Timestamp[{}]",
            Timestamp.valueOf(LocalDateTime.now())
        )
        val httpStatus = mutantCheckService.checkIfAHumanIsMutant(isMutantDnaRequest)

        return ResponseEntity(httpStatus)
    }
}
