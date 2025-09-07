package com.br.hermes.domain

import com.br.hermes.domain.dto.template.TemplateRequest
import com.br.hermes.domain.dto.template.TemplateResponse

interface DatabaseProvider {
    fun saveTemplate(request: TemplateRequest): TemplateResponse
    fun updateTemplate(request: TemplateRequest): TemplateResponse
    fun findTemplate(idTemplate: String, team: String): TemplateResponse?
}