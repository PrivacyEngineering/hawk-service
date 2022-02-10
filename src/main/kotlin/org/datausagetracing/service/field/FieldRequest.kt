package org.datausagetracing.service.field

import javax.validation.constraints.NotBlank
import javax.validation.constraints.Pattern
import javax.validation.constraints.Size

class FieldRequest {
    @NotBlank
    @Size(min = 1, max = 60)
    @Pattern(regexp = "^[A-Za-z0-9.-_]+$")
    lateinit var name: String

    @Size(max = 255)
    var description: String? = ""

    var personalData: Boolean? = false

    var specialCategoryPersonalData: Boolean? = false

    var legalBases: MutableList<LegalBase> = mutableListOf()

    var legalRequirement: Boolean? = false

    var contractualRegulation: Boolean? = false

    var obligationToProvide: Boolean? = false

    var consequences: String? = ""
}

