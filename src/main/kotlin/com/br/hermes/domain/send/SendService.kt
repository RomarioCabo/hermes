package com.br.hermes.domain.send

import com.br.hermes.domain.dto.sendemail.MessageRequest

fun interface SendService {
    fun email(request: MessageRequest)
}