package org.prography.error

import org.bson.types.ObjectId
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import java.time.Instant

@Document(collection = "exception_log")
data class ExceptionLog(
    @Id
    val id: ObjectId? = null,
    val domain: String, // ex) "user", "order", "payment"
    val message: String, // human-readable message
    val details: Map<String, Any> = emptyMap(), // 도메인별 추가 정보
    val timestamp: Instant = Instant.now(),
)
