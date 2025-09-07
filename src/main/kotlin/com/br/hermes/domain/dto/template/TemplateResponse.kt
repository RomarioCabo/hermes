package com.br.hermes.domain.dto.template

import java.time.LocalDateTime

data class TemplateResponse(
    val idTemplate: String,
    val team: String,
    val html: String,
    val createdAt: LocalDateTime,
    val updatedAt: LocalDateTime? = null
)
