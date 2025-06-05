package org.prography.kakao.region.service

import org.prography.kakao.place.external.KakaoFeignClient
import org.prography.kakao.region.dto.Coord2AddressDocument
import org.prography.kakao.region.dto.RegionCodes
import org.springframework.stereotype.Service

@Service
class KakaoRegionService(
    private val kakaoFeignClient: KakaoFeignClient,
) {
    /**
     * x, y 기반으로 법정동, 행정동 주소를 검색하는 함수
     */
    fun fetchAndSaveCodes(
        x: String,
        y: String,
    ): RegionCodes {
        val response = kakaoFeignClient.coord2Address(x, y)

        val bCode = findRegionCode(response.documents, x, y, "B", "법정동")
        val hCode = findRegionCode(response.documents, x, y, "H", "행정동")

        return RegionCodes(bCode = bCode, hCode = hCode)
    }

    // 1) 함수로 분리하는 방법
    private fun findRegionCode(
        documents: List<Coord2AddressDocument>,
        x: String,
        y: String,
        regionKey: String,
        regionName: String,
    ): String {
        return documents
            .firstOrNull { doc ->
                doc.regionType.equals(regionKey, ignoreCase = true) &&
                    !doc.code.isNullOrBlank()
            }
            ?.code
            ?: throw IllegalStateException(
                "$regionName 코드($regionKey) 정보가 없습니다: x=$x, y=$y, docs=$documents",
            )
    }
}
