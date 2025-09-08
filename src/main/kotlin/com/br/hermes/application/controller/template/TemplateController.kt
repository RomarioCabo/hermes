package com.br.hermes.application.controller.template

import com.br.hermes.application.controller.constants.ControllerConstants
import com.br.hermes.application.exception.ErrorHttpResponseDto
import com.br.hermes.domain.dto.template.TemplateRequest
import com.br.hermes.domain.dto.template.TemplateResponse
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.tags.Tag
import io.swagger.v3.oas.annotations.tags.Tags
import jakarta.validation.Valid
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody

@Tags(Tag(name = "Template", description = "Template REST Controller"))
interface TemplateController {

    @ApiResponse(
        responseCode = "201",
        description = "Template criado com sucesso.",
        content = [Content(
            mediaType = MediaType.APPLICATION_JSON_VALUE,
            schema = Schema(implementation = TemplateResponse::class)
        )]
    )
    @ApiResponse(
        responseCode = "500",
        description = "Internal Server Error.",
        content = [Content(
            mediaType = MediaType.APPLICATION_JSON_VALUE,
            schema = Schema(implementation = ErrorHttpResponseDto::class)
        )]
    )
    @PostMapping(
        value = [ControllerConstants.TEMPLATE],
        consumes = [MediaType.APPLICATION_JSON_VALUE],
        produces = [MediaType.APPLICATION_JSON_VALUE]
    )
    fun saveTemplate(@RequestBody @Valid request: TemplateRequest): ResponseEntity<TemplateResponse>

    @ApiResponse(
        responseCode = "200",
        description = "Template alterado com sucesso.",
        content = [Content(
            mediaType = MediaType.APPLICATION_JSON_VALUE,
            schema = Schema(implementation = TemplateResponse::class)
        )]
    )
    @ApiResponse(
        responseCode = "500",
        description = "Internal Server Error.",
        content = [Content(
            mediaType = MediaType.APPLICATION_JSON_VALUE,
            schema = Schema(implementation = ErrorHttpResponseDto::class)
        )]
    )
    @PutMapping(
        value = [ControllerConstants.TEMPLATE],
        consumes = [MediaType.APPLICATION_JSON_VALUE],
        produces = [MediaType.APPLICATION_JSON_VALUE]
    )
    fun updateTemplate(@RequestBody @Valid request: TemplateRequest): ResponseEntity<TemplateResponse>

    @ApiResponse(
        responseCode = "200",
        description = "Template recuperado com sucesso.",
        content = [Content(
            mediaType = MediaType.APPLICATION_JSON_VALUE,
            schema = Schema(implementation = TemplateResponse::class)
        )]
    )
    @ApiResponse(
        responseCode = "500",
        description = "Internal Server Error.",
        content = [Content(
            mediaType = MediaType.APPLICATION_JSON_VALUE,
            schema = Schema(implementation = ErrorHttpResponseDto::class)
        )]
    )
    @GetMapping(
        value = [ControllerConstants.TEMPLATE + "/{idTemplate}" + "/{team}"],
        produces = [MediaType.APPLICATION_JSON_VALUE]
    )
    fun findTemplate(@PathVariable idTemplate: String, @PathVariable team: String): ResponseEntity<TemplateResponse?>
}