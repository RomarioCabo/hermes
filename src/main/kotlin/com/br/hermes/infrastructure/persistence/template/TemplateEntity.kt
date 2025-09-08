package com.br.hermes.infrastructure.persistence.template

import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
@Table(name = "templates")
data class TemplateEntity (
    @Id
    @Column(name = "id_template", nullable = false, length = 255)
    val idTemplate: String = "",

    @Column(name = "time", nullable = false, length = 255)
    val team: String = "",

    @Column(name = "html", nullable = false, columnDefinition = "TEXT")
    val html: String = "",

    @Column(name = "created_at", nullable = false)
    val createdAt: LocalDateTime = LocalDateTime.now(),

    @Column(name = "updated_at")
    val updatedAt: LocalDateTime? = null
)