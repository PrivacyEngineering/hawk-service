package io.hawk.service.traffic.usage.insert

import io.swagger.v3.oas.annotations.Operation
import org.apache.logging.log4j.LogManager
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*
import jakarta.validation.Valid

@Validated
@RestController
@RequestMapping("/api/usages")
class UsageInsertController(
    val usageService: UsageInsertService
) {
    private val logger = LogManager.getLogger(javaClass)

    @PostMapping
    @Operation(description = "Create / update usage")
    fun postUsage(@Valid @ModelAttribute @RequestBody request: UsageRequest) {
        logger.trace("Usage {}", request)
        usageService.insertUsage(request)
    }

    @PostMapping("/batch")
    @Operation(description = "Create / update multiple usages")
    fun postUsageBatch(@Valid @ModelAttribute @RequestBody batch: List<UsageRequest>) {
        logger.trace("Batch {}", batch)
        usageService.insertUsages(batch)
    }
}