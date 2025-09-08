plugins {
    val kotlinVersion = "1.9.25"
    val springBootVersion = "3.5.5"
    val dependencyManagementVersion = "1.1.7"

    kotlin("jvm") version kotlinVersion
    kotlin("plugin.spring") version kotlinVersion
    id("org.springframework.boot") version springBootVersion
    id("io.spring.dependency-management") version dependencyManagementVersion
    jacoco
}

group = "com.br"
version = "0.0.1-SNAPSHOT"
description = "hermes"

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(17)
    }
}

repositories {
    mavenCentral()
}

val mockitoVersion = "5.5.0"
val mockitoKotlinVersion = "5.1.0"
val springdocVersion = "2.8.9"
val fluentHcVersion = "4.5.14"
val commonsIoVersion = "2.20.0"
val modelMapperVersion = "3.2.4"
val springSecurityCryptoVersion = "6.4.4"
val commonsCodecVersion = "1.19.0"
val ognlVersion = "3.2.21"

fun DependencyHandlerScope.coreDependencies() {
    implementation("org.springframework.boot:spring-boot-starter-web") {
        exclude(group = "org.springframework.boot", module = "spring-boot-starter-tomcat")
    }
    implementation("org.springframework.boot:spring-boot-starter-data-jpa") {
        exclude(group = "org.flywaydb", module = "flyway-core")
        exclude(group = "org.springframework.boot", module = "spring-boot-starter-tomcat")
    }
    implementation("org.springframework.boot:spring-boot-starter-undertow")
    implementation("org.springframework.boot:spring-boot-starter-actuator")
    implementation("org.springframework.boot:spring-boot-starter-validation")
    implementation("org.springframework.boot:spring-boot-starter-mail")
    implementation("org.springframework.boot:spring-boot-starter-thymeleaf")
    implementation("ognl:ognl:$ognlVersion")
}

fun DependencyHandlerScope.databaseDependencies() {
    implementation("com.zaxxer:HikariCP")
    runtimeOnly("org.postgresql:postgresql")
    testImplementation("com.h2database:h2")
    implementation("org.flywaydb:flyway-core")
    implementation("org.flywaydb:flyway-database-postgresql")
}

fun DependencyHandlerScope.documentationDependencies() {
    implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui:$springdocVersion")
    implementation("org.springdoc:springdoc-openapi-starter-webmvc-api:$springdocVersion")
}

fun DependencyHandlerScope.utilityDependencies() {
    implementation("com.fasterxml.jackson.core:jackson-annotations")
    implementation("org.modelmapper:modelmapper:$modelMapperVersion")
    implementation("org.apache.httpcomponents:fluent-hc:$fluentHcVersion")
    testImplementation("org.apache.httpcomponents:httpclient:$fluentHcVersion")
    implementation("commons-io:commons-io:$commonsIoVersion")
    implementation("org.springframework.security:spring-security-crypto:$springSecurityCryptoVersion")
    implementation("commons-codec:commons-codec:$commonsCodecVersion")
}

fun DependencyHandlerScope.kotlinDependencies() {
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
}

fun DependencyHandlerScope.devDependencies() {
    developmentOnly("org.springframework.boot:spring-boot-devtools")
}

fun DependencyHandlerScope.testDependencies() {
    testImplementation("org.springframework.boot:spring-boot-starter-test") {
        exclude(group = "org.mockito", module = "mockito-core")
    }
    testImplementation("org.jetbrains.kotlin:kotlin-test-junit5")
    testImplementation("org.mockito:mockito-core:$mockitoVersion")
    testImplementation("org.mockito:mockito-junit-jupiter:$mockitoVersion")
    testImplementation("org.mockito.kotlin:mockito-kotlin:$mockitoKotlinVersion")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

dependencies {
    coreDependencies()
    databaseDependencies()
    documentationDependencies()
    utilityDependencies()
    kotlinDependencies()
    devDependencies()
    testDependencies()
}

kotlin {
    compilerOptions {
        freeCompilerArgs.addAll("-Xjsr305=strict")
        jvmTarget.set(org.jetbrains.kotlin.gradle.dsl.JvmTarget.JVM_17)
    }
}

tasks.withType<Test> {
    useJUnitPlatform()
}

jacoco {
    toolVersion = "0.8.12"
}

tasks.jacocoTestReport {
    dependsOn(tasks.test)
    reports {
        xml.required.set(true)
        html.required.set(true)
    }
}