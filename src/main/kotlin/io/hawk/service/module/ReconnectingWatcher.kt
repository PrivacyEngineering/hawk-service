package io.hawk.service.module

import io.fabric8.kubernetes.client.Watcher
import io.fabric8.kubernetes.client.WatcherException
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

abstract class ReconnectingWatcher<T>: Watcher<T> {
    abstract fun initialize()

    @OptIn(DelicateCoroutinesApi::class)
    override fun onClose(cause: WatcherException?) {
        GlobalScope.launch {
            delay(5_000L)
            initialize()
        }
    }
}