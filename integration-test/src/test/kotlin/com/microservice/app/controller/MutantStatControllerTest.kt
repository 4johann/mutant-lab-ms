package com.microservice.app.controller

import com.microservice.app.util.ReadJsonFile
import com.microservice.common.route.InternalRoute.STATS
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.jdbc.Sql
import org.springframework.test.context.jdbc.SqlGroup
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers

@SpringBootTest
@AutoConfigureMockMvc
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class MutantStatControllerTest {

    @Autowired
    private lateinit var mvc: MockMvc

    @Test
    @SqlGroup(
        value = [
            Sql("/query/delete_dna.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
        ]
    )
    fun `get statistics without data`() {
        val url = STATS
        val response = ReadJsonFile.getContent("response/get_stats_without_data.json")
        val request = MockMvcRequestBuilders.get(url)

        mvc.perform(request)
            .andExpect(MockMvcResultMatchers.status().isOk)
            .andExpect(MockMvcResultMatchers.content().json(response))
    }

    @Test
    @SqlGroup(
        value = [
            Sql("/query/delete_dna.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD),
            Sql("/query/insert_dna.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
        ]
    )
    fun `get statistics with a ratio equal to point 2`() {
        val url = STATS
        val response = ReadJsonFile.getContent("response/get_statistics_with_a_ratio_equal_to_point_2.json")
        val request = MockMvcRequestBuilders.get(url)

        mvc.perform(request)
            .andExpect(MockMvcResultMatchers.status().isOk)
            .andExpect(MockMvcResultMatchers.content().json(response))
    }
}
