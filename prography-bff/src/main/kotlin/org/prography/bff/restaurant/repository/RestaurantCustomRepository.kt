package org.prography.bff.restaurant.repository

import org.prography.bff.restaurant.repository.model.PlaceInfo
import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.data.mongodb.core.query.Criteria
import org.springframework.data.mongodb.core.query.Query
import org.springframework.stereotype.Component
import java.util.Optional

@Component
class RestaurantCustomRepository(
    private val mongoTemplate: MongoTemplate,
) {
    companion object {
        private const val COLLECTION_NAME = "restaurant_data"
    }

    /**
     * 특정 id (_id) 가 restaurant_data 컬렉션에 존재하는지 체크
     */
    fun existsById(id: String): Boolean {
        val query = Query(Criteria.where("_id").`is`(id))
        return mongoTemplate.exists(query, COLLECTION_NAME)
    }

    /**
     * 기존에 작성하신 kakaoPlaceData 조회 메서드
     */
    fun findKakaoPlaceInfoById(id: String): Optional<PlaceInfo> {
        val query =
            Query(Criteria.where("_id").`is`(id)).apply {
                fields()
                    .include("b_code")
                    .include("kakaoPlaceData.roadAddressName")
                    .include("kakaoPlaceData.placeName")
                    .include("kakaoPlaceData.categoryName")
                    .exclude("_id")
            }

        val result =
            mongoTemplate.findOne(
                query,
                PlaceInfo::class.java,
                COLLECTION_NAME,
            )
        return Optional.ofNullable(result)
    }

    fun findKakaoPlaceInfoInIds(ids: List<String>): List<PlaceInfo> {
        if (ids.isEmpty()) return emptyList()

        val query =
            Query(Criteria.where("_id").`in`(ids)).apply {
                fields()
                    .include("b_code")
                    .include("kakaoPlaceData.roadAddressName")
                    .include("kakaoPlaceData.placeName")
                    .include("kakaoPlaceData.categoryName")
            }

        return mongoTemplate.find(
            query,
            PlaceInfo::class.java,
            COLLECTION_NAME,
        )
    }
}
