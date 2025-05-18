package org.prography.geo.domain

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import java.time.LocalDateTime

@Document(collection = "Geo_Dong_Info")
data class GeoDongInfo(
    /**
     * MongoDB _id 로 쓸 유니크한 행정동 코드
     * 예: "11140530" – 서울특별시(11) 서초구(140) 반포1동(530)
     */
    @Id
    val dongCode: String,
    var name: String,
) {
    var kakaoInfoUpdatedAt: LocalDateTime? = null
    var naverInfoUpdatedAt: LocalDateTime? = null
    var kakaoReviewUpdatedAt: LocalDateTime? = null
    var naverReviewUpdatedAt: LocalDateTime? = null
}
