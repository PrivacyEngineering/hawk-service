package org.datausagetracing.service.usage.insert

import org.apache.logging.log4j.LogManager
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

@Validated
@RestController
@RequestMapping("/api/usages")
class UsageInsertController(
    val usageService: UsageInsertService
) {
    private val logger = LogManager.getLogger(javaClass)

    @PostMapping
    fun postUsage(@Valid @ModelAttribute @RequestBody request: UsageRequest) {
        logger.trace("Usage {}", request)
        usageService.insertUsage(request)
    }

    @PostMapping("/batch")
    fun postUsageBatch(@Valid @ModelAttribute @RequestBody batch: List<UsageRequest>) {
        logger.trace("Batch {}", batch)
        usageService.insertUsages(batch)
    }
    /*

    [
    Usage(id=b7234921-81cd-43e7-b42b-1adb8a27e7db, metadata=Metadata(side=SERVER, phase=REQUEST, timestamp=2022-01-20T18:04:38.740419Z[UTC]), endpoint=Endpoint(id=http:GET:Paskals-iMac-Pro.local:/, host=Paskals-iMac-Pro.local, protocol=HTTP/1.1, additionalProperties={method=GET, path=/}), initiator=Initiator(host=0:0:0:0:0:0:0:1), fields=[Field(format='properties', namespace='header', path='/host', count=1), Field(format='properties', namespace='header', path='/accept', count=1), Field(format='properties', namespace='header', path='/upgrade-insecure-requests', count=1), Field(format='properties', namespace='header', path='/cookie', count=1), Field(format='properties', namespace='header', path='/user-agent', count=1), Field(format='properties', namespace='header', path='/accept-language', count=1), Field(format='properties', namespace='header', path='/accept-encoding', count=1), Field(format='properties', namespace='header', path='/connection', count=1)], tags=Tags(tags={})),
    Usage(id=b7234921-81cd-43e7-b42b-1adb8a27e7db, metadata=Metadata(side=SERVER, phase=RESPONSE, timestamp=2022-01-20T18:04:38.768114Z[UTC]), endpoint=Endpoint(id=null, host=null, protocol=null, additionalProperties={status=200}), initiator=Initiator(host=null), fields=[], tags=Tags(tags={})),

    Usage(id=d40e86db-de8a-4ebb-9de1-8030a11d29bd, metadata=Metadata(side=SERVER, phase=REQUEST, timestamp=2022-01-20T18:04:40.724638Z[UTC]), endpoint=Endpoint(id=http:GET:Paskals-iMac-Pro.local:/, host=Paskals-iMac-Pro.local, protocol=HTTP/1.1, additionalProperties={method=GET, path=/}), initiator=Initiator(host=0:0:0:0:0:0:0:1), fields=[Field(format='properties', namespace='header', path='/host', count=1), Field(format='properties', namespace='header', path='/accept', count=1), Field(format='properties', namespace='header', path='/upgrade-insecure-requests', count=1), Field(format='properties', namespace='header', path='/cookie', count=1), Field(format='properties', namespace='header', path='/user-agent', count=1), Field(format='properties', namespace='header', path='/accept-language', count=1), Field(format='properties', namespace='header', path='/accept-encoding', count=1), Field(format='properties', namespace='header', path='/connection', count=1)], tags=Tags(tags={})),
    Usage(id=d40e86db-de8a-4ebb-9de1-8030a11d29bd, metadata=Metadata(side=SERVER, phase=RESPONSE, timestamp=2022-01-20T18:04:40.726156Z[UTC]), endpoint=Endpoint(id=null, host=null, protocol=null, additionalProperties={status=200}), initiator=Initiator(host=null), fields=[], tags=Tags(tags={}))]
     */
}