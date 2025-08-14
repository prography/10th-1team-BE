package org.prography.bff.bookmark.controller

import org.prography.bff.bookmark.controller.model.BookmarkGroup
import org.prography.bff.bookmark.controller.model.BookmarkGroupSaveDto
import org.prography.bff.bookmark.controller.model.BookmarkGroupUpdateDTO
import org.prography.bff.bookmark.controller.model.BookmarkGroupsDTO
import org.prography.bff.bookmark.controller.model.BookmarkMoveDto
import org.prography.bff.bookmark.controller.model.BookmarkPlace
import org.prography.bff.bookmark.controller.model.BookmarksDTO
import org.prography.bff.bookmark.controller.model.UpdatePlaceAtGroup
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

/**
 * 북마크 관련 서비스 컨트롤러
 */
@RestController
@RequestMapping("/bookmark")
class BookmarkControllerImpl(
    private val bookmarkService: BookmarkService,
) : BookmarkController {
    @PostMapping("/group")
    override fun createBookmarkGroup(
        @AuthUser userId: UUID,
        @RequestBody dto: BookmarkGroupSaveDto,
    ): ApiResponse<UUID> {
        val groupId: UUID = bookmarkService.createBookmarkGroup(userId = userId, icon = dto.icon, groupName = dto.groupName)
        return ApiResponse.success(groupId)
    }

    @PatchMapping("/group/{group_id}")
    override fun modifyBookmarkGroup(
        @AuthUser userId: UUID,
        @PathVariable("group_id") groupId: UUID,
        @RequestBody dto: BookmarkGroupUpdateDTO,
    ): ApiResponse<Void> {
        bookmarkService.updateBookmarkGroup(userId = userId, groupId = groupId, groupName = dto.groupName, icon = dto.icon)
        return ApiResponse.success()
    }

    @GetMapping("/group")
    override fun getBookmarkGroups(
        @AuthUser userId: UUID,
    ): ApiResponse<BookmarkGroupsDTO> {
        val placeGroups: List<PlaceGroup> = bookmarkService.getBookmarkGroups(userId)

        if (placeGroups.isEmpty()) {
            ApiResponse.success(BookmarkGroupsDTO())
        }

        val dto =
            BookmarkGroupsDTO(
                total = placeGroups.size.toLong(),
                groups =
                    placeGroups.map {
                        BookmarkGroup(
                            groupId = it.id,
                            groupName = it.name,
                            icon = it.icon,
                            numberOfBookmark = it.total,
                            createAt = it.createdAt,
                            savedAt = it.savedAt,
                        )
                    },
            )

        return ApiResponse.success(dto)
    }

    @GetMapping("/group/{place_id}")
    override fun getBookmarkGroups(
        @AuthUser userId: UUID,
        @PathVariable("place_id") placeId: String,
    ): ApiResponse<List<BookmarkGroup>> {
        val placeGroupWithSaved: PlaceGroupWithSaved =
            bookmarkService.getBookmarkGroups(userId = userId, placeId = placeId)

        val groups: List<BookmarkGroup> =
            placeGroupWithSaved.placeGroups
                .map {
                    BookmarkGroup(
                        groupId = it.id,
                        groupName = it.name,
                        icon = it.icon,
                        numberOfBookmark = it.total,
                        isSaved = placeGroupWithSaved.savedGroupIds.contains(it.id),
                        createAt = it.createdAt,
                        savedAt = it.savedAt,
                    )
                }

        return ApiResponse.success(groups)
    }

    @DeleteMapping("/group/{group_id}")
    override fun deleteBookmarkGroup(
        @AuthUser userId: UUID,
        @PathVariable("group_id") groupId: UUID,
    ): ApiResponse<Void> {
        bookmarkService.deleteBookmarkGroup(userId = userId, groupId = groupId)
        return ApiResponse.success()
    }

    @PutMapping("/place/{place_id}")
    override fun modifyBookmarkAtGroup(
        @AuthUser userId: UUID,
        @PathVariable(name = "place_id") placeId: String,
        @RequestParam(required = false, name = "group_ids") groupIds: List<UUID>?,
    ): ApiResponse<UpdatePlaceAtGroup> {
        bookmarkService.updateBookmarkAtGroup(
            userId = userId,
            placeId = placeId,
            desiredGroupIds =
                groupIds?.toSet()
                    ?: emptySet(),
        )
        return ApiResponse.success(
            UpdatePlaceAtGroup(
                userId = userId,
                placeId = placeId,
                groupIds = groupIds,
            ),
        )
    }

    @DeleteMapping("/group/{group_id}/places")
    override fun removeBookmarksAtGroup(
        @AuthUser userId: UUID,
        @PathVariable(name = "group_id") groupId: UUID,
        @RequestParam(required = true, name = "place_ids") placeIds: List<String>,
    ): ApiResponse<Void> {
        bookmarkService.removeBookmarkAtGroup(userId = userId, groupId = groupId, placeIds = placeIds)
        return ApiResponse.success()
    }

    @PatchMapping("/place/move")
    override fun moveBookmarkAtGroup(
        @AuthUser userId: UUID,
        @RequestBody dto: BookmarkMoveDto,
    ): ApiResponse<Void> {
        bookmarkService.moveBookmarkAtGroup(
            userId = userId,
            targets = dto.targetGroups,
            source = dto.sourceGroup,
            placeIds = dto.placeId,
        )

        return ApiResponse.success()
    }

    @GetMapping("/place/{group_id}")
    override fun getBookmarks(
        @AuthUser userId: UUID,
        @PathVariable(name = "group_id") groupId: UUID,
    ): ApiResponse<BookmarksDTO> {
        val vo: PlaceGroupWithPlaces = bookmarkService.getBookmarks(groupId = groupId)

        val dto =
            BookmarksDTO(
                groupId = vo.placeGroupId,
                groupName = vo.placeGroupName,
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
    override fun savedBookmarks(
        @AuthUser userId: UUID?,
        @PathVariable("place_id") placeId: String,
    ): ApiResponse<Boolean> {
        if (userId == null) {
            return ApiResponse.success(false)
        }

        val saved: Boolean = bookmarkService.isBookmark(userId = userId, placeId = placeId)
        return ApiResponse.success(saved)
    }
}
