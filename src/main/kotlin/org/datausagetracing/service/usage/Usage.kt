package org.datausagetracing.service.usage

import java.time.ZonedDateTime
import java.util.*
import javax.persistence.*

@Entity
class Usage {
    @Id
    lateinit var id: UUID

    @Column(columnDefinition= "TIMESTAMP WITH TIME ZONE")
    var clientRequestTimestamp: ZonedDateTime? = null

    @Column(columnDefinition= "TIMESTAMP WITH TIME ZONE")
    var clientResponseTimestamp: ZonedDateTime? = null

    @Column(columnDefinition= "TIMESTAMP WITH TIME ZONE")
    var serverRequestTimestamp: ZonedDateTime? = null

    @Column(columnDefinition= "TIMESTAMP WITH TIME ZONE")
    var serverResponseTimestamp: ZonedDateTime? = null

    var endpointId: String? = null

    var endpointHost: String? = null

    var endpointProtocol: String? = null

    lateinit var initiatorHost: String

    @OneToMany(mappedBy = "usage", cascade = [CascadeType.REMOVE])
    var endpointProperties: MutableList<EndpointProperty> = mutableListOf()

    @OneToMany(mappedBy = "usage", cascade = [CascadeType.REMOVE])
    var initiatorProperties: MutableList<InitiatorProperty> = mutableListOf()

    @OneToMany(mappedBy = "usage", cascade = [CascadeType.REMOVE])
    var fields: MutableList<UsageField> = mutableListOf()

    @OneToMany(mappedBy = "usage", cascade = [CascadeType.REMOVE])
    var tags: MutableList<Tag> = mutableListOf()
}