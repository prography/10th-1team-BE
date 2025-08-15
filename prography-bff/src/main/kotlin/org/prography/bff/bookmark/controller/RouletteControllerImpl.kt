package org.prography.bff.bookmark.controller

import org.prography.bff.bookmark.controller.model.roulette.RouletteGroup
import org.prography.bff.bookmark.controller.model.roulette.RouletteGroupSaveDTO
import org.prography.bff.bookmark.controller.model.roulette.RouletteGroupUpdateDTO
import org.prography.bff.bookmark.controller.model.roulette.RouletteGroupWithPlaceDTO
import org.prography.bff.bookmark.controller.model.roulette.RouletteGroupsDTO
import org.prography.bff.bookmark.service.RouletteService
import org.prography.bff.config.response.ApiResponse
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.util.UUID

@RestController
@RequestMapping("/roulette")
class RouletteControllerImpl(
    private val rouletteService: RouletteService,
) : RouletteController {
    override fun createRouletteGroup(
        userId: UUID,
        dto: RouletteGroupSaveDTO,
    ): ApiResponse<UUID> {
        TODO("Not yet implemented")
    }

    override fun modifyRouletteGroup(
        userId: UUID,
        rouletteId: UUID,
        dto: RouletteGroupUpdateDTO,
    ): ApiResponse<Void> {
        TODO("Not yet implemented")
    }

    override fun getRoulette(userId: UUID): ApiResponse<RouletteGroupsDTO> {
        TODO("Not yet implemented")
    }

    override fun getRouletteGroups(
        userId: UUID,
        placeId: String,
    ): ApiResponse<List<RouletteGroup>> {
        TODO("Not yet implemented")
    }

    override fun deleteRouletteGroup(
        userId: UUID,
        rouletteId: UUID,
    ): ApiResponse<Void> {
        TODO("Not yet implemented")
    }

    override fun modifyItemAtRouletteGroup(
        userId: UUID,
        placeId: String,
        rouletteIds: List<UUID>?,
    ) {
        TODO("Not yet implemented")
    }

    override fun getRouletteGroup(
        userId: UUID,
        rouletteId: UUID,
    ): ApiResponse<RouletteGroupWithPlaceDTO> {
        TODO("Not yet implemented")
    }

    override fun addedItemAtRouletteGroup(
        userId: UUID?,
        placeId: String,
    ): ApiResponse<Boolean> {
        TODO("Not yet implemented")
    }
}
