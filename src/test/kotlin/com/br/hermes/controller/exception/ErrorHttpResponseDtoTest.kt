package com.br.hermes.controller.exception

import com.br.hermes.application.exception.ErrorHttpResponseDto
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.fasterxml.jackson.databind.SerializationFeature
import org.junit.jupiter.api.Test
import java.time.LocalDateTime
import kotlin.test.assertEquals

class ErrorHttpResponseDtoTest {

    private val objectMapper: ObjectMapper = ObjectMapper()
        .registerModule(JavaTimeModule())
        .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)

    @Test
    fun `should create ErrorHttpResponseDto correctly`() {
        val timestamp = LocalDateTime.of(2025, 9, 6, 15, 0, 0)
        val dto = ErrorHttpResponseDto(
            code = "500",
            title = "Ops! Ocorreu um erro",
            message = "Erro inesperado",
            timestamp = timestamp
        )

        assertEquals("500", dto.code)
        assertEquals("Ops! Ocorreu um erro", dto.title)
        assertEquals("Erro inesperado", dto.message)
        assertEquals(timestamp, dto.timestamp)
    }

    @Test
    fun `should serialize to JSON correctly`() {
        val timestamp = LocalDateTime.of(2025, 9, 6, 15, 0, 0)
        val dto = ErrorHttpResponseDto(
            code = "400",
            title = "Bad Request",
            message = "Dados inválidos",
            timestamp = timestamp
        )

        val json = objectMapper.writeValueAsString(dto)

        assert(json.contains("\"code\":\"400\""))
        assert(json.contains("\"title\":\"Bad Request\""))
        assert(json.contains("\"message\":\"Dados inválidos\""))
        assert(json.contains("2025-09-06T15:00:00"))
    }
}
