package ks.citiesapp.domain.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import ks.citiesapp.domain.CityList

@Entity(tableName = "city_lists")
data class CityListEntity(
    @PrimaryKey val id: String,
    val name: String,
    val fullName: String,
    val color: Int,
    val cities: String // Будем хранить как CSV: "Париж,Берлин,Милан"
)

fun CityListEntity.toDomain(): CityList {
    return CityList(
        id = id,
        name = name,
        fullName = fullName,
        color = color,
        cities = cities.split(",").filter { it.isNotBlank() }
    )
}