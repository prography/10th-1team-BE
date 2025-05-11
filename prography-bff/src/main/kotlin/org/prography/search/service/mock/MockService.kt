package org.prography.search.service.mock

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import org.prography.config.exception.badrequest.InvalidRequestException
import org.prography.search.service.model.PlaceSummary
import org.springframework.core.io.ClassPathResource
import org.springframework.stereotype.Service

@Service
class MockService {
    private val objectMapper = jacksonObjectMapper()
    private val mockData: List<PlaceSummary> by lazy {
        val resource = ClassPathResource("place_summary_mock_200.json")
        resource.inputStream.use { inputStream ->
            objectMapper.readValue(inputStream)
        }
    }

    fun getSummaries(keyword: String?, lastId: Long?, size: Int): List<PlaceSummary> {
        val fromIndex = lastId?.let { getStatIndex(lastId) } ?: 0
        return if (keyword.isNullOrBlank()) {
            findAllSummaries(fromIndex, size)
        } else
            findFilteredSummaries(keyword, fromIndex, size)
    }

    private fun findAllSummaries(fromIndex: Long, size: Int): List<PlaceSummary> {
        if (fromIndex >= mockData.size) return emptyList()

        val toIndex = (fromIndex + size).coerceAtMost(mockData.size.toLong())
        return mockData.subList(fromIndex.toInt(), toIndex.toInt())
    }

    private fun findFilteredSummaries(
        keyword: String,
        fromIndex: Long,
        size: Int
    ): List<PlaceSummary> {
        if (fromIndex >= mockData.size) return emptyList()

        val toIndex = (fromIndex + size).coerceAtMost(mockData.size.toLong())
        return mockData.subList(fromIndex.toInt(), toIndex.toInt())
    }

    /**
     * DB에 전체를 계속해서 가져오면 병목이 발생할 위험이 있는데 어떻게 하는게 좋으려나
     */
    private fun getStatIndex(lastId: Long): Long {
        if (!existsById(lastId)) {
            throw InvalidRequestException.CursorInvalidRequest()
        }

        val index = mockData.indexOfFirst { it.id == lastId }
        require(index != -1) { "getStatIndex called with non-existent lastId: $lastId" }

        return (index + 1).toLong()
    }

    private fun existsById(id: Long?): Boolean =
        id?.let { cursorId -> mockData.any { it.id == cursorId } } ?: true

    fun isLatest(lastId: Long): Boolean {
        val index = mockData.indexOfFirst { it.id == lastId }
        return index + 1 < mockData.size
    }
}