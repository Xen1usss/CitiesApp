package ks.citiesapp.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import ks.citiesapp.domain.entity.CityListEntity

@Dao
interface CityListDao {

    @Query("SELECT * FROM city_lists")
    fun getAll(): Flow<List<CityListEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(list: CityListEntity)

    @Query("DELETE FROM city_lists WHERE id = :id")
    suspend fun deleteById(id: String)
}