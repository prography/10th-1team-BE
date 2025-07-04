package org.prography.bff.bookmark.controller

import io.swagger.v3.oas.annotations.ExternalDocumentation
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.media.ArraySchema
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.tags.Tag
import org.prography.bff.bookmark.controller.model.BookmarkCmdDto
import org.prography.bff.bookmark.controller.model.BookmarkGroupInfoDto
import org.prography.bff.bookmark.controller.model.BookmarkGroupSaveDto
import org.prography.bff.bookmark.controller.model.BookmarkInfoDto
import org.prography.bff.config.response.ApiResponse
import java.util.UUID

@Tag(
    name = "Bookmark",
    description = "가게 저장 및 그룹에 관련된 API",
)
interface BookmarkController {
    @Operation(
        summary = "그룹 저장 API",
        description = "가게 저장을 위한 그룹을 생성합니다.",
        externalDocs =
            ExternalDocumentation(
                description = "노션 문서",
                url = "https://www.notion.so/21cc0f5d7a1d8086a880ce7d3a470d01",
            ),
        responses = [
            io.swagger.v3.oas.annotations.responses.ApiResponse(
                responseCode = "200",
                content = [
                    Content(
                        mediaType = "application/json",
                        array =
                            ArraySchema(
                                schema = Schema(implementation = UUID::class),
                            ),
                    ),
                ],
            ),
        ],
    )
    fun createBookmarkGroup(
        userId: UUID,
        dto: BookmarkGroupSaveDto,
    ): ApiResponse<UUID>

    @Operation(
        summary = "그룹 조회 API",
        description = "유저가 생성한 그룹을 조회 합니다.",
        externalDocs =
            ExternalDocumentation(
                description = "피그마 링크",
                url = "https://www.figma.com/design/xGWaWKSAUvpUaUJVPsITZ5/%EB%A6%AC%EB%B7%B0-%EB%A7%A4%EC%B9%98-%EB%94%94%EC%9E%90%EC%9D%B8%ED%8C%8C%EC%9D%BC?node-id=1217-22056&t=wvWu6KGlfjrulv3r-11",
            ),
        responses = [
            io.swagger.v3.oas.annotations.responses.ApiResponse(
                responseCode = "200",
                content = [
                    Content(
                        mediaType = "application/json",
                        array =
                            ArraySchema(
                                schema = Schema(implementation = BookmarkGroupInfoDto::class),
                            ),
                    ),
                ],
            ),
        ],
    )
    fun getBookmarkGroups(userId: UUID): List<BookmarkGroupInfoDto>

    @Operation(
        summary = "그룹 조회 API",
        description = "가게 저장 시 생성된 그룹을 조회 합니다.",
        externalDocs =
            ExternalDocumentation(
                description = "피그마 링크",
                url = "https://www.figma.com/design/xGWaWKSAUvpUaUJVPsITZ5/%EB%A6%AC%EB%B7%B0-%EB%A7%A4%EC%B9%98-%EB%94%94%EC%9E%90%EC%9D%B8%ED%8C%8C%EC%9D%BC?node-id=1165-18638&t=wvWu6KGlfjrulv3r-11",
            ),
        responses = [
            io.swagger.v3.oas.annotations.responses.ApiResponse(
                responseCode = "200",
                content = [
                    Content(
                        mediaType = "application/json",
                        array =
                            ArraySchema(
                                schema = Schema(implementation = BookmarkGroupInfoDto::class),
                            ),
                    ),
                ],
            ),
        ],
    )
    fun getBookmarkGroups(
        userId: UUID,
        placeId: String,
    ): List<BookmarkGroupInfoDto>

    @Operation(
        summary = "그룹 삭제 API",
        description = "선택된 그룹을 삭제합니다.",
        externalDocs =
            ExternalDocumentation(
                description = "피그마 링크",
                url = "https://www.figma.com/design/xGWaWKSAUvpUaUJVPsITZ5/%EB%A6%AC%EB%B7%B0-%EB%A7%A4%EC%B9%98-%EB%94%94%EC%9E%90%EC%9D%B8%ED%8C%8C%EC%9D%BC?node-id=1215-25233&t=wvWu6KGlfjrulv3r-11",
            ),
        responses = [
            io.swagger.v3.oas.annotations.responses.ApiResponse(
                responseCode = "200",
                content = [
                    Content(
                        mediaType = "application/json",
                        array =
                            ArraySchema(
                                schema = Schema(implementation = Void::class),
                            ),
                    ),
                ],
            ),
        ],
    )
    fun deleteBookmarkGroup(
        userId: UUID,
        groupId: UUID,
    ): ApiResponse<Void>

    @Operation(
        summary = "가게 저장 API",
        description = "선택된 그룹에 가게를 저장 합니다.",
        externalDocs =
            ExternalDocumentation(
                description = "피그마 링크",
                url = "https://www.figma.com/design/xGWaWKSAUvpUaUJVPsITZ5/%EB%A6%AC%EB%B7%B0-%EB%A7%A4%EC%B9%98-%EB%94%94%EC%9E%90%EC%9D%B8%ED%8C%8C%EC%9D%BC?node-id=1167-22112&t=wvWu6KGlfjrulv3r-11",
            ),
        responses = [
            io.swagger.v3.oas.annotations.responses.ApiResponse(
                responseCode = "200",
                content = [
                    Content(
                        mediaType = "application/json",
                        array =
                            ArraySchema(
                                schema = Schema(implementation = Void::class),
                            ),
                    ),
                ],
            ),
        ],
    )
    fun addBookmarkAtGroup(
        userId: UUID,
        dto: BookmarkCmdDto,
    ): ApiResponse<Void>

    @Operation(
        summary = "가게 삭제 API",
        description = "선택된 그룹에 가게를 저장을 취소 합니다.",
        externalDocs =
            ExternalDocumentation(
                description = "피그마 링크",
                url = "https://www.figma.com/design/xGWaWKSAUvpUaUJVPsITZ5/%EB%A6%AC%EB%B7%B0-%EB%A7%A4%EC%B9%98-%EB%94%94%EC%9E%90%EC%9D%B8%ED%8C%8C%EC%9D%BC?node-id=1167-22178&t=wvWu6KGlfjrulv3r-11",
            ),
        responses = [
            io.swagger.v3.oas.annotations.responses.ApiResponse(
                responseCode = "200",
                content = [
                    Content(
                        mediaType = "application/json",
                        array =
                            ArraySchema(
                                schema = Schema(implementation = Void::class),
                            ),
                    ),
                ],
            ),
        ],
    )
    fun removeBookmarkAtGroup(
        userId: UUID,
        dto: BookmarkCmdDto,
    ): ApiResponse<Void>

    @Operation(
        summary = "가게 이동 API",
        description = "선택된 가게를 지정된 그룹으로 이동 됩니다.",
        externalDocs =
            ExternalDocumentation(
                description = "피그마 링크",
                url = "https://www.figma.com/design/xGWaWKSAUvpUaUJVPsITZ5/%EB%A6%AC%EB%B7%B0-%EB%A7%A4%EC%B9%98-%EB%94%94%EC%9E%90%EC%9D%B8%ED%8C%8C%EC%9D%BC?node-id=1217-27705&t=wvWu6KGlfjrulv3r-11",
            ),
        responses = [
            io.swagger.v3.oas.annotations.responses.ApiResponse(
                responseCode = "200",
                content = [
                    Content(
                        mediaType = "application/json",
                        array =
                            ArraySchema(
                                schema = Schema(implementation = Void::class),
                            ),
                    ),
                ],
            ),
        ],
    )
    fun moveBookmarkAtGroup(
        userId: UUID,
        dto: BookmarkCmdDto,
    ): ApiResponse<Void>

    @Operation(
        summary = "그룹에 저장된 가게 리스트 조회 API",
        description = "선택된 그룹에 저장된 가게들을 조회 합니다.",
        externalDocs =
            ExternalDocumentation(
                description = "피그마 링크",
                url = "https://www.figma.com/design/xGWaWKSAUvpUaUJVPsITZ5/%EB%A6%AC%EB%B7%B0-%EB%A7%A4%EC%B9%98-%EB%94%94%EC%9E%90%EC%9D%B8%ED%8C%8C%EC%9D%BC?node-id=1215-29848&t=wvWu6KGlfjrulv3r-11",
            ),
        responses = [
            io.swagger.v3.oas.annotations.responses.ApiResponse(
                responseCode = "200",
                content = [
                    Content(
                        mediaType = "application/json",
                        array =
                            ArraySchema(
                                schema = Schema(implementation = BookmarkInfoDto::class),
                            ),
                    ),
                ],
            ),
        ],
    )
    fun getBookmarks(
        userId: UUID,
        groupId: UUID,
    ): List<BookmarkInfoDto>
}
