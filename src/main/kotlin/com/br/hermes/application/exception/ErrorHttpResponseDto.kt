package com.br.hermes.application.exception

import com.fasterxml.jackson.annotation.JsonFormat
import java.time.LocalDateTime

data class ErrorHttpResponseDto(
    val code: String,
    val title: String,
    val message: String,

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm:ss")
    val timestamp: LocalDateTime
)
