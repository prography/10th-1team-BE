package org.prography.bff.region.model

import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test
import org.prography.bff.region.domain.entity.City
import org.prography.bff.region.domain.entity.Province

class ProvinceDtoTest {
    @Test
    fun make_domain_dto_asc_by_name() {
        val testProvince = Province("test", "test")
        val city = City("test", "배영구", true, testProvince)
        val city1 = City("test", "강남구", true, testProvince)
        val city2 = City("test2", "서초구", true, testProvince)
        testProvince.cityList.add(city)
        testProvince.cityList.add(city1)
        testProvince.cityList.add(city2)

        val fromDomain = ProvinceDto.fromDomain(testProvince)

        Assertions.assertThat(fromDomain.cityList.size).isEqualTo(3)
        Assertions.assertThat(fromDomain.cityList[0].name).isEqualTo(city1.name)
        Assertions.assertThat(fromDomain.cityList[1].name).isEqualTo(city.name)
        Assertions.assertThat(fromDomain.cityList[2].name).isEqualTo(city2.name)
    }
}
