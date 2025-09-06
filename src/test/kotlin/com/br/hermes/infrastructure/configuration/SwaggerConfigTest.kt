package com.br.hermes.infrastructure.configuration

import io.swagger.v3.oas.models.OpenAPI
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class SwaggerConfigTest {

    private val swaggerConfig = SwaggerConfig()

    @Test
    fun `openAPI should create OpenAPI bean with correct info`() {
        val openAPI: OpenAPI = swaggerConfig.openAPI()

        Assertions.assertNotNull(openAPI, "OpenAPI instance should not be null")

        val info = openAPI.info
        Assertions.assertNotNull(info, "Info object should not be null")

        Assertions.assertEquals("Hermes API - Envio de E-mails", info.title)
        Assertions.assertEquals(
            "API REST em Kotlin com Spring Boot para envio de e-mails com suporte a texto, HTML e anexos.",
            info.description
        )
        Assertions.assertEquals("v1", info.version)

        val license = info.license
        Assertions.assertNotNull(license, "License should not be null")
        Assertions.assertEquals("Apache 2.0", license.name)
        Assertions.assertEquals("https://www.apache.org/licenses/LICENSE-2.0", license.url)
    }
}