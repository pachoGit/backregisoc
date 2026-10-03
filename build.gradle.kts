import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.springframework.boot.gradle.tasks.run.BootRun

plugins {
    id("org.springframework.boot") version "3.4.1"
    id("io.spring.dependency-management") version "1.1.7"
    kotlin("jvm") version "2.1.0"
    kotlin("plugin.spring") version "2.1.0"
    kotlin("plugin.jpa") version "2.1.0"
}

group = "com.regisoc"
version = "0.0.1-SNAPSHOT"

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(21)
    }
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.springframework.boot:spring-boot-starter-validation")
    implementation("org.springframework.boot:spring-boot-starter-security")
    implementation("org.springframework.boot:spring-boot-starter-actuator")
    implementation("org.flywaydb:flyway-core")
    implementation("org.flywaydb:flyway-mysql")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
    implementation("org.jetbrains.kotlin:kotlin-reflect")

    implementation("io.jsonwebtoken:jjwt-api:0.12.6")
    runtimeOnly("io.jsonwebtoken:jjwt-impl:0.12.6")
    runtimeOnly("io.jsonwebtoken:jjwt-jackson:0.12.6")

    runtimeOnly("com.mysql:mysql-connector-j")

    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("org.springframework.security:spring-security-test")
    testImplementation("org.jetbrains.kotlin:kotlin-test-junit5")
    testImplementation("io.mockk:mockk:1.13.13")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

kotlin {
    compilerOptions {
        freeCompilerArgs.add("-Xjsr305=strict")
        jvmTarget.set(JvmTarget.JVM_21)
    }
}

tasks.withType<Test> {
    useJUnitPlatform()
}

// Perfil por defecto para `bootRun`: local. Se respeta SPRING_PROFILES_ACTIVE si ya viene definida.
tasks.named<BootRun>("bootRun") {
    val activeProfile = System.getenv("SPRING_PROFILES_ACTIVE")
        ?: System.getProperty("spring.profiles.active", "local")
    args("--spring.profiles.active=$activeProfile")
}

fun registerBootRunFor(profile: String, description: String) {
    tasks.register<BootRun>("bootRun${profile.replaceFirstChar { it.uppercase() }}") {
        group = "application"
        this.description = description
        mainClass.set("com.regisoc.RegisocApplicationKt")
        classpath = project.extensions.getByType<org.gradle.api.tasks.SourceSetContainer>()
            .getByName("main").runtimeClasspath
        args("--spring.profiles.active=$profile")
    }
}

registerBootRunFor("local", "Arranca la app con el perfil local (MySQL localhost, ver .env.example).")
registerBootRunFor("dev", "Arranca la app con el perfil dev (MySQL del ambiente de desarrollo).")
registerBootRunFor("prod", "Arranca la app con el perfil prod (variables DB_URL/JWT_SECRET obligatorias).")
