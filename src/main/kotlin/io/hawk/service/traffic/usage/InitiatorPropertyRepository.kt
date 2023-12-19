package io.hawk.service.traffic.usage

import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface InitiatorPropertyRepository: JpaRepository<InitiatorProperty, UUID>