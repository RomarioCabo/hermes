package com.br.hermes.controller

import com.br.hermes.application.controller.send.impl.SendControllerImpl
import com.br.hermes.domain.dto.sendemail.MessageRequest
import com.br.hermes.domain.send.SendService
import com.fasterxml.jackson.databind.ObjectMapper
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.kotlin.eq
import org.mockito.kotlin.mock
import org.mockito.kotlin.times
import org.mockito.kotlin.verify
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import com.br.hermes.application.controller.constants.ControllerConstants.SEND
import com.br.hermes.application.controller.constants.ControllerConstants.V1

class SendControllerTest {

    private lateinit var mockMvc: MockMvc
    private lateinit var sendService: SendService
    private lateinit var objectMapper: ObjectMapper

    companion object {
        private const val TEMPLATE_ID = "otp-template"
        private const val TEAM = "ANY"
        private const val EMAIL_TO = "destinatario@example.com"
        private const val SUBJECT = "Código OTP"
        private val TEMPLATE_VARIABLES = mapOf("code" to "123456")
    }

    @BeforeEach
    fun setUp() {
        sendService = mock()
        val controller = SendControllerImpl(sendService)
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build()
        objectMapper = ObjectMapper()
    }

    @Test
    fun `should send email with success`() {
        val request = buildOtpRequest()

        mockMvc.perform(
            post("$V1$SEND")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request))
        ).andExpect(status().isOk)

        verify(sendService, times(1)).email(eq(request))
    }

    private fun buildOtpRequest(): MessageRequest =
        MessageRequest(
            templateId = TEMPLATE_ID,
            team = TEAM,
            emailTo = EMAIL_TO,
            subject = SUBJECT,
            templateVariables = TEMPLATE_VARIABLES
        )
}
