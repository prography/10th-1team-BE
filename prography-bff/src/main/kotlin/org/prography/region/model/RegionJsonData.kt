// com.example.admin.dto/AdminData.kt
package org.prography.region.model

data class RegionJsonData(
    val name: String,
    val adm_cd2: String,
)
typealias AdminMap = Map<String, List<RegionJsonData>>
