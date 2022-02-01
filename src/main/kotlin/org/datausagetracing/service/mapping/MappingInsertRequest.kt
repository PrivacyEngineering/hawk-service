package org.datausagetracing.service.mapping

import javax.validation.constraints.Min
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotNull
import javax.validation.constraints.Size

class MappingInsertRequest {
    @NotBlank
    @Size(min = 1, max = 255)
    lateinit var endpointId: String

    var fields: List<MappingFieldInsertRequest> = mutableListOf()
}

class MappingFieldInsertRequest {
    @NotBlank
    @Size(min = 1, max = 255)
    lateinit var field: String

    @NotBlank
    @Size(min = 1, max = 255)
    lateinit var side: String

    @NotBlank
    @Size(min = 1, max = 255)
    lateinit var phase: String

    @NotBlank
    @Size(min = 1, max = 255)
    lateinit var namespace: String

    @NotBlank
    @Size(min = 1, max = 255)
    lateinit var format: String

    @NotBlank
    @Size(min = 1, max = 255)
    lateinit var path: String
}

class MappingUpdateRequest {
    @NotBlank
    @Size(min = 1, max = 255)
    lateinit var endpointId: String

    var fields: List<MappingFieldUpdateRequest> = mutableListOf()
}

class MappingFieldUpdateRequest {
    @Min(0)
    @NotNull
    var id: Int = 0

    @NotBlank
    @Size(min = 1, max = 255)
    lateinit var field: String

    @NotBlank
    @Size(min = 1, max = 255)
    lateinit var phase: String

    @NotBlank
    @Size(min = 1, max = 255)
    lateinit var namespace: String

    @NotBlank
    @Size(min = 1, max = 255)
    lateinit var format: String

    @NotBlank
    @Size(min = 1, max = 255)
    lateinit var path: String
}