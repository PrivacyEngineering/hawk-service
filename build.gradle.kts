import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id("org.springframework.boot") version "2.6.2"
    id("io.spring.dependency-management") version "1.0.11.RELEASE"
    kotlin("jvm") version "1.6.10"
    kotlin("plugin.spring") version "1.6.10"
    id("com.avast.gradle.docker-compose")
}

group = "org.datausagetracing"
version = "1.0.6"
java.sourceCompatibility = JavaVersion.VERSION_11

repositories {
    maven { url = uri("https://repo.spring.io/release") }
    maven { url = uri("https://jitpack.io")}
    mavenCentral()
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-actuator")
    implementation("org.springframework.boot:spring-boot-starter-validation")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.springframework.boot:spring-boot-starter-webflux")
    implementation("org.springframework.boot:spring-boot-configuration-processor")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
    implementation("io.projectreactor.kotlin:reactor-kotlin-extensions")
    implementation("com.vladmihalcea:hibernate-types-52:2.2.2")
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-reactor")
    implementation("org.springdoc:springdoc-openapi-webflux-ui:1.6.6")
    implementation("com.github.fkorotkov:k8s-kotlin-dsl:3.1.1")
    implementation("io.fabric8:kubernetes-client:5.0.1")
    implementation("io.micrometer:micrometer-registry-prometheus")
    developmentOnly("org.springframework.boot:spring-boot-devtools")
    runtimeOnly("org.postgresql:postgresql")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("io.projectreactor:reactor-test")
}

tasks.withType<KotlinCompile> {
    kotlinOptions {
        freeCompilerArgs = listOf("-Xjsr305=strict")
        jvmTarget = "11"
    }
}

tasks.getByName<org.springframework.boot.gradle.tasks.bundling.BootBuildImage>("bootBuildImage") {
    targetJavaVersion.set(JavaVersion.VERSION_11)
}

tasks.withType<Test> {
    useJUnitPlatform()
}

dockerCompose {
    isRequiredBy(tasks.bootRun)
    useComposeFiles.add("src/docker/postgresql/postgresql.yml")
    useComposeFiles.add("src/docker/grafana/grafana.yml")
    startedServices.add("postgresql")
    startedServices.add("grafana")
}
