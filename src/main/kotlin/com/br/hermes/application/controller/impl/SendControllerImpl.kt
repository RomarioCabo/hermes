package com.br.hermes.application.controller.impl

import com.br.hermes.application.controller.SendController
import com.br.hermes.domain.send.SendService
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.http.ResponseEntity
import com.br.hermes.application.controller.constants.ControllerConstants.V1
import com.br.hermes.domain.dto.MessageRequest

@CrossOrigin("*")
@RestController
@RequestMapping(V1)
class SendControllerImpl(
    private val sendService: SendService
) : SendController {

    override fun email(request: MessageRequest): ResponseEntity<Unit> {
        sendService.email(request)
        return ResponseEntity.ok().build()
    }
}
