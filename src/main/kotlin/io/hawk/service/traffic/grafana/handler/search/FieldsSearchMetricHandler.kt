package io.hawk.service.traffic.grafana.handler.search

import io.hawk.service.traffic.field.Field
import io.hawk.service.traffic.field.FieldRepository
import io.hawk.service.traffic.grafana.MetricHandler
import org.springframework.stereotype.Service

@Service
class FieldsSearchMetricHandler(private val fieldRepository: FieldRepository) : MetricHandler {
    override val target = "fields"

    override fun search(): List<String> = fieldRepository.findAll().map(Field::name)
}