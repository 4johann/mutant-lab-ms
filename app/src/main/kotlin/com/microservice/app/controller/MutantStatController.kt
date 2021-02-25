package com.microservice.app.controller

import com.microservice.common.dto.response.MutantStatResponse
import com.microservice.common.route.InternalRoute.STATS
import com.microservice.core.service.MutantStatService
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.sql.Timestamp
import java.time.LocalDateTime

@RestController
@RequestMapping
class MutantStatController {

    private val logger = LoggerFactory.getLogger(MutantStatController::class.java)

    @Autowired
    private lateinit var mutantStatService: MutantStatService

    @GetMapping(value = [STATS])
    fun getMutantStats(): ResponseEntity<MutantStatResponse> {
        logger.info(
            "MutantStatController::getMutantStats - START - Timestamp[{}]",
            Timestamp.valueOf(LocalDateTime.now())
        )
        val response = mutantStatService.getMutantStats()
        logger.info(
            "MutantStatController::getMutantStats - FINISH - Timestamp[{}]",
            Timestamp.valueOf(LocalDateTime.now())
        )

        return ResponseEntity(response, HttpStatus.OK)
    }
}
