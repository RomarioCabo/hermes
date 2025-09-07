package com.br.hermes.domain.dto.template

import jakarta.validation.constraints.NotBlank

data class TemplateRequest(
    @field:NotBlank(message = "O campo idTemplate não pode ser vazio ou nulo.")
    val idTemplate: String,

    @field:NotBlank(message = "O campo team não pode ser vazio ou nulo.")
    val team: String,

    @field:NotBlank(message = "O campo html não pode ser vazio ou nulo.")
    val html: String
)
