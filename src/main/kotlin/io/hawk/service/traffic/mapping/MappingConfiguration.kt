package io.hawk.service.traffic.mapping

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Configuration

@Configuration
@ConfigurationProperties("mappings")
class MappingConfiguration {
    var json: String? = null
    var mappings: List<MappingInsertRequest> = mutableListOf()
}