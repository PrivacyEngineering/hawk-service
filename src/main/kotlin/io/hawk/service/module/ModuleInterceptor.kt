package io.hawk.service.module

interface ModuleInterceptor<P> {
    fun mutatePayload(payload: P)
}