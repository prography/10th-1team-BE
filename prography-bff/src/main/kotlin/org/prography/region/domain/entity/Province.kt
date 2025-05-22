package org.prography.region.domain.entity

import jakarta.persistence.*

@Entity
@Table(name = "province")
class Province(
    @Id
    @Column(name = "province_code")
    val code: String,
    @Column(name = "province_name", nullable = false, length = 50)
    var name: String,
    @OneToMany(
        mappedBy = "province",
        cascade = [CascadeType.ALL],
        orphanRemoval = true,
        fetch = FetchType.LAZY,
    )
    val cities: MutableList<City> = mutableListOf(),
)
