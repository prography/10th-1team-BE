package org.prography.bff.vote.controller

import io.swagger.v3.oas.annotations.ExternalDocumentation
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.media.ArraySchema
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.tags.Tag
import org.prography.bff.config.response.ApiResponse
import org.prography.bff.vote.controller.model.VoteResultDto
import org.prography.bff.vote.controller.model.VoteSubmitDto
import org.prography.bff.vote.controller.model.VoteSummaryDto
import java.util.UUID

/**
 * 투표 관련 컨트롤러 Swagger 명세 인터페이스
 */
@Tag(
    name = "Vote",
    description = "리뷰 매치 투표 전용 API",
)
interface VoteController {
    @Operation(
        summary = "플랫폼 투표 결과 API",
        description = "해당 가게의 플랫폼 투표 통계에 대한 정보를 반환합니다.",
        externalDocs =
            ExternalDocumentation(
                description = "Figma 디자인 문서",
                url = "https://www.figma.com/design/xGWaWKSAUvpUaUJVPsITZ5/%EB%A6%AC%EB%B7%B0-%EB%A7%A4%EC%B9%98-%EB%94%94%EC%9E%90%EC%9D%B8%ED%8C%8C%EC%9D%BC?node-id=1103-15926&t=jtUGUew3ZipY26Ca-4",
            ),
        responses = [
            io.swagger.v3.oas.annotations.responses.ApiResponse(
                responseCode = "200",
                content = [
                    Content(
                        mediaType = "application/json",
                        array =
                            ArraySchema(
                                schema = Schema(implementation = VoteResultDto::class),
                            ),
                    ),
                ],
            ),
        ],
    )
    fun getPlatformVoteResult(
        userId: UUID?,
        placeId: String,
    ): ApiResponse<VoteResultDto>

    @Operation(
        summary = "플랫폼 투표 하기 API",
        description = "해당 가게의 플랫폼 투표하는 API 입니다.",
        externalDocs =
            ExternalDocumentation(
                description = "Figma 디자인 문서",
                url = "https://www.figma.com/design/xGWaWKSAUvpUaUJVPsITZ5/%EB%A6%AC%EB%B7%B0-%EB%A7%A4%EC%B9%98-%EB%94%94%EC%9E%90%EC%9D%B8%ED%8C%8C%EC%9D%BC?node-id=1013-19904&t=kootXK4oHHkiLL0H-11",
            ),
        responses = [
            io.swagger.v3.oas.annotations.responses.ApiResponse(
                responseCode = "200",
                content = [
                    Content(
                        mediaType = "application/json",
                    ),
                ],
            ),
        ],
    )
    fun submitPlatformVote(
        userId: UUID,
        placeId: String,
        dto: VoteSubmitDto,
    ): ApiResponse<Void>

    @Operation(
        summary = "투표 요약 정보 조회 API",
        description = " 해당 가게에 대한 전체 투표 수 및 유저의 투표 여부를 반환합니다.",
        externalDocs =
            ExternalDocumentation(
                description = "Figma 디자인 문서",
                url = "https://www.figma.com/design/xGWaWKSAUvpUaUJVPsITZ5/%EB%A6%AC%EB%B7%B0-%EB%A7%A4%EC%B9%98-%EB%94%94%EC%9E%90%EC%9D%B8%ED%8C%8C%EC%9D%BC?node-id=1013-20466&t=8HiLclRuTbZ5koFv-4",
            ),
        responses = [
            io.swagger.v3.oas.annotations.responses.ApiResponse(
                responseCode = "200",
                content = [
                    Content(
                        mediaType = "application/json",
                        schema = Schema(implementation = VoteSummaryDto::class),
                    ),
                ],
            ),
        ],
    )
    fun getVoteSummary(
        placeId: String,
        userId: UUID?,
    ): ApiResponse<VoteSummaryDto>

    @Operation(
        summary = "투표 취소 API",
        description = "선택된 투표를 취소하고 이력을 삭제합니다.",
        externalDocs =
            ExternalDocumentation(
                description = "Figma 디자인 문서",
                url = "https://www.figma.com/design/xGWaWKSAUvpUaUJVPsITZ5/%EB%A6%AC%EB%B7%B0-%EB%A7%A4%EC%B9%98-%EB%94%94%EC%9E%90%EC%9D%B8%ED%8C%8C%EC%9D%BC?node-id=1013-20466&t=8HiLclRuTbZ5koFv-4",
            ),
        responses = [
            io.swagger.v3.oas.annotations.responses.ApiResponse(
                responseCode = "200",
                content = [
                    Content(
                        mediaType = "application/json",
                        schema = Schema(implementation = Void::class),
                    ),
                ],
            ),
        ],
    )
    fun cancelVote(
        userId: UUID,
        historyId: Long,
    ): ApiResponse<Void>
}
