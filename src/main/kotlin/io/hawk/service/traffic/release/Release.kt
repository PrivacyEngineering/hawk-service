package io.hawk.service.traffic.release

import java.time.LocalDateTime

data class Release(
    val target: TargetIdentifier,
    val type: String,
    val name: String,
    val start: LocalDateTime,
    val end: LocalDateTime? = null
)

data class TargetIdentifier(
    val namespace: String,
    val apiVersion: String,
    val kind: String,
    val name: String,
)