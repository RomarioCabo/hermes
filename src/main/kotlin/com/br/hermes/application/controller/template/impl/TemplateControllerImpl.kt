package com.br.hermes.application.controller.template.impl

import com.br.hermes.application.controller.constants.ControllerConstants.V1
import com.br.hermes.application.controller.template.TemplateController
import com.br.hermes.domain.dto.template.TemplateRequest
import com.br.hermes.domain.dto.template.TemplateResponse
import com.br.hermes.domain.template.TemplateService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.servlet.support.ServletUriComponentsBuilder

@CrossOrigin("*")
@RestController
@RequestMapping(V1)
class TemplateControllerImpl(private val templateService: TemplateService) : TemplateController {

    override fun saveTemplate(request: TemplateRequest): ResponseEntity<TemplateResponse> =
        templateService.saveTemplate(request).let { response ->
            ResponseEntity.created(buildLocationUri(response)).body(response)
        }

    override fun updateTemplate(request: TemplateRequest): ResponseEntity<TemplateResponse> =
        ResponseEntity.ok(templateService.updateTemplate(request))

    override fun findTemplate(idTemplate: String, team: String): ResponseEntity<TemplateResponse?> =
        ResponseEntity.ok(templateService.findTemplate(idTemplate, team))

    private fun buildLocationUri(template: TemplateResponse) =
        ServletUriComponentsBuilder
            .fromCurrentRequest()
            .path("/{idTemplate}/{team}")
            .buildAndExpand(template.idTemplate, template.team)
            .toUri()
}