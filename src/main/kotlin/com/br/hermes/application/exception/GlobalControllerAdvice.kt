package com.br.hermes.application.exception

import org.springframework.context.MessageSource
import org.springframework.context.i18n.LocaleContextHolder
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import java.time.LocalDateTime
import org.springframework.http.HttpStatus.*
import org.springframework.http.MediaType.APPLICATION_JSON

@ControllerAdvice
class GlobalControllerAdvice(
    private val messageSource: MessageSource
) {

    companion object {
        private const val TITLE = "Ops! Ocorreu um erro"
    }

    @ExceptionHandler(Exception::class, RuntimeException::class)
    fun handleRuntimeException(ex: Exception): ResponseEntity<ErrorHttpResponseDto> {
        val errorDto = ErrorHttpResponseDto(
            code = INTERNAL_SERVER_ERROR.toString(),
            title = TITLE,
            message = ex.message ?: "Erro inesperado",
            timestamp = LocalDateTime.now()
        )

        return ResponseEntity.status(INTERNAL_SERVER_ERROR)
            .contentType(APPLICATION_JSON)
            .body(errorDto)
    }

    @ExceptionHandler(MethodArgumentNotValidException::class)
    fun handleValidationException(exception: MethodArgumentNotValidException): ResponseEntity<ErrorHttpResponseDto> {
        val errorsByField: Map<String, List<String>> = exception.bindingResult.fieldErrors
            .groupBy { it.field }
            .mapValues { entry ->
                entry.value.map { messageSource.getMessage(it, LocaleContextHolder.getLocale()) }
            }

        val errors: List<String> = errorsByField
            .toSortedMap()
            .flatMap { it.value.sorted() }

        val errorDto = ErrorHttpResponseDto(
            code = BAD_REQUEST.toString(),
            title = TITLE,
            message = errors.toString(),
            timestamp = LocalDateTime.now()
        )

        return ResponseEntity.status(BAD_REQUEST)
            .contentType(APPLICATION_JSON)
            .body(errorDto)
    }
}
