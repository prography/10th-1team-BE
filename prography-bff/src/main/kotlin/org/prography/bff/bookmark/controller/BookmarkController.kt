package org.prography.bff.bookmark.controller

import io.swagger.v3.oas.annotations.ExternalDocumentation
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.media.ArraySchema
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.tags.Tag
import org.prography.bff.bookmark.controller.model.BookmarkGroupInfoDto
import org.prography.bff.bookmark.controller.model.BookmarkGroupSaveDto
import org.prography.bff.bookmark.controller.model.BookmarkInfoDto
import org.prography.bff.bookmark.controller.model.BookmarkMoveDto
import org.prography.bff.bookmark.controller.model.UpdatePlaceAtGroup
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
                description = "피그마 링크",
                url = "https://www.figma.com/design/xGWaWKSAUvpUaUJVPsITZ5/%EB%A6%AC%EB%B7%B0-%EB%A7%A4%EC%B9%98-%EB%94%94%EC%9E%90%EC%9D%B8%ED%8C%8C%EC%9D%BC?node-id=2102-55202&t=OtsurZkGnix9x0d3-11",
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
                url = "https://www.figma.com/design/xGWaWKSAUvpUaUJVPsITZ5/%EB%A6%AC%EB%B7%B0-%EB%A7%A4%EC%B9%98-%EB%94%94%EC%9E%90%EC%9D%B8%ED%8C%8C%EC%9D%BC?node-id=2102-53568&t=OtsurZkGnix9x0d3-11",
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
    fun getBookmarkGroups(userId: UUID): ApiResponse<List<BookmarkGroupInfoDto>>

    @Operation(
        summary = "그룹 조회 API",
        description = "가게 저장 시 생성된 그룹을 조회 합니다.",
        externalDocs =
            ExternalDocumentation(
                description = "피그마 링크",
                url = "https://www.figma.com/design/xGWaWKSAUvpUaUJVPsITZ5/%EB%A6%AC%EB%B7%B0-%EB%A7%A4%EC%B9%98-%EB%94%94%EC%9E%90%EC%9D%B8%ED%8C%8C%EC%9D%BC?node-id=2102-55692&t=OtsurZkGnix9x0d3-11",
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
    ): ApiResponse<List<BookmarkGroupInfoDto>>

    @Operation(
        summary = "그룹 삭제 API",
        description = "선택된 그룹을 삭제합니다.",
        externalDocs =
            ExternalDocumentation(
                description = "피그마 링크",
                url = "https://www.figma.com/design/xGWaWKSAUvpUaUJVPsITZ5/%EB%A6%AC%EB%B7%B0-%EB%A7%A4%EC%B9%98-%EB%94%94%EC%9E%90%EC%9D%B8%ED%8C%8C%EC%9D%BC?node-id=2102-53467&t=OtsurZkGnix9x0d3-11",
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
        summary = "가게 저장 수정 API",
        description = "선택된 그룹은 가게를 저장하고 선택되지 않으면 삭제 합니다.",
        externalDocs =
            ExternalDocumentation(
                description = "피그마 링크",
                url = "https://www.figma.com/design/xGWaWKSAUvpUaUJVPsITZ5/%EB%A6%AC%EB%B7%B0-%EB%A7%A4%EC%B9%98-%EB%94%94%EC%9E%90%EC%9D%B8%ED%8C%8C%EC%9D%BC?node-id=2102-55692&t=HuSzMAS2aST8Ztlj-11",
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
    fun modifyBookmarkAtGroup(
        userId: UUID,
        placeId: String,
        groupIds: List<UUID>?,
    ): ApiResponse<UpdatePlaceAtGroup>

    @Operation(
        summary = "가게 삭제 API",
        description = "선택된 가게들을 그룹에서 저장을 취소 합니다.",
        externalDocs =
            ExternalDocumentation(
                description = "피그마 링크",
                url = "https://www.figma.com/design/xGWaWKSAUvpUaUJVPsITZ5/%EB%A6%AC%EB%B7%B0-%EB%A7%A4%EC%B9%98-%EB%94%94%EC%9E%90%EC%9D%B8%ED%8C%8C%EC%9D%BC?node-id=2102-56405&t=OtsurZkGnix9x0d3-11",
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
    fun removeBookmarksAtGroup(
        userId: UUID,
        groupId: UUID,
        placeIds: List<String>,
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
        dto: BookmarkMoveDto,
    ): ApiResponse<Void>

    @Operation(
        summary = "그룹에 저장된 가게 리스트 조회 API",
        description = "선택된 그룹에 저장된 가게들을 조회 합니다.",
        externalDocs =
            ExternalDocumentation(
                description = "피그마 링크",
                url = "https://www.figma.com/design/xGWaWKSAUvpUaUJVPsITZ5/%EB%A6%AC%EB%B7%B0-%EB%A7%A4%EC%B9%98-%EB%94%94%EC%9E%90%EC%9D%B8%ED%8C%8C%EC%9D%BC?node-id=2102-53692&t=OtsurZkGnix9x0d3-11",
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
    ): ApiResponse<List<BookmarkInfoDto>>

    @Operation(
        summary = "그룹이 저장된 유무 조회 API",
        description = "선택한 그룹이 저장되었는지 판단하는 API",
        externalDocs =
            ExternalDocumentation(
                description = "피그마 링크",
                url = "https://www.figma.com/design/xGWaWKSAUvpUaUJVPsITZ5/%EB%A6%AC%EB%B7%B0-%EB%A7%A4%EC%B9%98-%EB%94%94%EC%9E%90%EC%9D%B8%ED%8C%8C%EC%9D%BC?node-id=2102-54564&t=SLP0kPfR7MjLnHiC-11",
            ),
        responses = [
            io.swagger.v3.oas.annotations.responses.ApiResponse(
                responseCode = "200",
                content = [
                    Content(
                        mediaType = "application/json",
                        array =
                            ArraySchema(
                                schema = Schema(implementation = Boolean::class),
                            ),
                    ),
                ],
            ),
        ],
    )
    fun savedBookmarks(
        userId: UUID?,
        placeId: String,
    ): ApiResponse<Boolean>
}
