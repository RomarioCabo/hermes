package com.br.hermes.domain.dto

import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull

data class MessageRequest(
    @field:NotBlank(message = "O campo templateId não pode ser vazio ou nulo.")
    val templateId: String,

    @field:NotNull(message = "O templateVariables não pode ser nulo.")
    val templateVariables: Map<String, Any>,

    @field:Email(message = "Digite um e-mail válido.")
    @field:NotBlank(message = "O campo emailTo não pode ser vazio ou nulo.")
    val emailTo: String,

    @field:NotBlank(message = "O campo subject não pode ser vazio ou nulo.")
    val subject: String
)