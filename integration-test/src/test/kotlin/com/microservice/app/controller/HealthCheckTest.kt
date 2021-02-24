package com.microservice.app.controller

import com.microservice.common.route.InternalRoute.HEALTH
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@SpringBootTest
@AutoConfigureMockMvc
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class HealthCheckTest {

    @Autowired
    lateinit var mvc: MockMvc

    @Test
    fun healthCheck() {
        mvc.perform(get(HEALTH))
            .andExpect(status().isOk)
    }
}
