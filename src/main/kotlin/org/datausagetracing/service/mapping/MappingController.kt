package org.datausagetracing.service.mapping

import org.springframework.http.HttpStatus
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

@Validated
@RestController
@RequestMapping("/api/mappings")
class MappingController(
    val mappingService: MappingService
) {
    @GetMapping
    fun list() = mappingService.listMappings()

    @GetMapping("/{id}")
    fun show(@PathVariable id: Int) = mappingService.showMapping(id)

    @PostMapping
    fun create(@Valid request: MappingInsertRequest) = mappingService.insertMapping(request)

    @PutMapping
    fun update(@Valid request: MappingUpdateRequest) = mappingService.updateMapping(request)

    @DeleteMapping("/{id}")
    fun delete(@PathVariable id: Int) = mappingService.deleteMapping(id)

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(RuntimeException::class, IllegalStateException::class)
    fun handleException(exception: Throwable): Map<String, String> {
        exception.printStackTrace()
        return mapOf("error" to (exception.message ?: ""))
    }
}