package org.prography.bff.search.controller

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.enums.ParameterIn
import io.swagger.v3.oas.annotations.media.ArraySchema
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import org.prography.bff.config.response.ApiResponse
import org.prography.bff.config.response.CursorResponse
import org.prography.bff.search.controller.model.AutoCompleteResponseDTO
import org.prography.bff.search.controller.model.PlaceDetailDTO
import org.prography.bff.search.controller.model.SearchResponseDTO
import org.prography.bff.search.controller.model.enumeration.FoodCategory
import org.prography.bff.search.controller.model.enumeration.OrderStrategy

interface SearchController {
    @Operation(
        summary = "자동 완성 API",
        description = "상호명 앞자리가 동일한 부분을 검색",
        responses = [
            io.swagger.v3.oas.annotations.responses.ApiResponse(
                responseCode = "200",
                content = [
                    Content(
                        mediaType = "application/json",
                        array =
                            ArraySchema(
                                schema = Schema(implementation = AutoCompleteResponseDTO::class),
                                arraySchema = Schema(description = "Cursor-paginated list"),
                            ),
                    ),
                ],
            ),
        ],
    )
    fun autoComplete(
        @Parameter(
            name = "keyword",
            `in` = ParameterIn.QUERY,
            description = "검색어 (음식점 이름 또는 설명 등에서 일치 검색에 사용)",
            required = true,
            example = "치킨",
        )
        keyword: String,
        @Parameter(
            name = "size",
            `in` = ParameterIn.QUERY,
            description = "한 번에 조회할 결과 개수",
            required = false,
            example = "5",
        )
        size: Int?,
        @Parameter(
            name = "dong_code",
            `in` = ParameterIn.QUERY,
            description = "검색을 제한할 구 코드 리스트 (여러 개 전달 가능), 비어있을 경우 강남구 서치",
            required = false,
            example = "[\"11110\",\"11140\"]",
        )
        addressCodes: List<String>?,
        @Parameter(
            name = "category",
            `in` = ParameterIn.QUERY,
            description = """
                UNDEFINED - 정의되어 있지 않은 유형
                FD01 - 한식
                FD02 - 일식
                FD03 - 중식
                FD04 - 양식
                FD05 - 분식
                FD06 - 카페 & 베이커리
                FD07 - 패스트푸드
                FD08 - 샐러드
                FD09 - 육류
                FD10 - 해물
                FD11 - 주점
                FD12 - 기타 세계음식
            """,
            required = false,
            example = "FD01",
        )
        categories: FoodCategory?,
    ): ApiResponse<List<AutoCompleteResponseDTO>>

    @Operation(
        summary = "검색 중 & 검색 완료 API",
        description = "기본적인 Elasticsearch 매치 쿼리 기본정렬로 반환 됩니다. 마지막 아이디는 리스트뷰의 마지막 원소의 ID 값을 넣으면 조회 됩니다. (hasNext === false 일 경우 다음 스크롤에 불러들일 리스트가 없습니다.)",
        responses = [
            io.swagger.v3.oas.annotations.responses.ApiResponse(
                responseCode = "200",
                content = [
                    Content(
                        mediaType = "application/json",
                        array =
                            ArraySchema(
                                schema = Schema(implementation = SearchResponseDTO::class),
                                arraySchema = Schema(description = "Cursor-paginated list"),
                            ),
                    ),
                ],
            ),
        ],
    )
    fun searchTerm(
        @Parameter(
            name = "keyword",
            `in` = ParameterIn.QUERY,
            description = "검색어 (음식점 이름 또는 설명 등에서 일치 검색에 사용)",
            required = true,
            example = "치킨",
        )
        keyword: String,
        @Parameter(
            name = "size",
            `in` = ParameterIn.QUERY,
            description = "한 번에 조회할 결과 개수",
            required = false,
            example = "5",
        )
        size: Int?,
        @Parameter(
            name = "dong_code",
            `in` = ParameterIn.QUERY,
            description = "검색을 제한할 구 코드 리스트 (여러 개 전달 가능), 비어있을 경우 강남구 서치",
            required = false,
            example = "[\"11110\",\"11140\"]",
        )
        addressCodes: List<String>?,
        @Parameter(
            name = "category",
            `in` = ParameterIn.QUERY,
            description = """
                UNDEFINED - 정의되어 있지 않은 유형
                FD01 - 한식
                FD02 - 일식
                FD03 - 중식
                FD04 - 양식
                FD05 - 분식
                FD06 - 카페 & 베이커리
                FD07 - 패스트푸드
                FD08 - 샐러드
                FD09 - 육류
                FD10 - 해물
                FD11 - 주점
                FD12 - 기타 세계음식
            """,
            required = false,
            example = "FD01",
        )
        categories: FoodCategory?,
        @Parameter(
            name = "sort",
            `in` = ParameterIn.QUERY,
            description = """
                RELATED - 관련 순 검색 엔진 내부 점수 기반 내림차순 정렬
                AVERAGE_RATING_HIGH - 카카오와 네이버 점수 내림차순 정렬
                AVERAGE_RATING_LOW - 카카오와 네이버 점수 오름차순 정렬
                REVIEW_COUNT_HIGH - 카카오와 네이버 리뷰 갯수 내림차순 정렬
                REVIEW_COUNT_LOW - 카카오와 네이버 리뷰 갯수 오름차순 정렬
            """,
            required = false,
            example = "RELATED",
        )
        sort: OrderStrategy?,
        @Parameter(
            name = "cursor",
            `in` = ParameterIn.QUERY,
            description = "이전 페이지의 조회된 마지막 데이터의 정보, FE에서 따로 계산하실 필욘 없으시고 이전 응답 값의 cursor 필드 값을 넣어주시면 됩니다.",
            required = false,
            example = "eyJrZXkiOm51bGwsImlkIjoi7Jik7JeUX+y5tO2OmOyVpOugiOyKpO2GoOuekUDshJzsmrhf6rCV64Ko6rWsX+yVleq1rOygleuhnDEx6ri4XzM3LTMwIn0=",
        )
        cursorString: String?,
    ): ApiResponse<CursorResponse<SearchResponseDTO>>

    @Operation(
        summary = "음식점 세부 페이지 정보",
        description = "부족한 정보는 말해주세요.",
        responses = [
            io.swagger.v3.oas.annotations.responses.ApiResponse(
                responseCode = "200",
                content = [
                    Content(
                        mediaType = "application/json",
                        schema = Schema(implementation = PlaceDetailDTO::class),
                    ),
                ],
            ),
            io.swagger.v3.oas.annotations.responses.ApiResponse(
                responseCode = "404",
                description = "데이터가 수집되지 않은 음식점인 경우",
                content = [
                    Content(
                        mediaType = "application/json",
                        schema = Schema(implementation = ApiResponse.Failure::class),
                    ),
                ],
            ),
        ],
    )
    fun getMockSummary(placeId: String): ApiResponse<PlaceDetailDTO>
}
