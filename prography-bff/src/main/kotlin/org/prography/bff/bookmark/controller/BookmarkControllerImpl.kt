package org.prography.bff.bookmark.controller

import org.prography.bff.bookmark.controller.model.BookmarkCmdDto
import org.prography.bff.bookmark.controller.model.BookmarkGroupInfoDto
import org.prography.bff.bookmark.controller.model.BookmarkGroupSaveDto
import org.prography.bff.bookmark.controller.model.BookmarkInfoDto
import org.prography.bff.bookmark.service.BookmarkService
import org.prography.bff.bookmark.service.model.PlaceGroupWithSaved
import org.prography.bff.config.response.ApiResponse
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
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
        @RequestParam userId: UUID,
        @RequestBody dto: BookmarkGroupSaveDto,
    ): ApiResponse<UUID> {
        bookmarkService.createBookmarkGroup(userId = userId, icon = dto.icon, groupName = dto.groupName)
        return ApiResponse.success(UUID.randomUUID())
    }

    @GetMapping("/group")
    override fun getBookmarkGroups(
        @RequestParam userId: UUID,
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
        @RequestParam userId: UUID,
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
        @RequestParam userId: UUID,
        @PathVariable("groupId") groupId: UUID,
    ): ApiResponse<Void> {
        bookmarkService.deleteBookmarkGroup(userId = userId, groupId = groupId)
        return ApiResponse.success()
    }

    @PatchMapping("/place/{placeId}")
    override fun addBookmarkAtGroup(
        @RequestParam userId: UUID,
        @PathVariable("placeId") placeId: String,
        @RequestParam groupIds: List<UUID>,
    ): ApiResponse<Void> {
        bookmarkService.addBookmarkAtGroup(groupIds = groupIds, placeId = placeId)
        return ApiResponse.success()
    }

    @DeleteMapping("/place/{placeId}")
    override fun removeBookmarkAtGroups(
        @RequestParam userId: UUID,
        @PathVariable("placeId") placeId: String,
        @RequestParam groupIds: List<UUID>,
    ): ApiResponse<Void> {
        bookmarkService.removeBookmarkAtGroup(userId = userId, placeId = placeId, groupIds = groupIds)
        return ApiResponse.success()
    }

    @DeleteMapping("/group/{groupId}/places")
    override fun removeBookmarksAtGroup(
        @RequestParam userId: UUID,
        @PathVariable("groupId") groupId: UUID,
        @RequestParam placeIds: List<String>,
    ): ApiResponse<Void> {
        bookmarkService.removeBookmarkAtGroup(userId = userId, groupId = groupId, placeIds = placeIds)
        return ApiResponse.success()
    }

    @PatchMapping("/place/move")
    override fun moveBookmarkAtGroup(
        @RequestParam userId: UUID,
        @RequestBody dto: BookmarkCmdDto,
    ): ApiResponse<Void> {
        TODO("Not yet implemented")
    }

    @GetMapping("/place/{groupId}")
    override fun getBookmarks(
        @RequestParam userId: UUID,
        @PathVariable("groupId") groupId: UUID,
    ): ApiResponse<List<BookmarkInfoDto>> {
        val bookmarks: List<BookmarkInfoDto> =
            bookmarkService.getBookmarks(groupId)
                .map {
                    BookmarkInfoDto(
                        id = it.id,
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
