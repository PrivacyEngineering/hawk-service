package io.hawk.service.traffic.usage.endpoint

import io.swagger.v3.oas.annotations.Operation
import io.hawk.service.traffic.usage.GroupedUsageField
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/endpoints")
class EndpointController(val endpointService: EndpointService) {
    @GetMapping("/unmapped")
    @Operation(description = "Get list of unmapped endpoint ids")
    fun unmapped(): List<String> {
        return endpointService.fetchUnmappedEndpoints()
    }

    @GetMapping("/fields/grouped")
    @Operation(description = "Get distinct list of usage fields by endpoint id")
    fun groupedFields(@RequestParam endpointId: String): List<GroupedUsageField> {
        return endpointService.fetchGroupedFields(endpointId)
    }
}