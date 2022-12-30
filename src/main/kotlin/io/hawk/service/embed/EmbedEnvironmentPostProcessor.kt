package io.hawk.service.embed

import org.slf4j.LoggerFactory
import org.springframework.boot.SpringApplication
import org.springframework.boot.env.EnvironmentPostProcessor
import org.springframework.core.env.ConfigurableEnvironment
import org.springframework.core.env.MapPropertySource
import org.testcontainers.containers.GenericContainer
import org.testcontainers.containers.PostgreSQLContainer

/**
 * Config databases / needed services via. Testcontainers on local Docker
 * if embedded profile is active.
 * This must be temporarily here, that we can generate OpenAPI specs via. Springdoc,
 * as we can't generate them in the Gradle test.
 */
class EmbedEnvironmentPostProcessor : EnvironmentPostProcessor {
    private val logger = LoggerFactory.getLogger(javaClass)

    override fun postProcessEnvironment(
        environment: ConfigurableEnvironment,
        application: SpringApplication
    ) {
        if ("embedded" !in environment.activeProfiles) return
        logger.info("Initializing embedded...")
        val postgres: PostgreSQLContainer<*> = PostgreSQLContainer("postgres:13")
            .withDatabaseName("hawk")
            .withUsername("hawk")
            .withPassword("hawk")
        val grafana: GenericContainer<*> = GenericContainer("grafana/grafana")
            .withEnv("GF_INSTALL_PLUGINS", "simpod-json-datasource 0.3.0,novatec-sdg-panel")
            .withExposedPorts(3000)
        postgres.start()
        grafana.start()

        environment.propertySources.addFirst(
            MapPropertySource(
                "embedded", mapOf(
                    "spring.datasource.url" to postgres.jdbcUrl,
                    "spring.datasource.username" to postgres.username,
                    "spring.datasource.password" to postgres.password,
                    "spring.jpa.hibernate.ddl-auto" to "create"
                )
            )
        )
        logger.info("Embedded initialized. Grafana Port is ${grafana.getMappedPort(3000)}")
    }
}