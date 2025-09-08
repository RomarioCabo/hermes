package com.br.hermes.application.controller.send.impl

import com.br.hermes.application.controller.send.SendController
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.http.ResponseEntity
import com.br.hermes.application.controller.constants.ControllerConstants.V1
import com.br.hermes.domain.dto.defaultresponse.DefaultResponse
import com.br.hermes.domain.dto.sendemail.MessageRequest
import com.br.hermes.domain.enqueue.producer.EnqueueService

@CrossOrigin("*")
@RestController
@RequestMapping(V1)
class SendControllerImpl(
    private val enqueueService: EnqueueService
) : SendController {
    override fun email(request: MessageRequest): ResponseEntity<DefaultResponse> =
        enqueueService.email(request).let { response -> ResponseEntity.ok(response) }
}
