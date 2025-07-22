package ks.citiesapp.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import ks.citiesapp.data.database.dao.CityListDao
import ks.citiesapp.domain.entity.CityListEntity

@Database(entities = [CityListEntity::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun cityListDao(): CityListDao

    companion object {
        const val DATABASE_NAME = "cities_app_db"
    }
}