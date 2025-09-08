package com.br.hermes.domain.enqueue.consumer

import com.br.hermes.domain.dto.sendemail.MessageRequest
import com.br.hermes.domain.send.SendService
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.stereotype.Service

@Service
class EnqueueConsumerService(private val objectMapper: ObjectMapper, private val sendService: SendService) {

    @KafkaListener(topics = ["hermes-topic"], groupId = "hermes-group")
    fun consumeEmail(message: String) {
        try {
            val messageRequest: MessageRequest = objectMapper.readValue(message)
            println("📥 Mensagem recebida do tópico [hermes-topic]: $messageRequest")
            sendService.email(messageRequest)
        } catch (e: Exception) {
            println("❌ Erro ao processar mensagem Kafka: ${e.message}")
        }
    }
}
