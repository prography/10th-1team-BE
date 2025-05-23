package org.prography.autocomplete.controller

import org.prography.autocomplete.controller.model.AutoCompleteResponseDTO
import org.prography.autocomplete.service.AutoCompleteService
import org.prography.config.response.ApiResponse
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

/**
 *
 */
@RestController
class AutoCompleteController(
    private val service: AutoCompleteService,
) {
    /**
     *
     */
    @GetMapping("/auto")
    fun autocomplete(
        @RequestParam keyword: String,
        @RequestParam(defaultValue = "10") size: Int,
    ): ApiResponse<List<AutoCompleteResponseDTO>> {
        return ApiResponse.success(service.autocompleteByName(keyword, size))
    }
}
