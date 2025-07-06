package org.prography.bff.bookmark.controller

import org.prography.bff.bookmark.controller.model.BookmarkCmdDto
import org.prography.bff.bookmark.controller.model.BookmarkGroupInfoDto
import org.prography.bff.bookmark.controller.model.BookmarkGroupSaveDto
import org.prography.bff.bookmark.controller.model.BookmarkInfoDto
import org.prography.bff.bookmark.service.BookmarkService
import org.prography.bff.config.response.ApiResponse
import org.prography.bff.config.security.AuthUser
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
@RequestMapping("/group")
class BookmarkControllerImpl(
    private val bookmarkService: BookmarkService,
) : BookmarkController {
    @PostMapping("")
    override fun createBookmarkGroup(
        @RequestParam userId: UUID,
        @RequestBody dto: BookmarkGroupSaveDto,
    ): ApiResponse<UUID> {
        bookmarkService.createBookmarkGroup(userId = userId, icon = dto.icon, groupName = dto.groupName)
        return ApiResponse.success(UUID.randomUUID())
    }

    @GetMapping("")
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

    @GetMapping("/{id}")
    override fun getBookmarkGroups(
        @AuthUser userId: UUID,
        @PathVariable("id") placeId: String,
    ): List<BookmarkGroupInfoDto> {
        TODO("Not yet implemented")
    }

    @DeleteMapping("/{id}")
    override fun deleteBookmarkGroup(
        @AuthUser userId: UUID,
        @PathVariable("id") groupId: UUID,
    ): ApiResponse<Void> {
        TODO("Not yet implemented")
    }

    @PatchMapping("/place")
    override fun addBookmarkAtGroup(
        @RequestParam userId: UUID,
        @RequestBody dto: BookmarkCmdDto,
    ): ApiResponse<Void> {
        bookmarkService.addBookmarkAtGroup(groupIds = dto.groupIds, placeId = dto.placeId.single())

        return ApiResponse.success()
    }

    @DeleteMapping("/place")
    override fun removeBookmarkAtGroup(
        @AuthUser userId: UUID,
        @RequestBody dto: BookmarkCmdDto,
    ): ApiResponse<Void> {
        TODO("Not yet implemented")
    }

    @PatchMapping("/place/move")
    override fun moveBookmarkAtGroup(
        @AuthUser userId: UUID,
        @RequestBody dto: BookmarkCmdDto,
    ): ApiResponse<Void> {
        TODO("Not yet implemented")
    }

    @GetMapping("/place/{id}")
    override fun getBookmarks(
        @PathVariable("id") groupId: UUID,
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
}
