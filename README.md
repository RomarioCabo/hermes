# 📧 Hermes API

**Hermes API** é uma aplicação desenvolvida em **Kotlin com Spring Boot**, cujo objetivo é fornecer uma interface simples e eficiente para o **envio de e-mails**.

Inspirada em **Hermes**, o mensageiro dos deuses na mitologia grega, a API foi criada para ser **rápida, confiável e fácil de integrar** a outros sistemas.

---

## ✨ Funcionalidades

- Envio de e-mails em **texto simples** e **HTML**
- Suporte a **anexos**
- Configuração flexível via `application.yml`
- Estrutura modular em Kotlin, favorecendo **clareza** e **produtividade**

---

## 🛠️ Tecnologias utilizadas

- [Kotlin](https://kotlinlang.org/)
- [Spring Boot](https://spring.io/projects/spring-boot)
- [Jakarta Mail](https://eclipse-ee4j.github.io/mail/) (ou JavaMail)
- Gradle ou Maven

---

## ⚙️ Configuração

No arquivo `application.yml`, configure o servidor SMTP que será utilizado:

```yaml
spring:
  mail:
    host: smtp.seuprovedor.com
    port: 587
    username: seu_email@dominio.com
    password: sua_senha
    properties:
      mail:
        smtp:
          auth: true
          starttls:
            enable: true