package org.datausagetracing.service.mapping

data class MappingPresentation(
    val id: Int,
    val endpointId: String,
    val fields: List<MappingFieldPresentation>
) {
    constructor(mapping: Mapping) : this(
        mapping.id ?: 0, mapping.endpointId, mapping.fields.map(::MappingFieldPresentation)
    )
}

data class MappingFieldPresentation(
    val id: Int? = null,
    val field: String,
    val phase: String,
    val namespace: String,
    val format: String,
    val path: String
) {
    constructor(mappingField: MappingField) : this(
        mappingField.id,
        mappingField.field.name,
        mappingField.phase,
        mappingField.namespace,
        mappingField.format,
        mappingField.path
    )
}