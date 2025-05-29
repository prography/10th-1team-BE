package org.prography.error

import org.springframework.data.mongodb.repository.MongoRepository

interface ExceptionLogRepository : MongoRepository<ExceptionLog, String>
