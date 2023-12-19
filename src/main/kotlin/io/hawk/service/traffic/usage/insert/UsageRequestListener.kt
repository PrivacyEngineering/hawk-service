package io.hawk.service.traffic.usage.insert

interface UsageRequestListener {
    fun modifyUsage(usageRequest: UsageRequest)
}