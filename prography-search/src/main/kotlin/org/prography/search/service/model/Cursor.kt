package org.prography.search.service.model

import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.kotlinModule
import com.fasterxml.jackson.module.kotlin.readValue
import org.prography.search.service.model.enumeration.SortingStrategy
import java.util.Base64

data class Cursor(
    val key: Any?,
    val id: String,
) {
    companion object {
        private val mapper =
            ObjectMapper()
                .registerModule(kotlinModule())
                .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)

        fun decode(
            str: String,
            strategy: SortingStrategy,
        ): Cursor {
            /**
             * strategy 에 따라서 RATING 일 경우에는 double 타입으로 key로 그 외에는 int 형식으로
             * AVERAGE_RATING_HIGH, // 별점 높은 순 (카카오+네이버 평균)
             *     AVERAGE_RATING_LOW, // 별점 낮은 순 (카카오+네이버 평균)
             *     REVIEW_COUNT_HIGH, // 리뷰 많은 순 (카카오+네이버 합산)
             *     REVIEW_COUNT_LOW, // 리뷰 적은 순 (카카오+네이버 합산)
             */
            val json = String(Base64.getDecoder().decode(str))
            // 2) JSON → Map<String, Any>
            val map: Map<String, Any?> = mapper.readValue(json)
            val id =
                map["id"] as? String
                    ?: throw IllegalArgumentException("Cursor 에 id 가 없습니다.")

            val rawKey = map["key"]
            val key: Any? =
                when (strategy) {
                    SortingStrategy.RELATED -> rawKey
                    SortingStrategy.AVERAGE_RATING_HIGH,
                    SortingStrategy.AVERAGE_RATING_LOW,
                    -> (rawKey as Number).toDouble()

                    SortingStrategy.REVIEW_COUNT_HIGH,
                    SortingStrategy.REVIEW_COUNT_LOW,
                    -> (rawKey as Number).toInt()
                }
            return Cursor(key, id)
        }

        fun encode(
            key: Any,
            id: String,
        ): String {
            // RELATED 일 경우 { key:key, id:"" } 형식으로 주어진 값을 그냥 넣게 됨
            val map = mapOf("key" to key, "id" to id)
            val json = mapper.writeValueAsString(map)
            return Base64.getEncoder().encodeToString(json.toByteArray())
        }

        fun encode(id: String): String {
            // RELATED 일 경우 { key:null, id:"" } 형식
            val map = mapOf("key" to null, "id" to id)
            val json = mapper.writeValueAsString(map)
            return Base64.getEncoder().encodeToString(json.toByteArray())
        }
    }
}
