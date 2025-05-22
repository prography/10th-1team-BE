package org.prography.region.domain.repository

import org.prography.region.domain.entity.City
import org.springframework.data.jpa.repository.JpaRepository

interface CityRepository : JpaRepository<City, String>
