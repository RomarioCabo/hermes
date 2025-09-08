package com.br.hermes.application.controller.send

import com.br.hermes.application.controller.constants.ControllerConstants
import com.br.hermes.application.exception.ErrorHttpResponseDto
import com.br.hermes.domain.dto.defaultresponse.DefaultResponse
import com.br.hermes.domain.dto.sendemail.MessageRequest
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.tags.Tag
import io.swagger.v3.oas.annotations.tags.Tags
import jakarta.validation.Valid
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody

@Tags(Tag(name = "Send", description = "Send REST Controller"))
fun interface SendController {

    @ApiResponse(
        responseCode = "200",
        description = "Email enviado com sucesso.",
        content = [Content(
            mediaType = MediaType.APPLICATION_JSON_VALUE,
            schema = Schema(implementation = DefaultResponse::class)
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
    @PostMapping(value = [ControllerConstants.SEND], consumes = [MediaType.APPLICATION_JSON_VALUE])
    fun email(@RequestBody @Valid request: MessageRequest): ResponseEntity<DefaultResponse>
}