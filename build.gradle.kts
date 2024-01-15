import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id("org.springframework.boot") version "3.2.0"
    id("io.spring.dependency-management") version "1.1.4"
    id("org.springdoc.openapi-gradle-plugin") version "1.6.0"
    kotlin("jvm") version "1.9.20"
    kotlin("plugin.spring") version "1.9.20"
    kotlin("plugin.jpa") version "1.9.20"
}

group = "io.hawk"
version = "2.0.0"
java.sourceCompatibility = JavaVersion.VERSION_17

configurations {
    compileOnly {
        extendsFrom(configurations.annotationProcessor.get())
    }
}

repositories {
    maven { url = uri("https://jitpack.io")}
    mavenCentral()
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-actuator")
    implementation("org.springframework.boot:spring-boot-starter-cache")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.springframework.boot:spring-boot-starter-validation")
    implementation("org.springframework.boot:spring-boot-starter-webflux")
    implementation("org.springframework.boot:spring-boot-configuration-processor")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
    implementation("io.projectreactor.kotlin:reactor-kotlin-extensions")
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-reactor")
    implementation("com.vladmihalcea:hibernate-types-60:2.21.1")
    implementation("org.springdoc:springdoc-openapi-starter-webflux-ui:2.0.2")
    implementation("com.github.fkorotkov:k8s-kotlin-dsl:3.0.1")
    implementation("io.fabric8:kubernetes-client:6.9.2")
    implementation(platform("org.testcontainers:testcontainers-bom:1.17.4"))
    implementation("org.testcontainers:junit-jupiter")
    implementation("org.testcontainers:postgresql")
    implementation("com.github.PrivacyEngineering:hawk-dlp:1.0.4")
    implementation("io.micrometer:micrometer-registry-prometheus")
    developmentOnly("org.springframework.boot:spring-boot-devtools")
    runtimeOnly("org.postgresql:postgresql")
    annotationProcessor("org.springframework.boot:spring-boot-configuration-processor")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("io.projectreactor:reactor-test")
}

openApi {
    apiDocsUrl.set("http://localhost:8080/v3/api-docs")
    outputFileName.set("openapi.json")
    waitTimeInSeconds.set(60)
    customBootRun {
        args.set(listOf("--spring.profiles.active=embedded"))
    }
}

tasks.withType<KotlinCompile> {
    kotlinOptions {
        freeCompilerArgs = listOf("-Xjsr305=strict")
        jvmTarget = "17"
    }
}

tasks.withType<Test> {
    useJUnitPlatform()
}
