package io.hawk.service.traffic.field

import jakarta.validation.constraints.Pattern
import jakarta.validation.constraints.Size

class LegalBase : java.io.Serializable {
    @Size(max = 255)
    @Pattern(regexp = "^[A-Z]*(-?[0-9]*|[a-z]*)*$")
    var reference: String? = ""

    var description: String? = ""
}