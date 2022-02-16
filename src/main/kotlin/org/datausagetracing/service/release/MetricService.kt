package org.datausagetracing.service.release

import io.micrometer.prometheus.PrometheusMeterRegistry
import io.prometheus.client.Collector
import org.datausagetracing.service.usage.UsageRepository
import org.springframework.stereotype.Service

@Service
class MetricService(
    private val meterRegistry: PrometheusMeterRegistry,
    private val releaseCollector: ReleaseCollector,
    private val usageRepository: UsageRepository
) {
    private val registry get() = meterRegistry.prometheusRegistry

    init {
        registry.register(object : Collector() {
            override fun collect(): MutableList<MetricFamilySamples> {
                val services = releaseCollector.openReleases()
                    .map { it.target.name to it.start }
                    .distinctBy { it.first }
                    .toMap()
                val unmappedCount = services
                    .mapValues { usageRepository.findUnmappedCount("${it.key}-canary", it.value) }
                val mappedCount = services
                    .mapValues { usageRepository.findMappedCount("${it.key}-canary", it.value) }

                return services.keys.map {
                    val mapped = mappedCount[it]?.toDouble() ?: 0.0
                    val unmapped = unmappedCount[it]?.toDouble() ?: 0.0
                    createMetric("hawk.$it.count", mapped + unmapped)
                    createMetric("hawk.$it.mapped.count", mapped)
                    createMetric("hawk.$it.unmapped.count", unmapped)
                    if (unmapped == 0.0)
                        createMetric("hawk.$it.unmapped.ratio", 0.0)
                    else
                        createMetric("hawk.$it.unmapped.ratio", (mapped + unmapped) / unmapped)

                }.toMutableList()
            }
        })
    }

    private fun createMetric(name: String, value: Double?) = Collector.MetricFamilySamples(
        name,
        Collector.Type.GAUGE,
        "",
        listOf(
            Collector.MetricFamilySamples.Sample(
                name,
                emptyList(),
                emptyList(),
                value ?: 0.0
            )
        )
    )
}