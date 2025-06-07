package org.prography.search.exception

/**
 * Elasticsearch 관련된 예외처리
 */
sealed class ElasticsearchException(
    override val message: String,
    cause: Throwable? = null,
) : RuntimeException(message, cause) {
    /**
     * SSL 핸드쉐이크(인증서 검증) 과정에서 실패했을 때 던지는 예외
     */
    class CertificateValidationException(cause: Throwable) :
        ElasticsearchException("Failed to validate SSL certificate when connecting to Elasticsearch.", cause)

    /**
     * 연결 과정에서 예기치 않은 오류가 발생했을 때 던지는 예외
     */
    class ConnectionException(cause: Throwable) :
        ElasticsearchException("An unexpected error occurred during Elasticsearch search.", cause)

    /**
     * 그 외 엘라스틱 과정 중 발생하는 예외
     */
    class SearchingException(cause: Throwable) :
        ElasticsearchException("Unexpected error occurred while searching Elasticsearch.", cause)
}
