package org.datausagetracing.service.testdata

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.asCoroutineDispatcher
import org.apache.logging.log4j.LogManager
import org.datausagetracing.service.usage.*
import org.springframework.context.annotation.Profile
import org.springframework.stereotype.Service
import java.time.ZonedDateTime
import java.util.*
import java.util.concurrent.CompletableFuture
import java.util.concurrent.Executors
import java.util.concurrent.ThreadLocalRandom
import java.util.concurrent.TimeUnit
import javax.annotation.PostConstruct

@Service
@Profile("test-data")
class TestDataService(
    private val usageRepository: UsageRepository,
    private val usageFieldRepository: UsageFieldRepository,
    private val endpointPropertyRepository: EndpointPropertyRepository
) {
    private val batches = 100
    private val batchSize = 10
    private val beginDate = ZonedDateTime.now().minusDays(1)
    private val endDate = ZonedDateTime.now().plusHours(1)
    private val dateDiff = endDate.toEpochSecond() - beginDate.toEpochSecond()
    private val minLatency = 4L
    private val maxLatency = 30L
    private val logger = LogManager.getLogger(javaClass)

    private val testDataScope = CoroutineScope(Executors.newFixedThreadPool(1).asCoroutineDispatcher() + SupervisorJob())

    @PostConstruct
    fun insertRandomUsages() {
        CompletableFuture.runAsync {
            logger.info("Insert Test Data ($batches x $batchSize) = ${batchSize * batchSize}")
            repeat(batches, ::doBatch)
            logger.info("Test Data Insert complete")
        }
    }

    private fun doBatch(index: Int) {
        logger.info("Batch $index...")
        val list = List(batchSize, ::generateUsage)
        usageRepository.saveAllAndFlush(list.map { it.first })
        usageFieldRepository.saveAllAndFlush(list.flatMap { it.second })
        endpointPropertyRepository.saveAllAndFlush(list.flatMap { it.third })
    }

    private fun generateUsage(index: Int): Triple<Usage, List<UsageField>, List<EndpointProperty>> {
        val endpoint = endpoints.random()
        val usage = Usage().apply {
            id = UUID.randomUUID()
            val timestamp = randomTimestamp()
            val latency = randomLatency()
            clientRequestTimestamp = timestamp
            serverRequestTimestamp = timestamp.plusNanos(latency)
            serverResponseTimestamp = timestamp.plusNanos(latency * 2)
            clientResponseTimestamp = timestamp.plusNanos(latency * 3)
            endpointHost = endpoint.service
            endpointId = "http:${endpoint.method}:${endpoint.service}:${endpoint.path}"
            endpointProtocol = "HTTP"
            initiatorHost = endpoint.initiators.random()
        }
        return Triple(usage, endpoint.getFields(usage), endpoint.getEndpointProperties(usage))
    }

    fun randomTimestamp() = beginDate.plusSeconds(dateDiff.random())!!

    fun randomLatency() = TimeUnit.MILLISECONDS.toNanos(random(minLatency, maxLatency))

    fun random(begin: Long, to: Long) = ThreadLocalRandom.current().nextLong(begin, to)

    fun Long.random() = ThreadLocalRandom.current().nextLong(0, this)
}

fun endpoint(block: Endpoint.() -> Unit) = Endpoint().also(block)

class Endpoint(
    var service: String = "",
    var path: String = "",
    var method: String = "",
    var requestFields: MutableSet<String> = mutableSetOf(),
    var responseFields: MutableSet<String> = mutableSetOf(),
    val initiators: MutableSet<String> = mutableSetOf()
) {
    private val rationToTakeField = 0.6

    fun initiator(vararg name: String) {
        initiators.addAll(name)
    }
    fun request(vararg fields: String) {
        requestFields.addAll(fields)
    }

    fun response(vararg fields: String) {
        responseFields.addAll(fields)
    }

    fun getFields(usage: Usage): MutableList<UsageField> {
        val fields = mutableListOf<UsageField>()
        requestFields
            .filter { Math.random() <= rationToTakeField }
            .map {
                UsageField().apply {
                    id = UUID.randomUUID()
                    side = "SERVER"
                    phase = "REQUEST"
                    namespace = "body"
                    format = "json"
                    path = it
                    count = 1
                    this.usage = usage
                }
            }
            .also(fields::addAll)
        responseFields
            .filter { Math.random() <= rationToTakeField }
            .map {
                UsageField().apply {
                    id = UUID.randomUUID()
                    side = "SERVER"
                    phase = "RESPONSE"
                    namespace = "body"
                    format = "json"
                    path = it
                    count = 1
                    this.usage = usage
                }
            }
            .also(fields::addAll)
        return fields
    }

    fun getEndpointProperties(usage: Usage): MutableList<EndpointProperty> {
        return mutableListOf(
            EndpointProperty().apply {
                id = UUID.randomUUID()
                side = "SERVER"
                phase = "REQUEST"
                key = "method"
                value = method
                this.usage = usage
            },
            EndpointProperty().apply {
                id = UUID.randomUUID()
                side = "SERVER"
                phase = "RESPONSE"
                key = "status"
                value = "200"
                this.usage = usage
            }
        )
    }
}

val initiators = setOf(
    "user-service",
    "newsletter-service",
    "front-end",
    "product-service",
    "payment-service",
    "order-service",
    "authentication-service"
)

val endpoints = mutableSetOf(
    endpoint {
        service = "user-service"
        path = "/api/users"
        method = "GET"
        response("$.[*].email")
        response("$.[*].first-name")
        response("$.[*].last-name")
        response("$.[*].date-of-birth")
        response("$.[*].gender")
        initiator("payment-service", "product-service", "front-end")
    },
    endpoint {
        service = "user-service"
        path = "/api/users"
        method = "POST"
        request("$.email")
        request("$.first-name")
        request("$.last-name")
        request("$.date-of-birth")
        request("$.gender")
        request("$.street")
        request("$.zip")
        request("$.country")
        request("$.card-number")
        request("$.card-ccv")
        request("$.card-expiry")
        response("$.id")
        initiator("front-end")
    },
    endpoint {
        service = "user-service"
        path = "/api/users"
        method = "DELETE"
        request("$.id")
        initiator("front-end")
    },
    endpoint {
        service = "newsletter-service"
        path = "/api/newsletter"
        method = "POST"
        request("$.[*].email")
        request("$.[*].last-name")
        request("$.[*].gender")
        response("$.newsletter-id")
        initiator("product-service", "user-service")
    },
    endpoint {
        service = "product-service"
        path = "/api/product"
        method = "GET"
        response("$.[*].category.name")
        response("$.[*].product.name")
        response("$.[*].product.description")
        response("$.[*].product.stock")
        response("$.[*].price")
        initiator("front-end")
    },
    endpoint {
        service = "product-service"
        path = "/api/product"
        method = "POST"
        request("$.category.name")
        request("$.product.name")
        request("$.product.description")
        request("$.product.stock")
        request("$.price")
        initiator("front-end")
    },
    endpoint {
        service = "payment-service"
        path = "/api/payment"
        method = "POST"
        request("$.user.email")
        request("$.user.first-name")
        request("$.user.last-name")
        request("$.user.date-of-birth")
        request("$.user.gender")
        request("$.user.street")
        request("$.user.zip")
        request("$.user.country")
        request("$.user.card-number")
        request("$.user.card-ccv")
        request("$.user.card-expiry")
        request("$.products.[*].name")
        request("$.products.[*].price")
        initiator("front-end", "product-service")
    }
)