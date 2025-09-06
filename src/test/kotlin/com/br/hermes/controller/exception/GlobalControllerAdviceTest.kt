package com.br.hermes.controller.exception

import com.br.hermes.application.exception.GlobalControllerAdvice
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.kotlin.*
import org.springframework.context.MessageSource
import org.springframework.core.MethodParameter
import org.springframework.http.HttpStatus
import org.springframework.validation.BindingResult
import org.springframework.validation.FieldError
import org.springframework.web.bind.MethodArgumentNotValidException
import java.time.LocalDateTime
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class GlobalControllerAdviceTest {

    private lateinit var messageSource: MessageSource
    private lateinit var advice: GlobalControllerAdvice

    @BeforeEach
    fun setup() {
        messageSource = mock()
        advice = GlobalControllerAdvice(messageSource)
    }

    @Test
    fun `handleRuntimeException should return 500 with correct message`() {
        val ex = RuntimeException("Erro inesperado!")

        val response = advice.handleRuntimeException(ex)

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.statusCode)
        assertEquals("500 INTERNAL_SERVER_ERROR", response.body?.code)
        assertEquals("Ops! Ocorreu um erro", response.body?.title)
        assertEquals("Erro inesperado!", response.body?.message)
        assertTrue(response.body?.timestamp is LocalDateTime)
    }

    @Test
    fun `handleValidationException should return 400 with field errors`() {
        // Cria FieldErrors reais
        val fieldError1 = FieldError("objectName", "field1", "Campo inválido 1")
        val fieldError2 = FieldError("objectName", "field2", "Campo inválido 2")

        // Mock do BindingResult
        val bindingResult = mock<BindingResult> {
            on { fieldErrors } doReturn listOf(fieldError1, fieldError2)
        }

        // Cria um MethodParameter "dummy" para passar no construtor
        val dummyMethod = this::class.java.getDeclaredMethod("dummyMethod")
        val methodParameter = MethodParameter(dummyMethod, -1)

        // Instancia real de MethodArgumentNotValidException
        val exception = MethodArgumentNotValidException(methodParameter, bindingResult)

        // Mock do MessageSource
        whenever(messageSource.getMessage(any<FieldError>(), any())).thenAnswer {
            (it.arguments[0] as FieldError).defaultMessage!!
        }

        val response = advice.handleValidationException(exception)

        assertEquals(HttpStatus.BAD_REQUEST, response.statusCode)
        assertEquals("400 BAD_REQUEST", response.body?.code)
        assertEquals("Ops! Ocorreu um erro", response.body?.title)
        assertTrue(response.body?.message?.contains("Campo inválido 1") == true)
        assertTrue(response.body?.message?.contains("Campo inválido 2") == true)
        assertTrue(response.body?.timestamp is LocalDateTime)
    }

    fun dummyMethod() {}
}
