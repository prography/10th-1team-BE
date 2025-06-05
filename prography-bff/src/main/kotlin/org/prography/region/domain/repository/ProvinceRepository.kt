package org.prography.region.domain.repository

import org.prography.region.domain.entity.Province
import org.springframework.data.jpa.repository.JpaRepository

interface ProvinceRepository : JpaRepository<Province, String>
