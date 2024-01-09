package io.hawk.service.dlp

import jakarta.persistence.CascadeType
import jakarta.persistence.DiscriminatorValue
import jakarta.persistence.Entity
import jakarta.persistence.OneToMany

@Entity
@DiscriminatorValue("inspect")
class InspectDlpResult : DlpResult() {
    @OneToMany(mappedBy = "result", cascade = [CascadeType.ALL])
    lateinit var findings: List<DlpFinding>
}