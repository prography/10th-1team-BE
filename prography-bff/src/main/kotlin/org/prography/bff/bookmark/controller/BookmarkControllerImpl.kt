package org.prography.bff.bookmark.controller

import org.prography.bff.bookmark.controller.model.BookmarkGroupInfoDto
import org.prography.bff.bookmark.controller.model.BookmarkGroupSaveDto
import org.prography.bff.bookmark.controller.model.BookmarkInfoDto
import org.prography.bff.bookmark.controller.model.BookmarkMoveDto
import org.prography.bff.bookmark.service.BookmarkService
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
        bookmarkService.createBookmarkGroup(userId = userId, icon = dto.icon, groupName = dto.groupName)
        return ApiResponse.success(UUID.randomUUID())
    }

    @GetMapping("/group")
    override fun getBookmarkGroups(
        @AuthUser userId: UUID,
    ): ApiResponse<List<BookmarkGroupInfoDto>> {
        val groups: List<BookmarkGroupInfoDto> =
            bookmarkService.getBookmarkGroups(userId)
                .map {
                    BookmarkGroupInfoDto(
                        groupId = it.id,
                        groupName = it.name,
                        icon = it.icon,
                        numberOfBookmark = it.total,
                        createAt = it.createdAt,
                        savedAt = it.savedAt,
                    )
                }

        return ApiResponse.success(groups)
    }

    @GetMapping("/group/{placeId}")
    override fun getBookmarkGroups(
        @AuthUser userId: UUID,
        @PathVariable("placeId") placeId: String,
    ): ApiResponse<List<BookmarkGroupInfoDto>> {
        val vo: PlaceGroupWithSaved =
            bookmarkService.getBookmarkGroups(userId = userId, placeId = placeId)

        val groups: List<BookmarkGroupInfoDto> =
            vo.placeGroups
                .map {
                    BookmarkGroupInfoDto(
                        groupId = it.id,
                        groupName = it.name,
                        icon = it.icon,
                        numberOfBookmark = it.total,
                        isSaved = vo.savedGroupIds.contains(it.id),
                        createAt = it.createdAt,
                        savedAt = it.savedAt,
                    )
                }

        return ApiResponse.success(groups)
    }

    @DeleteMapping("/group/{groupId}")
    override fun deleteBookmarkGroup(
        @AuthUser userId: UUID,
        @PathVariable("groupId") groupId: UUID,
    ): ApiResponse<Void> {
        bookmarkService.deleteBookmarkGroup(userId = userId, groupId = groupId)
        return ApiResponse.success()
    }

    @PutMapping("/place/{placeId}")
    override fun modifyBookmarkAtGroup(
        @AuthUser userId: UUID,
        @PathVariable("placeId") placeId: String,
        @RequestParam groupIds: List<UUID>,
    ): ApiResponse<Void> {
        bookmarkService.updateBookmarkAtGroup(userId = userId, placeId = placeId, savedGroupIds = groupIds)
        return ApiResponse.success()
    }

    @DeleteMapping("/group/{groupId}/places")
    override fun removeBookmarksAtGroup(
        @AuthUser userId: UUID,
        @PathVariable("groupId") groupId: UUID,
        @RequestParam placeIds: List<String>,
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

    @GetMapping("/place/{groupId}")
    override fun getBookmarks(
        @AuthUser userId: UUID,
        @PathVariable("groupId") groupId: UUID,
    ): ApiResponse<List<BookmarkInfoDto>> {
        val bookmarks: List<BookmarkInfoDto> =
            bookmarkService.getBookmarks(groupId)
                .map {
                    BookmarkInfoDto(
                        groupId = it.groupId,
                        placeId = it.placeId,
                        placeName = it.placeName,
                        roadAddress = it.roadAddress,
                        category = it.category.split(" > ").last(),
                        legal = it.legal,
                        savedAt = it.savedAt,
                    )
                }

        return ApiResponse.success(bookmarks)
    }

    @GetMapping("/saved")
    override fun savedBookmarks(
        userId: UUID,
        placeId: String,
    ): ApiResponse<Boolean> {
        val saved: Boolean = bookmarkService.isBookmark(userId = userId, placeId = placeId)

        return ApiResponse.success(saved)
    }
}
