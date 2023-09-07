package org.datausagetracing.service.grafana.handler.table

import org.datausagetracing.service.grafana.*
import org.datausagetracing.service.field.ExplicitFieldRepository
import org.springframework.stereotype.Service
@Service
class LegalBasesTableMetricHandler(
        private val explicitFieldRepository: ExplicitFieldRepository
) : MetricHandler {
    override val target = "legal_bases"
    override fun query(request: QueryRequest, target: QueryTarget): List<QueryResult> {
        return listOf(
                TableQueryResult(
                        listOf(
                                TableColumn("reference","string"),
                                TableColumn("description", "string")
                        ),
                        explicitFieldRepository.legalBases().map {
                            arrayOf(it.key, it.value)
                        }
                )
        )
    }
}