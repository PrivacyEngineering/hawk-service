package org.datausagetracing.service.usage

import com.vladmihalcea.hibernate.type.json.JsonBinaryType
import org.hibernate.annotations.DynamicInsert
import org.hibernate.annotations.DynamicUpdate
import org.hibernate.annotations.Type
import org.hibernate.annotations.TypeDef
import java.time.ZonedDateTime
import java.util.*
import javax.persistence.*

@Entity
@DynamicInsert
@DynamicUpdate
@TypeDef(name = "jsonb", typeClass = JsonBinaryType::class)
class Usage {
    @Id
    lateinit var id: UUID

    lateinit var clientRequestTimestamp: ZonedDateTime

    lateinit var clientResponseTimestamp: ZonedDateTime

    lateinit var serverRequestTimestamp: ZonedDateTime

    lateinit var serverResponseTimestamp: ZonedDateTime

    lateinit var endpointId: String

    lateinit var endpointHost: String

    lateinit var endpointProtocol: String

    @Type(type = "jsonb")
    @Column(columnDefinition = "jsonb")
    lateinit var endpointAdditionalProperties: MutableMap<String, String>

    lateinit var initiatorHost: String

    @Type(type = "jsonb")
    @Column(columnDefinition = "jsonb")
    lateinit var initiatorAdditionalProperties: MutableMap<String, String>

    @OneToMany(mappedBy = "usage")
    var fields: MutableList<Field> = mutableListOf()

    @Type(type = "jsonb")
    @Column(columnDefinition = "jsonb")
    lateinit var tags: MutableMap<String, String>
}