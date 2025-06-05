package org.prography.region.domain.service

import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.databind.ObjectMapper
import jakarta.annotation.PostConstruct
import org.prography.region.domain.entity.City
import org.prography.region.domain.entity.Dong
import org.prography.region.domain.entity.Province
import org.prography.region.domain.repository.CityRepository
import org.prography.region.domain.repository.DongRepository
import org.prography.region.domain.repository.ProvinceRepository
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
    private val dongRepo: DongRepository,
) {
    @PostConstruct
    @Transactional
    fun saveData() {
        // 1) JSON 파일 로드
        val resource = resourceLoader.getResource("classpath:korea_full_region_data.json")
        val fullMap: Map<String, Map<String, List<RegionJsonData>>> =
            objectMapper.readValue(
                resource.inputStream,
                object : TypeReference<Map<String, Map<String, List<RegionJsonData>>>>() {},
            )

        fullMap.forEach { (provinceName, cityMap) ->
            if (cityMap.isEmpty()) return@forEach

            // 2) Province 생성 (코드: adm_cd2 앞 2자리)
            val firstDong = cityMap.values.first().first()
            val provinceCode = firstDong.adm_cd2.substring(0, 2)
            val province = Province(code = provinceCode, name = provinceName)
            provinceRepo.save(province)

            cityMap.forEach { (cityName, dongs) ->
                // 3) City 생성 (코드: dong.adm_cd2 앞 5자리)
                val cityCode = dongs.first().adm_cd2.substring(0, 5)
                val city = City(code = cityCode, name = cityName, province = province)
                cityRepo.save(city)

                // 4) Dong 생성
                dongs.forEach { dto ->
                    val dong = Dong(code = dto.adm_cd2, name = dto.name, city = city)
                    dongRepo.save(dong)
                }
            }
        }
    }

    fun getRegionData(): List<Province> = provinceRepo.findAll()
}
