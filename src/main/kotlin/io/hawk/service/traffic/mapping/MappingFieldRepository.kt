package io.hawk.service.traffic.mapping

import org.springframework.data.jpa.repository.JpaRepository

interface MappingFieldRepository : JpaRepository<MappingField, Int>