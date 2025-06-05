package org.prography.region.controller

import org.prography.config.response.ApiResponse
import org.prography.region.domain.service.RegionService
import org.prography.region.model.RegionDto
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/regions")
class RegionControllerImpl(
    private val regionService: RegionService,
) : RegionController {
    @GetMapping
    override fun getSearchableRegions(): ApiResponse<RegionDto> {
        val regionData = regionService.getRegionData()

        val regionDto = RegionDto.fromDomain(regionData)
        return ApiResponse.success(regionDto)
    }
}
