package io.hawk.service.traffic.mapping

data class MappingPresentation(
    val id: Int,
    val endpointId: String,
    val fields: List<MappingFieldPresentation>,
    val purposes: List<Purpose>,
    val legitimateInterests: List<String>,
    val recipients: List<String>,
    val storage: List<Storage>,
) {
    constructor(mapping: Mapping) : this(
        mapping.id ?: 0, mapping.endpointId, mapping.fields.map(::MappingFieldPresentation), mapping.purposes, mapping.legitimateInterests, mapping.recipients, mapping.storage
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