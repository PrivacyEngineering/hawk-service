package org.datausagetracing.service.usage.insert

interface UsageRequestListener {
    fun modifyUsage(usageRequest: UsageRequest)
}