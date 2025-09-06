package com.br.hermes.domain.send.impl

import com.br.hermes.domain.dto.MessageRequest
import com.br.hermes.domain.send.SendService
import jakarta.mail.MessagingException
import jakarta.mail.internet.MimeMessage
import org.springframework.beans.factory.annotation.Value
import org.springframework.mail.javamail.JavaMailSender
import org.springframework.mail.javamail.MimeMessageHelper
import org.springframework.stereotype.Service
import org.thymeleaf.context.Context
import org.thymeleaf.spring6.SpringTemplateEngine

@Service
class SendServiceImpl(
    private val mailSender: JavaMailSender,
    private val templateEngine: SpringTemplateEngine
) : SendService {

    @Value("\${mail.username}")
    private lateinit var from: String

    override fun email(request: MessageRequest) {
        val context: Context = Context().apply {
            setVariables(request.templateVariables)
        }

        val htmlContent: String = templateEngine.process(request.templateId, context)

        try {
            val message: MimeMessage = mailSender.createMimeMessage()
            val helper = MimeMessageHelper(message, true)

            helper.setFrom(from)
            helper.setTo(request.emailTo)
            helper.setSubject(request.subject)
            helper.setText(htmlContent, true)

            mailSender.send(message)
        } catch (e: MessagingException) {
            throw RuntimeException("Erro ao enviar e-mail", e)
        }
    }
}
