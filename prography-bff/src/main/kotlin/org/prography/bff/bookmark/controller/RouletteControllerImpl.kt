package org.prography.bff.bookmark.controller

import org.prography.bff.bookmark.controller.model.BookmarkPlace
import org.prography.bff.bookmark.controller.model.roulette.RouletteGroup
import org.prography.bff.bookmark.controller.model.roulette.RouletteGroupSaveDTO
import org.prography.bff.bookmark.controller.model.roulette.RouletteGroupUpdateDTO
import org.prography.bff.bookmark.controller.model.roulette.RouletteGroupWithPlaceDTO
import org.prography.bff.bookmark.controller.model.roulette.RouletteGroupsDTO
import org.prography.bff.bookmark.service.BookmarkService
import org.prography.bff.bookmark.service.model.PlaceGroup
import org.prography.bff.bookmark.service.model.PlaceGroupWithPlaces
import org.prography.bff.bookmark.service.model.PlaceGroupWithSaved
import org.prography.bff.config.response.ApiResponse
import org.prography.bff.config.security.AuthUser
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import java.util.UUID

@RestController
@RequestMapping("/roulette")
class RouletteControllerImpl(
    private val bookmarkService: BookmarkService,
) : RouletteController {
    @PostMapping("")
    override fun createRouletteGroup(
        @AuthUser userId: UUID,
        @RequestBody dto: RouletteGroupSaveDTO,
    ): ApiResponse<UUID> {
        val rouletteId =
            bookmarkService.createRouletteGroup(
                userId = userId,
                icon = dto.icon,
                rouletteName = dto.name,
            )
        return ApiResponse.success(rouletteId)
    }

    @PatchMapping("/{id}")
    override fun modifyRouletteGroup(
        @AuthUser userId: UUID,
        @PathVariable("id") rouletteId: UUID,
        @RequestBody dto: RouletteGroupUpdateDTO,
    ): ApiResponse<Void> {
        bookmarkService.updateRoulette(userId = userId, name = dto.name, icon = dto.icon, rouletteId = rouletteId)
        return ApiResponse.success()
    }

    @GetMapping("")
    override fun getRouletteGroups(
        @AuthUser userId: UUID,
    ): ApiResponse<RouletteGroupsDTO> {
        val placeGroups: List<PlaceGroup> = bookmarkService.getRouletteGroups(userId = userId)

        if (placeGroups.isEmpty()) {
            ApiResponse.success(RouletteGroupsDTO())
        }

        val dto =
            RouletteGroupsDTO(
                total = placeGroups.size.toLong(),
                groups =
                    placeGroups.map {
                        RouletteGroup(
                            id = it.id,
                            name = it.name,
                            icon = it.icon,
                            numberOfItem = it.total,
                            createAt = it.createdAt,
                            savedAt = it.savedAt,
                        )
                    },
            )

        return ApiResponse.success(dto)
    }

    @GetMapping("/{place_id}")
    override fun getRouletteGroups(
        @AuthUser userId: UUID,
        @PathVariable("place_id") placeId: String,
    ): ApiResponse<List<RouletteGroup>> {
        val placeGroupWithSaved: PlaceGroupWithSaved = bookmarkService.getRouletteGroups(userId = userId, placeId = placeId)

        val rouletteGroups =
            placeGroupWithSaved.placeGroups.map {
                RouletteGroup(
                    id = it.id,
                    name = it.name,
                    icon = it.icon,
                    numberOfItem = it.total,
                    isAdded = placeGroupWithSaved.savedGroupIds.contains(it.id),
                    createAt = it.createdAt,
                    savedAt = it.savedAt,
                )
            }

        return ApiResponse.success(rouletteGroups)
    }

    @DeleteMapping("/{id}")
    override fun deleteRouletteGroup(
        @AuthUser userId: UUID,
        @PathVariable("id") rouletteId: UUID,
    ): ApiResponse<Void> {
        bookmarkService.deleteBookmarkGroup(userId = userId, groupId = rouletteId)
        return ApiResponse.success()
    }

    @PutMapping("/{place_id}")
    override fun modifyItemAtRouletteGroup(
        @AuthUser userId: UUID,
        @PathVariable("place_id") placeId: String,
        @RequestParam rouletteIds: List<UUID>?,
    ): ApiResponse<Void> {
        bookmarkService.updateItemAtRoulette(
            userId = userId,
            placeId = placeId,
            desiredRouletteIds = rouletteIds?.toSet() ?: emptySet(),
        )

        return ApiResponse.success()
    }

    @GetMapping("detail/{id}")
    override fun getItemsAtRouletteGroup(
        @AuthUser userId: UUID,
        @PathVariable("id") rouletteId: UUID,
    ): ApiResponse<RouletteGroupWithPlaceDTO> {
        val vo: PlaceGroupWithPlaces = bookmarkService.getBookmarks(groupId = rouletteId)

        val dto =
            RouletteGroupWithPlaceDTO(
                id = vo.placeGroupId,
                name = vo.placeGroupName,
                icon = vo.placeGroupIcon,
                total = vo.numberOfPlace,
                places =
                    vo.places.map {
                        BookmarkPlace(
                            placeId = it.placeId,
                            placeName = it.placeName,
                            roadAddress = it.roadAddress,
                            category = it.category.split(" > ").last(),
                            legal = it.legal,
                            savedAt = it.savedAt,
                        )
                    },
            )

        return ApiResponse.success(dto)
    }

    @GetMapping("/saved/{place_id}")
    override fun addedItemAtRouletteGroup(
        @AuthUser userId: UUID?,
        @PathVariable("place_id") placeId: String,
    ): ApiResponse<Boolean> {
        if (userId == null) {
            return ApiResponse.success(false)
        }

        val added: Boolean = bookmarkService.isRouletteBookmark(userId = userId, placeId = placeId)
        return ApiResponse.success(added)
    }
}
