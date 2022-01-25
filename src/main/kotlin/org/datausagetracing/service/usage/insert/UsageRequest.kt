package org.datausagetracing.service.usage.insert

import com.fasterxml.jackson.annotation.JsonAnySetter
import java.time.ZonedDateTime
import java.util.*
import javax.validation.constraints.Min
import javax.validation.constraints.NotNull
import javax.validation.constraints.Pattern
import javax.validation.constraints.Size

class UsageRequest {
    @NotNull
    @Pattern(regexp = "[0-9a-f]{8}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{12}")
    lateinit var id: String

    lateinit var metadata: MetadataRequest

    lateinit var endpoint: EndpointRequest

    lateinit var initiator: InitiatorRequest

    var fields: MutableList<FieldRequest> = mutableListOf()

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
    lateinit var timestamp: ZonedDateTime
    override fun toString(): String {
        return "Metadata(side=$side, phase=$phase, timestamp=$timestamp)"
    }
}

class EndpointRequest {
    @Size(max = 255)
    var id: String? = null

    @Size(max = 255)
    var host: String? = null

    @Size(max = 255)
    var protocol: String? = null

    @JsonAnySetter
    var additionalProperties: MutableMap<String, String> = mutableMapOf()

    override fun toString(): String {
        return "Endpoint(id=$id, host=$host, protocol=$protocol, additionalProperties=$additionalProperties)"
    }
}

class InitiatorRequest {
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
    lateinit var format: String

    @NotNull
    @Size(max = 255)
    lateinit var namespace: String

    @NotNull
    @Size(max = 255)
    lateinit var path: String

    @Min(1)
    @NotNull
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