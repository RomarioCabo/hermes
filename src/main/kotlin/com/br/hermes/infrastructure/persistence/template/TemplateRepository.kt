package com.br.hermes.infrastructure.persistence.template

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional

@Repository
@Transactional(readOnly = true)
interface TemplateRepository : JpaRepository<TemplateEntity, String> {

    @Query("select t from TemplateEntity t where t.idTemplate = :idTemplate and t.team = :team")
    fun findTemplate(idTemplate: String, team: String): TemplateEntity?
}