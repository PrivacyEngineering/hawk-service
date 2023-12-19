package io.hawk.service.dlp

import jakarta.persistence.DiscriminatorValue
import jakarta.persistence.Entity
import jakarta.persistence.OneToMany

@Entity
@DiscriminatorValue("inspect")
class InspectDlpResult : DlpResult() {
    @OneToMany(mappedBy = "result")
    lateinit var findings: List<DlpFinding>
}