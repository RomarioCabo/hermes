package com.br.hermes.domain.template.impl

import com.br.hermes.domain.DatabaseProvider
import com.br.hermes.domain.dto.template.TemplateRequest
import com.br.hermes.domain.dto.template.TemplateResponse
import com.br.hermes.domain.template.TemplateService
import org.springframework.stereotype.Service

@Service
class TemplateServiceImpl(private val databaseProvider: DatabaseProvider) : TemplateService {

    override fun saveTemplate(request: TemplateRequest): TemplateResponse =
        databaseProvider.saveTemplate(request)

    override fun updateTemplate(request: TemplateRequest): TemplateResponse =
        databaseProvider.updateTemplate(request)

    override fun findTemplate(idTemplate: String, team: String): TemplateResponse? =
        databaseProvider.findTemplate(idTemplate, team)
}