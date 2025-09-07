package com.br.hermes.domain.mapper

import com.br.hermes.domain.dto.template.TemplateRequest
import com.br.hermes.domain.dto.template.TemplateResponse
import com.br.hermes.infrastructure.persistence.template.TemplateEntity
import org.springframework.stereotype.Component
import java.time.LocalDateTime

@Component
class Mapper {

    fun fromRequestToEntity(request: TemplateRequest, updatedAt: LocalDateTime? = null) = TemplateEntity(
        idTemplate = request.idTemplate,
        team = request.team,
        html = request.html,
        createdAt = LocalDateTime.now(),
        updatedAt = updatedAt
    )

    fun fromEntityToResponse(entity: TemplateEntity) = TemplateResponse(
        idTemplate = entity.idTemplate,
        team = entity.team,
        html = entity.html,
        createdAt = entity.createdAt,
        updatedAt = entity.updatedAt
    )
}