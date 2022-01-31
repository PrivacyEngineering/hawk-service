package org.datausagetracing.service.field

import org.springframework.http.HttpStatus
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

@Validated
@RestController
@CrossOrigin(origins = ["http://localhost:3000"])
@RequestMapping("/api/fields")
class FieldController(private val fieldService: FieldService) {
    @GetMapping
    fun list() = fieldService.listFields()

    @GetMapping("/{name}")
    fun show(@PathVariable name: String) = fieldService.showField(name)

    @PostMapping
    fun create(@Valid request: FieldRequest) = fieldService.insertField(request)

    @PutMapping
    fun update(@Valid request: FieldRequest) = fieldService.updateField(request)

    @DeleteMapping("/{name}")
    fun delete(@PathVariable name: String) = fieldService.deleteField(name)

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(RuntimeException::class, IllegalStateException::class)
    fun handleException(exception: Throwable): Map<String, String> {
        exception.printStackTrace()
        return mapOf("error" to (exception.message ?: ""))
    }
}