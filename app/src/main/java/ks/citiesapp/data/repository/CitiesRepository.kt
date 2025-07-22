package ks.citiesapp.data

class CitiesRepository {
    fun getAllCities(): List<String> = listOf(
        "Москва", "Хабаровск", "Ираклион", "Афины", "Мюнхен",
        "Линц", "Зальцбург", "Париж", "Вена", "Берлин", "Варшава", "Милан"
    )
}