package org.datausagetracing.service.grafana.handler.search

import org.datausagetracing.service.field.Field
import org.datausagetracing.service.field.FieldRepository
import org.datausagetracing.service.grafana.MetricHandler
import org.springframework.stereotype.Service

@Service
class FieldsSearchMetricHandler(private val fieldRepository: FieldRepository) : MetricHandler {
    override val target = "fields"

    override fun search(): List<String> = fieldRepository.findAll().map(Field::name)
}