package org.prography.bff.bookmark.controller

import org.prography.bff.bookmark.controller.model.BookmarkCmdDto
import org.prography.bff.bookmark.controller.model.BookmarkGroupInfoDto
import org.prography.bff.bookmark.controller.model.BookmarkGroupSaveDto
import org.prography.bff.bookmark.controller.model.BookmarkInfoDto
import org.prography.bff.config.response.ApiResponse
import org.prography.bff.config.security.AuthUser
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.util.UUID

/**
 * 북마크 관련 서비스 컨트롤러
 */
@RestController
@RequestMapping("/group")
class BookmarkControllerImpl : BookmarkController {
    @PostMapping("")
    override fun createBookmarkGroup(
        @AuthUser userId: UUID,
        @RequestBody dto: BookmarkGroupSaveDto,
    ): ApiResponse<UUID> {
        TODO("Not yet implemented")
    }

    @GetMapping("")
    override fun getBookmarkGroups(
        @AuthUser userId: UUID,
    ): List<BookmarkGroupInfoDto> {
        TODO("Not yet implemented")
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
        @AuthUser userId: UUID,
        @RequestBody dto: BookmarkCmdDto,
    ): ApiResponse<Void> {
        TODO("Not yet implemented")
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
        @AuthUser userId: UUID,
        @PathVariable("id") groupId: UUID,
    ): List<BookmarkInfoDto> {
        TODO("Not yet implemented")
    }
}
