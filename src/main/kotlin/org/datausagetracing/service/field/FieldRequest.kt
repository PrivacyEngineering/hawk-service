package org.datausagetracing.service.field

import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotNull
import javax.validation.constraints.Pattern
import javax.validation.constraints.Size

class FieldRequest {
    @NotBlank
    @Size(min = 1, max = 60)
    @Pattern(regexp = "^[A-Za-z0-9.-_]+$")
    lateinit var name: String

    @Size(max = 255)
    var description: String? = ""

    @NotNull
    var personalData: Boolean = false

    @NotNull
    var specialCategoryPersonalData: Boolean = false
}