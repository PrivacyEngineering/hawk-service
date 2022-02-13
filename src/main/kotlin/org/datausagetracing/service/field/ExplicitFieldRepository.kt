package org.datausagetracing.service.field

import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.stereotype.Repository
import java.sql.ResultSet
import java.time.ZonedDateTime

@Repository
class ExplicitFieldRepository(
    private val jdbcTemplate: JdbcTemplate
) {
    fun fieldRequests() : Map<String, Long> {
        return jdbcTemplate.query<Map<String, Long>>(
        """
            SELECT field.name, sum(uf.count) FROM field
            LEFT JOIN mapping_field mf on field.id = mf.field_id
            LEFT JOIN usage u on u.endpoint_id = mf.endpoint_id
            LEFT JOIN usage_field uf on
                uf.usage_id = u.id AND
                uf.phase = mf.phase AND
                uf.namespace = mf.namespace AND
                uf.format = mf.format AND
                uf.path = mf.path
            GROUP BY field.name
        """.trimIndent()) { it: ResultSet ->
            val map = mutableMapOf<String, Long>()
            while(it.next()) {
                map[it.getString(1)] = it.getLong(2)
            }
            return@query map
        } ?: emptyMap()
    }

    fun fieldRequests(fieldNames: Array<String>) : Map<String, Long> {
        return jdbcTemplate.query<Map<String, Long>>(
            """
            SELECT field.name, sum(uf.count) FROM field
            LEFT JOIN mapping_field mf on field.id = mf.field_id
            LEFT JOIN usage u on u.endpoint_id = mf.endpoint_id
            LEFT JOIN usage_field uf on
                uf.usage_id = u.id AND
                uf.phase = mf.phase AND
                uf.namespace = mf.namespace AND
                uf.format = mf.format AND
                uf.path = mf.path
            WHERE field.name IN (${fieldNames.joinToString { "?" }})
            GROUP BY field.name
        """.trimIndent(), fieldNames) { it: ResultSet ->
            val map = mutableMapOf<String, Long>()
            while(it.next()) {
                map[it.getString(1)] = it.getLong(2)
            }
            return@query map
        } ?: emptyMap()
    }

    fun fieldRequests(rawFrom: ZonedDateTime,
                      rawTo: ZonedDateTime,
                      interval: Long,) : Map<Long, Long> {
        val from = rawFrom.toLocalDateTime()
        val to = rawTo.toLocalDateTime()

        return jdbcTemplate.query<Map<Long, Long>>(
            """
                SELECT ((EXTRACT(EPOCH FROM COALESCE(u.server_request_timestamp, u.client_request_timestamp, '1970-01-01T01:00:00Z')) * 1000)::bigint / ?) as bucket, sum(uf.count)
                FROM field
                LEFT JOIN mapping_field mf on field.id = mf.field_id
                LEFT JOIN usage u on u.endpoint_id = mf.endpoint_id
                LEFT JOIN usage_field uf on
                    uf.usage_id = u.id AND
                    uf.phase = mf.phase AND
                    uf.namespace = mf.namespace AND
                    uf.format = mf.format AND
                    uf.path = mf.path
                WHERE ((server_request_timestamp BETWEEN ? AND ?) OR
                   (client_request_timestamp BETWEEN ? AND ?))
                GROUP BY bucket;
        """.trimIndent(), arrayOf(interval, from, to, from, to)) { it: ResultSet ->
            val map = mutableMapOf<Long, Long>()
            while(it.next()) {
                map[it.getLong(1) * interval] = it.getLong(2)
            }
            return@query map
        } ?: emptyMap()
    }

    fun fieldRequests(rawFrom: ZonedDateTime,
                      rawTo: ZonedDateTime,
                      interval: Long,
                      fieldNames: Array<String>) : Map<Long, Long> {
        val from = rawFrom.toLocalDateTime()
        val to = rawTo.toLocalDateTime()

        return jdbcTemplate.query<Map<Long, Long>>(
            """
                SELECT ((EXTRACT(EPOCH FROM COALESCE(u.server_request_timestamp, u.client_request_timestamp, '1970-01-01T01:00:00Z')) * 1000)::bigint / ?) as bucket, sum(uf.count)
                FROM field
                LEFT JOIN mapping_field mf on field.id = mf.field_id
                LEFT JOIN usage u on u.endpoint_id = mf.endpoint_id
                LEFT JOIN usage_field uf on
                    uf.usage_id = u.id AND
                    uf.phase = mf.phase AND
                    uf.namespace = mf.namespace AND
                    uf.format = mf.format AND
                    uf.path = mf.path
                WHERE ((server_request_timestamp BETWEEN ? AND ?) OR
                   (client_request_timestamp BETWEEN ? AND ?)) AND
                   field.name IN (${fieldNames.joinToString { "?" }})
                GROUP BY bucket;
        """.trimIndent(), arrayOf(interval, from, to, from, to, *fieldNames)) { it: ResultSet ->
            val map = mutableMapOf<Long, Long>()
            while(it.next()) {
                map[it.getLong(1) * interval] = it.getLong(2)
            }
            return@query map
        } ?: emptyMap()
    }
}