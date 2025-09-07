package com.br.hermes.infrastructure.persistence

import com.br.hermes.domain.DatabaseProvider
import com.br.hermes.domain.dto.template.TemplateRequest
import com.br.hermes.domain.dto.template.TemplateResponse
import com.br.hermes.domain.mapper.Mapper
import com.br.hermes.infrastructure.persistence.template.TemplateRepository
import org.springframework.stereotype.Component
import java.time.LocalDateTime

@Component
class DatabaseProviderImpl(private val mapper: Mapper, private val templateRepository: TemplateRepository) :
    DatabaseProvider {

    override fun saveTemplate(request: TemplateRequest): TemplateResponse =
        templateRepository.save(mapper.fromRequestToEntity(request))
            .let { templateEntity -> mapper.fromEntityToResponse(templateEntity) }

    override fun updateTemplate(request: TemplateRequest): TemplateResponse =
        templateRepository.save(mapper.fromRequestToEntity(request, LocalDateTime.now()))
            .let { templateEntity -> mapper.fromEntityToResponse(templateEntity) }

    override fun findTemplate(idTemplate: String, team: String): TemplateResponse? =
        templateRepository.findTemplate(idTemplate, team)
            ?.let { templateEntity -> mapper.fromEntityToResponse(templateEntity) }
}