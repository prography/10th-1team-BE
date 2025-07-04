package org.prography.bff.region.domain.service

import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.databind.ObjectMapper
import jakarta.annotation.PostConstruct
import org.prography.bff.config.exception.notfound.NotFoundException
import org.prography.bff.region.domain.entity.City
import org.prography.bff.region.domain.entity.Dong
import org.prography.bff.region.domain.entity.Province
import org.prography.bff.region.domain.repository.CityRepository
import org.prography.bff.region.domain.repository.DongRepository
import org.prography.bff.region.domain.repository.ProvinceRepository
import org.springframework.core.io.ResourceLoader
import org.springframework.data.repository.findByIdOrNull
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
    data class RegionJsonData(
        @JsonProperty("법정동코드") val admCd: String,
        @JsonProperty("시도명") val provinceName: String,
        @JsonProperty("시군구명") val cityName: String?,
        @JsonProperty("읍면동명") val townshipName: String?,
        @JsonProperty("리명") val villageName: String?,
    )

    fun findRegionByBCode(bCode: String): String {
        val dong = dongRepo.findByIdOrNull(bCode) ?: throw NotFoundException.DongNotFoundException()
        return dong.name
    }

    @PostConstruct
    @Transactional
    fun saveData() {
        if (dongRepo.count() > 0) {
            return // 이미 저장된 경우 저장 X
        }
        // 1) JSON 파일 로드 (플랫한 리스트)
        val resource = resourceLoader.getResource("classpath:korea_full_region_data.json")
        val all: List<RegionJsonData> =
            objectMapper.readValue(
                resource.inputStream,
                object : TypeReference<List<RegionJsonData>>() {},
            )

        // 2) 시도별로 묶기
        all.groupBy { it.provinceName }
            .forEach { (provinceName, provinceList) ->

                // 3) Province 저장 (코드: admCd 앞 2자리)
                val provCode = provinceList.first().admCd.substring(0, 2)
                val province = Province(code = provCode, name = provinceName)
                provinceRepo.save(province)

                // 4) 시군구별로 묶기
                provinceList
                    .filter { it.cityName != null }
                    .groupBy { it.cityName!! }
                    .forEach { (cityName, cityList) ->

                        // 5) City 저장 (코드: admCd 앞 5자리)
                        val cityCode = cityList.first().admCd.substring(0, 5)
                        val city =
                            City(
                                code = cityCode,
                                name = cityName,
                                province = province,
                            )
                        cityRepo.save(city)

                        // 6) Dong 저장
                        cityList.forEach { dto ->
                            val dongCode = dto.admCd
                            // 읍면동명 + 리명이 둘 다 있을 땐 합쳐서, 아니면 있는 쪽만
                            val dongName =
                                listOfNotNull(dto.townshipName, dto.villageName)
                                    .joinToString(" ")
                            val dong =
                                Dong(
                                    code = dongCode,
                                    name = dongName,
                                    city = city,
                                )
                            dongRepo.save(dong)
                        }
                    }
            }

        println(">>> 저장 완료: Province ${provinceRepo.count()}, City ${cityRepo.count()}, Dong ${dongRepo.count()}")
    }

    fun getRegionData(): List<Province> = provinceRepo.findAll()
}
