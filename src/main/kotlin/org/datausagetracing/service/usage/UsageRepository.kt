package org.datausagetracing.service.usage

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.repository.CrudRepository
import java.util.*

interface UsageRepository : JpaRepository<Usage, UUID>