package org.datausagetracing.service.usage.endpoint

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/endpoints")
class EndpointController(val endpointService: EndpointService) {
    @GetMapping("/unmapped")
    fun unmapped(): List<String> {
        return endpointService.fetchUnmappedEndpoints()
    }
}