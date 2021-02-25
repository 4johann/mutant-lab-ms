package com.microservice.app.controller

import com.microservice.app.util.ReadJsonFile
import com.microservice.common.route.InternalRoute
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.data.redis.core.StringRedisTemplate
import org.springframework.http.MediaType
import org.springframework.test.context.jdbc.Sql
import org.springframework.test.context.jdbc.SqlGroup
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers

@SpringBootTest
@AutoConfigureMockMvc
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class MutantCheckControllerTest {

    @Autowired
    private lateinit var stringRedisTemplate: StringRedisTemplate

    @Autowired
    private lateinit var mvc: MockMvc

    @Test
    @SqlGroup(
        value = [
            Sql("/query/delete_dna.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
        ]
    )
    fun `successful verification of mutant DNA`() {
        val url = InternalRoute.MUTANTS
        val dnaRequest = ReadJsonFile.getContent("request/mutant_dna.json")
        val request = MockMvcRequestBuilders.post(url)
            .contentType(MediaType.APPLICATION_JSON)
            .content(dnaRequest)

        mvc.perform(request)
            .andExpect(MockMvcResultMatchers.status().isNoContent)
    }

    @Test
    @SqlGroup(
        value = [
            Sql("/query/delete_dna.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
        ]
    )
    fun `successful vertical verification of mutant DNA`() {
        val url = InternalRoute.MUTANTS
        val dnaRequest = ReadJsonFile.getContent("request/mutant_dna_vertical_check.json")
        val request = MockMvcRequestBuilders.post(url)
            .contentType(MediaType.APPLICATION_JSON)
            .content(dnaRequest)

        mvc.perform(request)
            .andExpect(MockMvcResultMatchers.status().isNoContent)
    }

    @Test
    @SqlGroup(
        value = [
            Sql("/query/delete_dna.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
        ]
    )
    fun `successful horizontal verification of mutant DNA`() {
        val url = InternalRoute.MUTANTS
        val dnaRequest = ReadJsonFile.getContent("request/mutant_dna_horizontal_check.json")
        val request = MockMvcRequestBuilders.post(url)
            .contentType(MediaType.APPLICATION_JSON)
            .content(dnaRequest)

        mvc.perform(request)
            .andExpect(MockMvcResultMatchers.status().isNoContent)
    }

    @Test
    @SqlGroup(
        value = [
            Sql("/query/delete_dna.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
        ]
    )
    fun `successful forward oblique verification of mutant DNA`() {
        val url = InternalRoute.MUTANTS
        val dnaRequest = ReadJsonFile.getContent("request/mutant_dna_foward_oblique_check.json")
        val request = MockMvcRequestBuilders.post(url)
            .contentType(MediaType.APPLICATION_JSON)
            .content(dnaRequest)

        mvc.perform(request)
            .andExpect(MockMvcResultMatchers.status().isNoContent)
    }

    @Test
    @SqlGroup(
        value = [
            Sql("/query/delete_dna.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
        ]
    )
    fun `successful backward oblique verification of mutant DNA`() {
        val url = InternalRoute.MUTANTS
        val dnaRequest = ReadJsonFile.getContent("request/mutant_dna_backward_oblique_check.json")
        val request = MockMvcRequestBuilders.post(url)
            .contentType(MediaType.APPLICATION_JSON)
            .content(dnaRequest)

        mvc.perform(request)
            .andExpect(MockMvcResultMatchers.status().isNoContent)
    }

    @Test
    @SqlGroup(
        value = [
            Sql("/query/delete_dna.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
        ]
    )
    fun `successful verification of human DNA`() {
        val url = InternalRoute.MUTANTS
        val dnaRequest = ReadJsonFile.getContent("request/human_dna.json")
        stringRedisTemplate.delete("9a6075cacde77b50052d5c100f33e2af")
        val request = MockMvcRequestBuilders.post(url)
            .contentType(MediaType.APPLICATION_JSON)
            .content(dnaRequest)

        mvc.perform(request)
            .andExpect(MockMvcResultMatchers.status().isForbidden)
    }

    @Test
    @SqlGroup(
        value = [
            Sql("/query/delete_dna.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD),
            Sql("/query/insert_fake_mutant_dna.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
        ]
    )
    fun `successful verification of mutant DNA by returning data from the postgres database`() {
        val url = InternalRoute.MUTANTS
        val dnaRequest = ReadJsonFile.getContent("request/human_dna.json")
        stringRedisTemplate.delete("9a6075cacde77b50052d5c100f33e2af")
        val request = MockMvcRequestBuilders.post(url)
            .contentType(MediaType.APPLICATION_JSON)
            .content(dnaRequest)

        mvc.perform(request)
            .andExpect(MockMvcResultMatchers.status().isNoContent)
    }

    @Test
    @SqlGroup(
        value = [
            Sql("/query/delete_dna.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD),
        ]
    )
    fun `successful verification of mutant DNA by returning data from the redis database`() {
        val url = InternalRoute.MUTANTS
        stringRedisTemplate.opsForValue().set("9a6075cacde77b50052d5c100f33e2af", true.toString())
        val dnaRequest = ReadJsonFile.getContent("request/human_dna.json")
        val request = MockMvcRequestBuilders.post(url)
            .contentType(MediaType.APPLICATION_JSON)
            .content(dnaRequest)

        mvc.perform(request)
            .andExpect(MockMvcResultMatchers.status().isNoContent)
    }

    @Test
    fun `Nucleobase invalid`() {
        val url = InternalRoute.MUTANTS
        val dnaRequest = ReadJsonFile.getContent("request/nucleobase_invalid.json")
        val request = MockMvcRequestBuilders.post(url)
            .contentType(MediaType.APPLICATION_JSON)
            .content(dnaRequest)

        mvc.perform(request)
            .andExpect(MockMvcResultMatchers.status().isBadRequest)
    }

    @Test
    fun `DNA invalid`() {
        val url = InternalRoute.MUTANTS
        val dnaRequest = ReadJsonFile.getContent("request/dna_invalid.json")
        val request = MockMvcRequestBuilders.post(url)
            .contentType(MediaType.APPLICATION_JSON)
            .content(dnaRequest)

        mvc.perform(request)
            .andExpect(MockMvcResultMatchers.status().isBadRequest)
    }
}
