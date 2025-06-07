package org.prography.bff.region.domain.repository

import org.prography.bff.region.domain.entity.Dong
import org.springframework.data.jpa.repository.JpaRepository

interface DongRepository : JpaRepository<Dong, String>
