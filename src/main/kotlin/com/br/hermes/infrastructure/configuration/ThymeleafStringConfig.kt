package com.br.hermes.infrastructure.configuration

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.thymeleaf.TemplateEngine
import org.thymeleaf.templateresolver.StringTemplateResolver
import org.thymeleaf.templatemode.TemplateMode

@Configuration
class ThymeleafStringConfig {

    @Bean
    fun stringTemplateEngine(): TemplateEngine {
        val resolver = StringTemplateResolver().apply {
            templateMode = TemplateMode.HTML
            isCacheable = false
        }

        return TemplateEngine().apply {
            setTemplateResolver(resolver)
        }
    }
}