package io.hawk.service.module.release

import io.hawk.service.traffic.usage.insert.UsageRequest
import io.hawk.service.traffic.usage.insert.UsageRequestListener
import org.springframework.stereotype.Service
import java.time.format.DateTimeFormatter

@Service
class UsageRequestReleaseListener(private val resourceCollector: ReleaseCollector) :
    UsageRequestListener {
    override fun modifyUsage(usageRequest: UsageRequest) {
        if (usageRequest.endpoint.host == null) return
        val release = resourceCollector.releases.values
            .firstOrNull { it.target.name == usageRequest.endpoint.host } ?: return

        usageRequest.tags.apply {
            tags["releases.type"] = release.type
            tags["releases.name"] = release.name
            tags["releases.start"] = release.start.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME)
            release.end?.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME)
                ?.also { tags["releases.end"] = it }
        }
    }
}