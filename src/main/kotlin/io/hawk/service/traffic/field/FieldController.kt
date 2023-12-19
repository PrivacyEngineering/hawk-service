package io.hawk.service.traffic.field

import io.swagger.v3.oas.annotations.Operation
import org.springframework.http.HttpStatus
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*
import jakarta.validation.Valid

@Validated
@RestController
@RequestMapping("/api/fields")
@CrossOrigin(origins = arrayOf("*"))
class FieldController(private val fieldService: FieldService) {
    @GetMapping
    @Operation(description = "List all fields")
    fun list() = fieldService.listFields()

    @GetMapping("/{name}")
    @Operation(description = "Get single existing field")
    fun show(@PathVariable name: String) = fieldService.showField(name)

    @PostMapping
    @Operation(description = "Create single field")
    fun create(@Valid @RequestBody request: FieldRequest) = fieldService.insertField(request)

    @PutMapping
    @Operation(description = "Update single existing field")
    fun update(@Valid @RequestBody request: FieldRequest) = fieldService.updateField(request)

    @DeleteMapping("/{name}")
    @Operation(description = "Delete single existing field")
    fun delete(@PathVariable name: String) = fieldService.deleteField(name)

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(RuntimeException::class, IllegalStateException::class)
    fun handleException(exception: Throwable): Map<String, String> {
        exception.printStackTrace()
        return mapOf("error" to (exception.message ?: ""))
    }
}