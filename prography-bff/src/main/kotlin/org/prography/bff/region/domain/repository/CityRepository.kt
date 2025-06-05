package org.prography.bff.region.domain.repository

import org.prography.bff.region.domain.entity.City
import org.springframework.data.jpa.repository.JpaRepository

interface CityRepository : JpaRepository<City, String>
