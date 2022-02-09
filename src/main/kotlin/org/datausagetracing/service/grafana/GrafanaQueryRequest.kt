package org.datausagetracing.service.grafana

import java.time.ZonedDateTime

class QueryRequest {
    var panelId: String? = null
    var range: QueryRange? = null
    var intervalMs: Long = 0
    var maxDataPoints: Int? = null
    var targets: MutableList<QueryTarget> = mutableListOf()
    var adhocFilters: MutableList<AdhocFilter> = mutableListOf()

    fun from() = range?.from?.let(ZonedDateTime::parse)

    fun to() = range?.to?.let(ZonedDateTime::parse)

    fun findFilter(key: String): String? = adhocFilters.firstOrNull { it.key == key }?.value

    fun matchesFilters(key: String, value: String?) = adhocFilters
        .filter { it.key == key }
        .all { it.matches(value) }
}

class QueryRange {
    var from: String? = null
    var to: String? = null
}

class QueryTarget {
    lateinit var target: String
    var refId: String? = null
    var type: String? = null
    var payload: Any? = null

    fun payload(name: String): Any? = (payload as? Map<*, *>)?.get(name)
}

class AdhocFilter {
    var key: String? = null
    var operator: String? = null
    var value: String? = null

    fun matches(other: String?): Boolean = when (operator) {
        ">", "<" -> {
            val valueNumber = value?.toDoubleOrNull() ?: 0.0
            val otherNumber = other?.toDoubleOrNull() ?: 0.0
            if (operator == "<") otherNumber < valueNumber else otherNumber > valueNumber
        }
        else -> value == other
    }
}