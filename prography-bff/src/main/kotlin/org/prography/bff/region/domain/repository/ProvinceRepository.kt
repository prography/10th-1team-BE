package org.prography.bff.region.domain.repository

import org.prography.bff.region.domain.entity.Province
import org.springframework.data.jpa.repository.JpaRepository

interface ProvinceRepository : JpaRepository<Province, String>
