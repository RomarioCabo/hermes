package com.br.hermes.domain.template

import com.br.hermes.domain.dto.template.TemplateRequest
import com.br.hermes.domain.dto.template.TemplateResponse

interface TemplateService {
    fun saveTemplate(request: TemplateRequest): TemplateResponse
    fun updateTemplate(request: TemplateRequest): TemplateResponse
    fun findTemplate(idTemplate: String, team: String): TemplateResponse?
}