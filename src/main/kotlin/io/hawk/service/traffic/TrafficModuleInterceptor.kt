package io.hawk.service.traffic

import io.hawk.service.module.ModuleInterceptor
import io.hawk.service.traffic.usage.Usage

interface TrafficModuleInterceptor : ModuleInterceptor<Usage>