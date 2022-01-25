package org.datausagetracing.service.field

import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*
import org.springframework.web.reactive.function.server.ServerResponse
import org.springframework.web.reactive.function.server.ServerResponse.badRequest
import org.springframework.web.reactive.function.server.json
import reactor.core.publisher.Mono
import javax.validation.Valid

@Validated
@RestController
@RequestMapping("/api/fields")
class FieldController(private val fieldService: FieldService) {
    @GetMapping
    fun list() = fieldService.listFields()

    @PostMapping
    fun create(@Valid request: FieldRequest) = fieldService.insertField(request)

    @PutMapping
    fun update(@Valid request: FieldRequest) = fieldService.updateField(request)

    @DeleteMapping("/{name}")
    fun update(@PathVariable name: String) = fieldService.deleteField(name)

    @ExceptionHandler(RuntimeException::class, IllegalStateException::class)
    fun handleException(exception: Throwable): Mono<ServerResponse> {
        return badRequest().json().bodyValue(mapOf("error" to exception.message))
    }
}