package org.prography.region.domain.service

import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.databind.ObjectMapper
import jakarta.annotation.PostConstruct
import org.prography.region.domain.entity.City
import org.prography.region.domain.entity.Province
import org.prography.region.domain.repository.CityRepository
import org.prography.region.domain.repository.ProvinceRepository
import org.prography.region.model.AdminMap
import org.prography.region.model.RegionJsonData
import org.springframework.core.io.ResourceLoader
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true)
class RegionService(
    private val objectMapper: ObjectMapper,
    private val resourceLoader: ResourceLoader,
    private val provinceRepo: ProvinceRepository,
    private val cityRepo: CityRepository,
) {
    @PostConstruct
    @Transactional
    fun saveData() {
        // 1) JSON 파일 로드
        val resource = resourceLoader.getResource("classpath:korea_region_data.json")
        val adminMap: AdminMap =
            objectMapper.readValue(
                resource.inputStream,
                object : TypeReference<AdminMap>() {},
            )

        adminMap.forEach { (provinceName, regions) ->
            if (regions.isEmpty()) return@forEach

            // 2) Province 생성 (코드는 adm_cd2 앞 2자리)
            val provinceCode = regions.first().adm_cd2.substring(0, 2)
            val province = Province(code = provinceCode, name = provinceName)
            provinceRepo.save(province)

            // 3) City 생성 (코드는 adm_cd2 앞 5자리)
            regions.forEach { dto: RegionJsonData ->
                val cityCode = dto.adm_cd2
                val city = City(code = cityCode, name = dto.name, province = province)
                cityRepo.save(city)
            }
        }
    }

    fun getRegionData(): List<Province> {
        return provinceRepo.findAll()
    }
}
