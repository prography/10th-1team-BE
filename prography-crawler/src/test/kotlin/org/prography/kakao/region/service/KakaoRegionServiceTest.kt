package org.prography.kakao.region.service

import feign.FeignException
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.prography.kakao.region.dto.RegionCodes
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles

@SpringBootTest
@ActiveProfiles("test")
class KakaoRegionServiceIntegrationTest {
    @Autowired
    private lateinit var kakaoRegionService: KakaoRegionService

    /**
     * 실제로 존재하는 좌표(예: 서울시청 인근 좌표)를 넣어서,
     * B(법정동) / H(행정동) 코드가 제대로 넘어오는지 확인합니다.
     *
     * 이 좌표를 적절히 변경해서, 실제로 Kakao Local API 서버에서
     * “정상적으로” 응답해 주는 x, y 값을 사용해주세요.
     */
    @Test
    @DisplayName("fetchAndSaveCodes 실제 Kakao API 호출 시 정상적으로 bCode/hCode가 리턴되는지")
    fun `fetchAndSaveCodes returns non-empty codes from real Kakao API`() {
        // 실제로 유효한 좌표 예시: (127.015101, 37.498095) → 서울 강남구청 근처
        val x = "127.029495989804"
        val y = "37.5056355004988"

        val regionCodes: RegionCodes = kakaoRegionService.fetchAndSaveCodes(x, y)

        // bCode, hCode 둘 다 빈 문자열이 아니어야 함
        assertTrue(regionCodes.bCode.isNotBlank(), "법정동 코드가 빈 문자열이 아닐 것")
        assertTrue(regionCodes.hCode.isNotBlank(), "행정동 코드가 빈 문자열이 아닐 것")

        println("법정동 코드: ${regionCodes.bCode}, 행정동 코드: ${regionCodes.hCode}")
    }

    /**
     * 잘못된 좌표(예: 바다 한복판)나, valid 좌표지만
     * 문서상 B/H 타입이 없을 때 예외가 나오는지 테스트할 수 있습니다.
     * (이 좌표를 실제 Kakao에서 검증 후, 예외가 나오는 좌표로 교체)
     */
    @Test
    @DisplayName("fetchAndSaveCodes 잘못된 좌표로 예외 발생 테스트")
    fun `fetchAndSaveCodes throws exception on invalid coordinates`() {
        // 예: 너무 엉뚱한 좌표(127, 0) 같은 경우
        val badX = "127.000000"
        val badY = "0.000000"

        // 잘못된 좌표라면, response.documents 에 B/H 타입이 없어서 IllegalStateException 발생
        val ex =
            assertThrows(FeignException::class.java) {
                kakaoRegionService.fetchAndSaveCodes(badX, badY)
            }
        println("예외 메시지: ${ex.message}")
    }
}
