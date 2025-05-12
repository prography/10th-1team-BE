package org.prography.search.service.mock

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import org.prography.config.exception.badrequest.InvalidRequestException
import org.prography.config.exception.notfound.NotFoundException
import org.prography.search.service.model.PlaceDetail
import org.prography.search.service.model.PlaceSummary
import org.springframework.core.io.ClassPathResource
import org.springframework.stereotype.Service

@Service
class MockService {
    private val objectMapper = jacksonObjectMapper()
    private val mockSummaryData: List<PlaceSummary> by lazy {
        val resource = ClassPathResource("place_summary_mock_200.json")
        resource.inputStream.use { inputStream ->
            objectMapper.readValue(inputStream)
        }
    }

    private val mockDetailData: List<PlaceDetail> by lazy {
        val resource = ClassPathResource("place_detail_mock_200.json")
        resource.inputStream.use { inputStream ->
            objectMapper.readValue(inputStream)
        }
    }

    fun getSummaries(
        keyword: String?,
        lastId: Long?,
        size: Int,
    ): List<PlaceSummary> {
        val fromIndex = lastId?.let { getStatIndex(lastId) } ?: 0
        return if (keyword.isNullOrBlank()) {
            findAllSummaries(fromIndex, size)
        } else {
            findFilteredSummaries(keyword, fromIndex, size)
        }
    }

    private fun findAllSummaries(
        fromIndex: Long,
        size: Int,
    ): List<PlaceSummary> {
        if (fromIndex >= mockSummaryData.size) return emptyList()

        val toIndex = (fromIndex + size).coerceAtMost(mockSummaryData.size.toLong())
        return mockSummaryData.subList(fromIndex.toInt(), toIndex.toInt())
    }

    private fun findFilteredSummaries(
        keyword: String,
        fromIndex: Long,
        size: Int,
    ): List<PlaceSummary> {
        if (fromIndex >= mockSummaryData.size) return emptyList()

        val toIndex = (fromIndex + size).coerceAtMost(mockSummaryData.size.toLong())
        return mockSummaryData.subList(fromIndex.toInt(), toIndex.toInt())
    }

    private fun getStatIndex(lastId: Long): Long {
        if (!existsBySummaryId(lastId)) {
            throw InvalidRequestException.CursorInvalidRequest()
        }

        val index = mockSummaryData.indexOfFirst { it.id == lastId }
        require(index != -1) { "getStatIndex called with non-existent lastId: $lastId" }

        return (index + 1).toLong()
    }

    private fun existsBySummaryId(id: Long?): Boolean = id?.let { cursorId -> mockSummaryData.any { it.id == cursorId } } ?: true

    fun isLatest(lastId: Long): Boolean {
        val index = mockSummaryData.indexOfFirst { it.id == lastId }
        return index + 1 < mockSummaryData.size
    }

    fun getPlaceDetail(placeId: Long): PlaceDetail {
        // TODO DB에서 조회할 때는 List 원소들을 LIMIT으로 걸어서 조금씩 가져와야함
        return mockDetailData.find { it.id == placeId }
            ?: throw NotFoundException.PlaceNotFoundException()
    }
}
