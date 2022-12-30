package io.hawk.service.traffic.usage.insert

import com.fasterxml.jackson.annotation.JsonAnySetter
import io.swagger.v3.oas.annotations.media.Schema
import java.time.ZonedDateTime
import java.util.*
import jakarta.validation.constraints.Min
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Pattern
import jakarta.validation.constraints.Size

class UsageRequest {
    @NotNull
    @Schema(description = "unique identifier of the request/response")
    @Pattern(regexp = "[0-9a-f]{8}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{12}")
    lateinit var id: String

    @Schema(description = "Required Information about the sender of this usage")
    lateinit var metadata: MetadataRequest

    @Schema(description = "Information about the server and endpoint")
    lateinit var endpoint: EndpointRequest

    @Schema(description = "Information about the initiator of the request")
    lateinit var initiator: InitiatorRequest

    @Schema(description = "Extracted atomic value references of the messages data")
    var fields: MutableList<FieldRequest> = mutableListOf()

    @Schema(description = "Additional information that is associated with this usage")
    lateinit var tags: TagsRequest

    fun uuid(): UUID = UUID.fromString(id)

    override fun toString(): String {
        return "Usage(id=$id, metadata=$metadata, endpoint=$endpoint, initiator=$initiator, fields=$fields, tags=$tags)"
    }
}

class MetadataRequest {
    @NotNull
    lateinit var side: Side

    @NotNull
    lateinit var phase: Phase

    @NotNull
    @Schema(description = "Date and time when the request was captured, use zoned ISO Date Time if possible")
    lateinit var timestamp: ZonedDateTime

    override fun toString(): String {
        return "Metadata(side=$side, phase=$phase, timestamp=$timestamp)"
    }
}

class EndpointRequest {
    @Size(max = 255)
    @Schema(description = "indexed identifier to uniquely identify this specific endpoint. In case of http this should be http:<method>:<host>:<path>, must be sent at some point")
    var id: String? = null

    @Size(max = 255)
    @Schema(description = "destination host name, must be sent at some point")
    var host: String? = null

    @Size(max = 255)
    @Schema(description = "example http, must be sent at some point")
    var protocol: String? = null

    @JsonAnySetter
    var additionalProperties: MutableMap<String, String> = mutableMapOf()

    override fun toString(): String {
        return "Endpoint(id=$id, host=$host, protocol=$protocol, additionalProperties=$additionalProperties)"
    }
}

class InitiatorRequest {
    @Schema(description = "requestor host name, must be sent at some point")
    var host: String? = null

    @JsonAnySetter
    var additionalProperties: MutableMap<String, String> = mutableMapOf()

    override fun toString(): String {
        return "Initiator(host=$host)"
    }
}

class FieldRequest {
    @NotNull
    @Size(max = 255)
    @Schema(description = "format extracted e.g. properties, json, yaml, xml")
    lateinit var format: String

    @NotNull
    @Size(max = 255)
    @Schema(description = "The namespace in which the data is located, e.g. in case of http header / body")
    lateinit var namespace: String

    @NotNull
    @Size(max = 255)
    @Schema(description = "parsed expression from the origin (xpath, jsonpath)")
    lateinit var path: String

    @Min(1)
    @NotNull
    @Schema(description = "How often that atomic value reference occured")
    var count: Int = 0

    override fun toString(): String {
        return "Field(format='$format', namespace='$namespace', path='$path', count=$count)"
    }
}

class TagsRequest {
    @JsonAnySetter
    var tags: MutableMap<String, String> = mutableMapOf()


    override fun toString(): String {
        return "Tags(tags=$tags)"
    }
}

enum class Side {
    CLIENT, SERVER
}

enum class Phase {
    REQUEST, RESPONSE
}