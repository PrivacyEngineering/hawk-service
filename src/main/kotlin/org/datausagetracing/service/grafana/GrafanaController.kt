package org.datausagetracing.service.grafana

import org.springframework.web.bind.annotation.*

@RestController
@CrossOrigin(origins = arrayOf("*"))
class GrafanaController(
    val handlers: List<MetricHandler>
) {
    @GetMapping
    fun index() = Unit

    @PostMapping("/search")
    fun search(input: Map<String, String>): List<String> {
        return handlers.map(MetricHandler::target)
    }

    @PostMapping("/query")
    fun query(@RequestBody request: QueryRequest): List<QueryResult> {
        return handlers
            .filter { handler -> request.targets.any { it.target == handler.target } }
            .flatMap { it.query(request) }
    }
}