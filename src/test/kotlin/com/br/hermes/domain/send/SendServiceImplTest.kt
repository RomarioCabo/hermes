package com.br.hermes.domain.send

import com.br.hermes.domain.dto.MessageRequest
import com.br.hermes.domain.send.impl.SendServiceImpl
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
    private lateinit var sendService: SendServiceImpl

    private val from = "test@example.com"

    @BeforeEach
    fun setup() {
        mailSender = mock()
        templateEngine = mock()
        sendService = SendServiceImpl(mailSender, templateEngine, from)
    }

    @Test
    fun `should send email with success`() {
        val request = buildRequest(templateId = "email-template", subject = "Assunto de Teste")

        val mimeMessage: MimeMessage = mock()
        whenever(mailSender.createMimeMessage()).thenReturn(mimeMessage)
        whenever(templateEngine.process(eq(request.templateId), any<Context>())).thenReturn("<html><body>Email de Teste</body></html>")

        sendService.email(request)

        verify(mailSender, times(1)).send(mimeMessage)
        verify(templateEngine, times(1)).process(eq(request.templateId), any())
    }

    @Test
    fun `should throw exception when try send email`() {
        val request = buildRequest(templateId = "template", subject = "Erro Teste")

        val mimeMessage: MimeMessage = mock()
        whenever(mailSender.createMimeMessage()).thenReturn(mimeMessage)
        whenever(templateEngine.process(eq(request.templateId), any<Context>())).thenReturn("<html>conteúdo</html>")
        doThrow(RuntimeException("Falha ao enviar e-mail")).whenever(mailSender).send(mimeMessage)

        assertFailsWith<RuntimeException> {
            sendService.email(request)
        }
    }

    private fun buildRequest(
        templateId: String = "template",
        templateVariables: Map<String, Any> = mapOf("x" to "y"),
        emailTo: String = "destinatario@example.com",
        subject: String = "Assunto de Teste"
    ): MessageRequest = MessageRequest(
        templateId = templateId,
        templateVariables = templateVariables,
        emailTo = emailTo,
        subject = subject
    )
}
