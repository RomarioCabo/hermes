package com.br.hermes.domain.enqueue.producer

import com.br.hermes.domain.dto.defaultresponse.DefaultResponse
import com.br.hermes.domain.dto.sendemail.MessageRequest

fun interface EnqueueService {
    fun email(messageRequest: MessageRequest): DefaultResponse
}