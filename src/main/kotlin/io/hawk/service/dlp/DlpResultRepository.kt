package io.hawk.service.dlp

import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface DlpResultRepository : JpaRepository<DlpResult, UUID>