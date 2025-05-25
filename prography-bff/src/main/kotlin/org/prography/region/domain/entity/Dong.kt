package org.prography.region.domain.entity

import jakarta.persistence.*

@Entity
@Table(name = "dong")
data class Dong(
    @Id
    @Column(length = 10)
    val code: String,
    @Column(nullable = false)
    val name: String,
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "city_code", nullable = false)
    val city: City,
)
