package io.fairflix.jemefaischier.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import io.fairflix.jemefaischier.models.Favorite

@Dao
interface FavoriteDao {
    @Query("SELECT * FROM favorite")
    fun getAll() : LiveData<List<Favorite>>

    @Query("SELECT * FROM favorite WHERE osm_id=:osmId")
    fun getByOsmId(osmId : Long) : LiveData<Favorite?>

    @Insert
    suspend fun create(favorite: Favorite)

    @Query("DELETE FROM favorite WHERE osm_id=:osmId")
    suspend fun delete(osmId : Long)

}