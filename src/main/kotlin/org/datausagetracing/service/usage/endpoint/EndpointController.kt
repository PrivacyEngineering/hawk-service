package org.datausagetracing.service.usage.endpoint

import org.datausagetracing.service.usage.GroupedUsageField
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/endpoints")
class EndpointController(val endpointService: EndpointService) {
    @GetMapping("/unmapped")
    fun unmapped(): List<String> {
        return endpointService.fetchUnmappedEndpoints()
    }

    @GetMapping("/fields/grouped")
    fun groupedFields(@RequestParam endpointId: String): List<GroupedUsageField> {
        return endpointService.fetchGroupedFields(endpointId)
    }
}