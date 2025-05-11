package org.prography.geo.service

import com.google.gson.*
import org.locationtech.jts.geom.Coordinate
import org.locationtech.jts.geom.GeometryFactory
import org.prography.geo.domain.GeoDongInfo
import org.prography.geo.domain.GeoDongInfoRepository
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component
import java.io.IOException
import java.io.InputStreamReader
import java.nio.charset.StandardCharsets

@Component
class GeoRectSliceService(
    private val geoDongInfoRepository: GeoDongInfoRepository,
) {
    companion object {
        private val log = LoggerFactory.getLogger(GeoRectSliceService::class.java)
        private const val RESOURCE_PATH = "HangJeongDong_ver20250401.geojson"
    }

    private object Keys {
        const val FEATURES = "features"
        const val PROPERTIES = "properties"
        const val ADM_NM = "adm_nm"
        const val ADM_CD2 = "adm_cd2"
        const val GEOMETRY = "geometry"
        const val TYPE = "type"
        const val COORDINATES = "coordinates"
        const val MULTI_POLYGON = "MultiPolygon"
    }

    private val geometryFactory = GeometryFactory()
    private val features: JsonArray

    init {
        val stream =
            this::class.java.classLoader.getResourceAsStream(RESOURCE_PATH)
                ?: throw IllegalStateException("Resource not found: $RESOURCE_PATH")
        try {
            InputStreamReader(stream, StandardCharsets.UTF_8).use { reader ->
                val root = JsonParser.parseReader(reader).asJsonObject
                features = root.getAsJsonArray(Keys.FEATURES)
            }
        } catch (e: IOException) {
            throw IllegalStateException("Failed to load/parse geojson: $RESOURCE_PATH", e)
        } catch (e: JsonIOException) {
            throw IllegalStateException("Failed to load/parse geojson: $RESOURCE_PATH", e)
        } catch (e: JsonSyntaxException) {
            throw IllegalStateException("Failed to load/parse geojson: $RESOURCE_PATH", e)
        }
    }

    /**
     * 특정 행정동(adm_nm)에 대한 geometry 영역을 step(°) 단위 사각형으로 슬라이스.
     * 반환 형식: "서쪽,북쪽,동쪽,남쪽"  (x1,y2,x2,y1)
     */
    fun sliceRectFromFeature(
        admName: String,
        step: Double,
    ): List<String> {
        val feature = findGeoDongInfoFromAdmName(admName) // 행정동 정보 저장 + feature 반환
        val props = feature.getAsJsonObject(Keys.PROPERTIES)
        val dongCode = props.get(Keys.ADM_CD2).asString
        val name = props.get(Keys.ADM_NM).asString

        geoDongInfoRepository.save(GeoDongInfo(dongCode = dongCode, name = name))
        val rects = mutableListOf<String>()

        // ── geometry 파싱 ───
        val geomJson = feature.getAsJsonObject(Keys.GEOMETRY)
        val type = geomJson.get(Keys.TYPE).asString
        val coordsArr = geomJson.getAsJsonArray(Keys.COORDINATES)

        val ring =
            if (type == Keys.MULTI_POLYGON) {
                coordsArr[0].asJsonArray[0].asJsonArray
            } else {
                coordsArr[0].asJsonArray
            }

        val coords =
            ring.map { p ->
                p.asJsonArray.let { Coordinate(it[0].asDouble, it[1].asDouble) }
            }.let { list ->
                if (!list.first().equals2D(list.last())) list + list.first() else list
            }.toTypedArray()

        val region =
            geometryFactory
                .createPolygon(geometryFactory.createLinearRing(coords), null)

        // ── bounding box 계산 ───────────────────────────────────────────────────
        var minX = Double.POSITIVE_INFINITY
        var maxX = Double.NEGATIVE_INFINITY
        var minY = Double.POSITIVE_INFINITY
        var maxY = Double.NEGATIVE_INFINITY
        coords.forEach { c ->
            if (c.x < minX) minX = c.x
            if (c.x > maxX) maxX = c.x
            if (c.y < minY) minY = c.y
            if (c.y > maxY) maxY = c.y
        }

        // ── rect 슬라이스 ───────────────────────────────────────────────────────
        var x = minX
        while (x < maxX) {
            var y = minY
            while (y < maxY) {
                val x2 = x + step
                val y2 = y + step
                val cell =
                    geometryFactory.createPolygon(
                        arrayOf(
                            Coordinate(x, y),
                            Coordinate(x2, y),
                            Coordinate(x2, y2),
                            Coordinate(x, y2),
                            Coordinate(x, y),
                        ),
                    )
                if (region.intersects(cell)) {
                    rects += "%.6f,%.6f,%.6f,%.6f".format(x, y2, x2, y)
                }
                y += step
            }
            x += step
        }

        log.info("{} rects slice for {}", rects.size, admName)
        return rects
    }

    /**
     * admName 에 해당하는 feature 의 속성(JsonObject)을 찾아
     * GeoDongInfo 를 저장하고 반환한다.
     *
     * @throws IllegalArgumentException 없으면 예외
     */
    private fun findGeoDongInfoFromAdmName(admName: String): JsonObject {
        val feature =
            features
                .asSequence()
                .map(JsonElement::getAsJsonObject)
                .firstOrNull {
                    it.getAsJsonObject(Keys.PROPERTIES)
                        .get(Keys.ADM_NM).asString == admName
                } ?: throw IllegalArgumentException("행정동 [$admName] 을(를) 찾을 수 없습니다.")
        return feature
    }

    fun getDongCodeFromAdmName(admName: String): String {
        val feature = findGeoDongInfoFromAdmName(admName)
        val props = feature.getAsJsonObject(Keys.PROPERTIES)
        return props.get(Keys.ADM_CD2).asString
    }
}
