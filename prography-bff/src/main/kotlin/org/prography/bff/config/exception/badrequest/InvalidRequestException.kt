package org.prography.bff.config.exception.badrequest

sealed class InvalidRequestException(
    override val message: String,
) : RuntimeException(message)
