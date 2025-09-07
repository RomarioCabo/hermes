package com.br.hermes.domain.send.impl

import com.br.hermes.application.exception.TemplateNotFoundException
import com.br.hermes.domain.dto.sendemail.MessageRequest
import com.br.hermes.domain.dto.template.TemplateResponse
import com.br.hermes.domain.send.SendService
import com.br.hermes.domain.template.TemplateService
import jakarta.mail.MessagingException
import jakarta.mail.internet.MimeMessage
import org.springframework.beans.factory.annotation.Value
import org.springframework.mail.javamail.JavaMailSender
import org.springframework.mail.javamail.MimeMessageHelper
import org.springframework.stereotype.Service
import org.thymeleaf.TemplateEngine
import org.thymeleaf.context.Context

@Service
class SendServiceImpl(
    private val mailSender: JavaMailSender,
    private val stringTemplateEngine: TemplateEngine,
    private val templateService: TemplateService,
    @Value("\${mail.username}") private val from: String
) : SendService {

    override fun email(request: MessageRequest) {
        val template: TemplateResponse = templateService.findTemplate(
            request.templateId, request.team
        ) ?: throw TemplateNotFoundException("Template não encontrado: ${request.templateId} / ${request.team}")

        val context: Context = Context().apply {
            setVariables(request.templateVariables)
        }

        val htmlContent: String = stringTemplateEngine.process(template.html, context)

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
