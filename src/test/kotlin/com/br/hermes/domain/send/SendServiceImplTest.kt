package com.br.hermes.domain.send

import com.br.hermes.domain.dto.sendemail.MessageRequest
import com.br.hermes.domain.dto.template.TemplateResponse
import com.br.hermes.domain.send.impl.SendServiceImpl
import com.br.hermes.domain.template.TemplateService
import jakarta.mail.internet.MimeMessage
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.kotlin.*
import org.springframework.mail.javamail.JavaMailSender
import org.thymeleaf.context.Context
import org.thymeleaf.spring6.SpringTemplateEngine
import kotlin.test.assertFailsWith

class SendServiceImplTest {

    private lateinit var mailSender: JavaMailSender
    private lateinit var templateEngine: SpringTemplateEngine
    private lateinit var templateService: TemplateService
    private lateinit var sendService: SendServiceImpl

    private val from = "test@example.com"

    @BeforeEach
    fun setup() {
        mailSender = mock()
        templateEngine = mock()
        templateService = mock()
        sendService = SendServiceImpl(mailSender, templateEngine, templateService, from)
    }

    @Test
    fun `should send email with success`() {
        val request = buildRequest(templateId = "email-template", subject = "Assunto de Teste")

        // Mock do templateService para retornar um TemplateResponse válido
        whenever(templateService.findTemplate(request.templateId, request.team))
            .thenReturn(
                TemplateResponse(
                    idTemplate = request.templateId,
                    team = request.team,
                    html = "<html><body>Email de Teste</body></html>",
                    createdAt = java.time.LocalDateTime.now()
                )
            )

        val mimeMessage: MimeMessage = mock()
        whenever(mailSender.createMimeMessage()).thenReturn(mimeMessage)
        whenever(
            templateEngine.process(
                eq("<html><body>Email de Teste</body></html>"),
                any<Context>()
            )
        ).thenReturn("<html><body>Email de Teste</body></html>")

        sendService.email(request)

        verify(mailSender, times(1)).send(mimeMessage)
        verify(templateEngine, times(1)).process(eq("<html><body>Email de Teste</body></html>"), any())
    }

    @Test
    fun `should throw exception when try send email`() {
        val request = buildRequest(templateId = "template", subject = "Erro Teste")

        val mimeMessage: MimeMessage = mock()
        whenever(mailSender.createMimeMessage()).thenReturn(mimeMessage)
        whenever(templateEngine.process(eq(request.templateId), any<Context>()))
            .thenReturn("<html>conteúdo</html>")
        doThrow(RuntimeException("Falha ao enviar e-mail")).whenever(mailSender).send(mimeMessage)

        assertFailsWith<RuntimeException> {
            sendService.email(request)
        }
    }

    @Test
    fun `should send email with template from database`() {
        val htmlTemplate = "<html><body>Olá <span th:text=\"\${name}\"></span></body></html>"
        val request = buildRequest(
            templateId = "email-template",
            templateVariables = mapOf("name" to "Romário")
        )

        whenever(templateService.findTemplate(request.templateId, request.team))
            .thenReturn(
                TemplateResponse(
                    idTemplate = request.templateId,
                    team = request.team,
                    html = htmlTemplate,
                    createdAt = java.time.LocalDateTime.now()
                )
            )

        val mimeMessage: MimeMessage = mock()
        whenever(mailSender.createMimeMessage()).thenReturn(mimeMessage)
        whenever(templateEngine.process(eq(htmlTemplate), any<Context>()))
            .thenReturn("<html><body>Olá Romário</body></html>")

        sendService.email(request)

        verify(mailSender, times(1)).send(mimeMessage)
        verify(templateEngine, times(1)).process(eq(htmlTemplate), any())
    }

    private fun buildRequest(
        templateId: String = "template",
        templateVariables: Map<String, Any> = mapOf("x" to "y"),
        emailTo: String = "destinatario@example.com",
        subject: String = "Assunto de Teste"
    ): MessageRequest = MessageRequest(
        templateId = templateId,
        team = "any",
        templateVariables = templateVariables,
        emailTo = emailTo,
        subject = subject
    )
}
