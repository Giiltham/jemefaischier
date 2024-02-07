package io.fairflix.jemefaischier.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import io.fairflix.jemefaischier.models.Favorite

@Dao
interface FavoriteDao {
    @Query("SELECT * FROM favorite")
    fun getAll() : List<Favorite>

    @Query("SELECT * FROM favorite WHERE osm_id=:osmId")
    fun getByOsmId(osmId : Int)

    @Insert
    fun insert(favorite: Favorite)

    @Delete
    fun delete(favorite : Favorite)

}