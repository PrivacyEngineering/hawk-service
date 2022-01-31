package org.datausagetracing.service.usage.endpoint

import com.fasterxml.jackson.annotation.JsonAnyGetter
import com.fasterxml.jackson.annotation.JsonInclude

@JsonInclude(JsonInclude.Include.NON_NULL)
data class Endpoint(
    val endpointId: String,
    val host: String?,
    val protocol: String?,
    @JsonAnyGetter
    val additional: Map<String, String>
)