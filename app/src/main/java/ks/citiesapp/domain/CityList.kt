package ks.citiesapp.domain

data class CityList(
    val id: String,
    val name: String,
    val fullName: String,
    val color: Int,
    val cities: List<String>
)
