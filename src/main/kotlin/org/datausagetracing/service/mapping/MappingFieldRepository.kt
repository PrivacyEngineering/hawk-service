package org.datausagetracing.service.mapping

import org.springframework.data.jpa.repository.JpaRepository

interface MappingFieldRepository : JpaRepository<MappingField, Int>