package org.prography.bff.vote.repository.model.converter

import jakarta.persistence.AttributeConverter
import jakarta.persistence.Converter
import org.prography.bff.vote.repository.model.enumeration.VoteCategory

@Converter
class VoteCategoriesConverter : AttributeConverter<List<VoteCategory>, String> {
    private val separator = ","

    override fun convertToDatabaseColumn(attribute: List<VoteCategory>): String {
        if (attribute.isEmpty()) {
            return ""
        }
        return attribute.joinToString(separator) {
            it.name
        }
    }

    override fun convertToEntityAttribute(dbData: String): List<VoteCategory> {
        if (dbData.isBlank()) {
            return emptyList()
        }

        return dbData.split(separator)
            .map { token -> VoteCategory.valueOf(token) }
    }
}
