package com.br.hermes.domain.dto

import com.br.hermes.domain.dto.sendemail.MessageRequest
import jakarta.validation.Validation
import jakarta.validation.Validator
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class MessageRequestTest {

    companion object {
        private lateinit var validator: Validator

        @BeforeAll
        @JvmStatic
        fun setupValidator() {
            val factory = Validation.buildDefaultValidatorFactory()
            validator = factory.validator
        }
    }

    @Test
    fun `valid MessageRequest should have no violations`() {
        val request = MessageRequest(
            templateId = "welcome-template",
            templateVariables = mapOf("name" to "Romário"),
            emailTo = "test@example.com",
            subject = "Bem-vindo!"
        )

        val violations = validator.validate(request)
        assertTrue(violations.isEmpty(), "Não deve haver violações para um request válido")
    }

    @Test
    fun `blank templateId should fail validation`() {
        val request = MessageRequest(
            templateId = "",
            templateVariables = mapOf("key" to "value"),
            emailTo = "test@example.com",
            subject = "Assunto"
        )

        val violations = validator.validate(request)
        assertEquals(1, violations.size)
        assertEquals("O campo templateId não pode ser vazio ou nulo.", violations.first().message)
    }

    @Test
    fun `invalid email should fail validation`() {
        val request = MessageRequest(
            templateId = "template",
            templateVariables = mapOf("key" to "value"),
            emailTo = "invalid-email",
            subject = "Assunto"
        )

        val violations = validator.validate(request)
        assertEquals(1, violations.size)
        assertEquals("Digite um e-mail válido.", violations.first().message)
    }

    @Test
    fun `blank subject should fail validation`() {
        val request = MessageRequest(
            templateId = "template",
            templateVariables = mapOf("key" to "value"),
            emailTo = "test@example.com",
            subject = ""
        )

        val violations = validator.validate(request)
        assertEquals(1, violations.size)
        assertEquals("O campo subject não pode ser vazio ou nulo.", violations.first().message)
    }
}
