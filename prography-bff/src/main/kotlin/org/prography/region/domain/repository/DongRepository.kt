package org.prography.region.domain.repository

import org.prography.region.domain.entity.Dong
import org.springframework.data.jpa.repository.JpaRepository

interface DongRepository : JpaRepository<Dong, String>
