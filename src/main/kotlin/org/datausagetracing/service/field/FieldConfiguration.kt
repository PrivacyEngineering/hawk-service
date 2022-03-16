package org.datausagetracing.service.field

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Configuration

@Configuration
@ConfigurationProperties("fields")
class FieldConfiguration {
    var json: String? = null
    var fields: List<FieldRequest> = mutableListOf()
}