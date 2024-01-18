package io.hawk.service.dlp

import jakarta.persistence.*

@Entity
@DiscriminatorValue("inspect")
class InspectDlpResult : DlpResult() {
    @OneToMany(mappedBy = "result", cascade = [CascadeType.ALL], fetch = FetchType.EAGER)
    lateinit var findings: List<DlpFinding>
}