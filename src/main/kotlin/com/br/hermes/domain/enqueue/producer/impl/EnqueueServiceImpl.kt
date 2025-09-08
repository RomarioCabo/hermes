package com.br.hermes.domain.enqueue.producer.impl

import com.br.hermes.domain.dto.defaultresponse.DefaultResponse
import com.br.hermes.domain.dto.sendemail.MessageRequest
import com.br.hermes.domain.enqueue.producer.EnqueueService
import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.stereotype.Service

@Service
class EnqueueServiceImpl(
    private val kafkaTemplate: KafkaTemplate<String, String>,
    private val objectMapper: ObjectMapper
) : EnqueueService {
    private val topic = "hermes-topic"

    override fun email(messageRequest: MessageRequest): DefaultResponse {
        val messageJson: String = objectMapper.writeValueAsString(messageRequest)
        kafkaTemplate.send(topic, messageJson)

        return DefaultResponse("E-mail enviado com sucesso!")
    }
}