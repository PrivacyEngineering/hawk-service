package org.datausagetracing.service.usage

import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.stereotype.Repository
import java.time.ZonedDateTime
import org.springframework.jdbc.core.query
import java.sql.ResultSet

@Repository
class ExplicitUsageRepository(
    val jdbcTemplate: JdbcTemplate
) {
    fun endpointResultsTimeSeries(
        from: ZonedDateTime,
        to: ZonedDateTime,
        interval: Long,
        endpoint: String
    ): Map<Long, Long> = prepareTimeSeries(from, to, interval, "AND endpoint_id = ?", endpoint)

    fun resultsTimeSeries(
        from: ZonedDateTime,
        to: ZonedDateTime,
        interval: Long,
    ): Map<Long, Long> = prepareTimeSeries(from, to, interval)

    private fun prepareTimeSeries(
        rawFrom: ZonedDateTime,
        rawTo: ZonedDateTime,
        interval: Long,
        where: String? = null,
        vararg whereArgs: Any
    ): Map<Long, Long> {
        val from = rawFrom.toLocalDateTime()
        val to = rawTo.toLocalDateTime()
        return jdbcTemplate.query<Map<Long, Long>>(
            """
            SELECT ((EXTRACT(EPOCH FROM COALESCE(server_request_timestamp, client_request_timestamp, '1970-01-01T01:00:00Z')) * 1000)::bigint / ?) as bucket, COUNT(*)
            FROM usage
            WHERE ((server_request_timestamp BETWEEN ? AND ?) OR
                   (client_request_timestamp BETWEEN ? AND ?))
            ${where ?: ""}
            GROUP BY bucket
        """.trimIndent(), interval, from, to, from, to, *whereArgs
        ) { it: ResultSet ->
            val map = mutableMapOf<Long, Long>()
            while(it.next()) {
                map[it.getLong("bucket") * interval] = it.getLong("count")
            }
            return@query map
        }
    }
}