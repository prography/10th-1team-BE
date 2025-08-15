package org.prography.bff.bookmark.controller

import io.swagger.v3.oas.annotations.ExternalDocumentation
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.media.ArraySchema
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.tags.Tag
import org.prography.bff.bookmark.controller.model.roulette.RouletteGroup
import org.prography.bff.bookmark.controller.model.roulette.RouletteGroupSaveDTO
import org.prography.bff.bookmark.controller.model.roulette.RouletteGroupUpdateDTO
import org.prography.bff.bookmark.controller.model.roulette.RouletteGroupWithPlaceDTO
import org.prography.bff.bookmark.controller.model.roulette.RouletteGroupsDTO
import org.prography.bff.config.response.ApiResponse
import java.util.UUID

@Tag(
    name = "Roulette",
    description = "룰렛에 관련된 API",
)
interface RouletteController {
    @Operation(
        summary = "룰렛 그룹 생성 API",
        description = "룰렛을 위한 그룹을 생성합니다.",
        externalDocs =
            ExternalDocumentation(
                description = "피그마 링크",
                url = "https://www.figma.com/design/xGWaWKSAUvpUaUJVPsITZ5/%EB%A6%AC%EB%B7%B0-%EB%A7%A4%EC%B9%98-%EB%94%94%EC%9E%90%EC%9D%B8%ED%8C%8C%EC%9D%BC?node-id=2102-56230&t=mOGBMUNGLC3ZpMpr-11",
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
    fun createRouletteGroup(
        userId: UUID,
        dto: RouletteGroupSaveDTO,
    ): ApiResponse<UUID>

    @Operation(
        summary = "룰렛 그룹 수정 API",
        description = "생성된 룰렛 그룹을 수정합니다.",
        externalDocs =
            ExternalDocumentation(
                description = "피그마 링크",
                url = "https://www.figma.com/design/xGWaWKSAUvpUaUJVPsITZ5/%EB%A6%AC%EB%B7%B0-%EB%A7%A4%EC%B9%98-%EB%94%94%EC%9E%90%EC%9D%B8%ED%8C%8C%EC%9D%BC?node-id=2102-55307&t=mOGBMUNGLC3ZpMpr-11",
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
    fun modifyRouletteGroup(
        userId: UUID,
        rouletteId: UUID,
        dto: RouletteGroupUpdateDTO,
    ): ApiResponse<Void>

    @Operation(
        summary = "룰렛 그룹 조회 API",
        description = "유저가 생성한 룰렛 그룹을 조회 합니다.",
        externalDocs =
            ExternalDocumentation(
                description = "피그마 링크",
                url = "https://www.figma.com/design/xGWaWKSAUvpUaUJVPsITZ5/%EB%A6%AC%EB%B7%B0-%EB%A7%A4%EC%B9%98-%EB%94%94%EC%9E%90%EC%9D%B8%ED%8C%8C%EC%9D%BC?node-id=2102-53142&t=mOGBMUNGLC3ZpMpr-11",
            ),
        responses = [
            io.swagger.v3.oas.annotations.responses.ApiResponse(
                responseCode = "200",
                content = [
                    Content(
                        mediaType = "application/json",
                        array =
                            ArraySchema(
                                schema = Schema(implementation = RouletteGroupsDTO::class),
                            ),
                    ),
                ],
            ),
        ],
    )
    fun getRoulette(userId: UUID): ApiResponse<RouletteGroupsDTO>

    @Operation(
        summary = "룰렛 그룹 조회 API",
        description = "가게 룰렛 추가 시 생성된 룰렛을 조회 합니다.",
        externalDocs =
            ExternalDocumentation(
                description = "피그마 링크",
                url = "https://www.figma.com/design/xGWaWKSAUvpUaUJVPsITZ5/%EB%A6%AC%EB%B7%B0-%EB%A7%A4%EC%B9%98-%EB%94%94%EC%9E%90%EC%9D%B8%ED%8C%8C%EC%9D%BC?node-id=2102-56052&t=mOGBMUNGLC3ZpMpr-11",
            ),
        responses = [
            io.swagger.v3.oas.annotations.responses.ApiResponse(
                responseCode = "200",
                content = [
                    Content(
                        mediaType = "application/json",
                        array =
                            ArraySchema(
                                schema = Schema(implementation = RouletteGroup::class),
                            ),
                    ),
                ],
            ),
        ],
    )
    fun getRouletteGroups(
        userId: UUID,
        placeId: String,
    ): ApiResponse<List<RouletteGroup>>

    @Operation(
        summary = "룰렛 그룹 삭제 API",
        description = "선택된 룰렛 그룹을 삭제합니다.",
        externalDocs =
            ExternalDocumentation(
                description = "피그마 링크",
                url = "https://www.figma.com/design/xGWaWKSAUvpUaUJVPsITZ5/%EB%A6%AC%EB%B7%B0-%EB%A7%A4%EC%B9%98-%EB%94%94%EC%9E%90%EC%9D%B8%ED%8C%8C%EC%9D%BC?node-id=2102-53182&t=mOGBMUNGLC3ZpMpr-11",
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
    fun deleteRouletteGroup(
        userId: UUID,
        rouletteId: UUID,
    ): ApiResponse<Void>

    @Operation(
        summary = "룰렛 아이템 수정 API",
        description = "선택된 룰렛에는 아이템를 추가하고 선택되지 않으면 삭제 합니다.",
        externalDocs =
            ExternalDocumentation(
                description = "피그마 링크",
                url = "https://www.figma.com/design/xGWaWKSAUvpUaUJVPsITZ5/%EB%A6%AC%EB%B7%B0-%EB%A7%A4%EC%B9%98-%EB%94%94%EC%9E%90%EC%9D%B8%ED%8C%8C%EC%9D%BC?node-id=2102-56052&t=mOGBMUNGLC3ZpMpr-11",
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
    fun modifyItemAtRouletteGroup(
        userId: UUID,
        placeId: String,
        rouletteIds: List<UUID>?,
    )

    @Operation(
        summary = "룰렛 그룹에 추가된 가게 조회 API",
        description = "선택된 룰렛 그룹에 추가된 가게들을 조회 합니다.",
        externalDocs =
            ExternalDocumentation(
                description = "피그마 링크",
                url = "https://www.figma.com/design/xGWaWKSAUvpUaUJVPsITZ5/%EB%A6%AC%EB%B7%B0-%EB%A7%A4%EC%B9%98-%EB%94%94%EC%9E%90%EC%9D%B8%ED%8C%8C%EC%9D%BC?node-id=2102-53667&t=mOGBMUNGLC3ZpMpr-11",
            ),
        responses = [
            io.swagger.v3.oas.annotations.responses.ApiResponse(
                responseCode = "200",
                content = [
                    Content(
                        mediaType = "application/json",
                        array =
                            ArraySchema(
                                schema = Schema(implementation = RouletteGroupWithPlaceDTO::class),
                            ),
                    ),
                ],
            ),
        ],
    )
    fun getRouletteGroup(
        userId: UUID,
        rouletteId: UUID,
    ): ApiResponse<RouletteGroupWithPlaceDTO>

    @Operation(
        summary = "룰렛에 추가된 유무 조회 API",
        description = "룰렛에 해당 가게가 추가 되었는지 판단하는 API",
        externalDocs =
            ExternalDocumentation(
                description = "피그마 링크",
                url = "https://www.figma.com/design/xGWaWKSAUvpUaUJVPsITZ5/%EB%A6%AC%EB%B7%B0-%EB%A7%A4%EC%B9%98-%EB%94%94%EC%9E%90%EC%9D%B8%ED%8C%8C%EC%9D%BC?node-id=2102-55763&t=mOGBMUNGLC3ZpMpr-11",
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
    fun addedItemAtRouletteGroup(
        userId: UUID?,
        placeId: String,
    ): ApiResponse<Boolean>
}
