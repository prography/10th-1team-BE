package org.prography.region.domain.entity

import jakarta.persistence.*

@Entity
@Table(name = "city")
class City(
    @Id
    @Column(name = "city_code")
    val code: String,
    @Column(name = "city_name", nullable = false, length = 50)
    var name: String,
    var isScraped: Boolean = false,
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "province_code", nullable = false)
    var province: Province,
)
