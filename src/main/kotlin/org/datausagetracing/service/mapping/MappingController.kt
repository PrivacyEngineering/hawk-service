package org.datausagetracing.service.mapping

import io.swagger.v3.oas.annotations.Operation
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
    @Operation(description = "List mappings")
    fun list() = mappingService.listMappings().map(::MappingPresentation)

    @GetMapping("/{id}")
    @Operation(description = "Get single existing mapping")
    fun show(@PathVariable id: Int) = mappingService.showMapping(id).let(::MappingPresentation)

    @PostMapping
    @Operation(description = "Create single mapping")
    fun create(@Valid @RequestBody request: MappingInsertRequest) = mappingService.insertMapping(request)

    @PutMapping
    @Operation(description = "Update single existing mapping")
    fun update(@Valid @RequestBody request: MappingUpdateRequest) = mappingService.updateMapping(request)

    @DeleteMapping("/{id}")
    @Operation(description = "Delete single existing mapping")
    fun delete(@PathVariable id: Int) = mappingService.deleteMapping(id)

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(RuntimeException::class, IllegalStateException::class)
    fun handleException(exception: Throwable): Map<String, String> {
        exception.printStackTrace()
        return mapOf("error" to (exception.message ?: ""))
    }
}