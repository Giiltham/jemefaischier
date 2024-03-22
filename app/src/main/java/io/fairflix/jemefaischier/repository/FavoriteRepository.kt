package io.fairflix.jemefaischier.repository

import android.app.Application
import androidx.lifecycle.LiveData
import io.fairflix.jemefaischier.dao.FavoriteDao
import io.fairflix.jemefaischier.db.AppDatabase
import io.fairflix.jemefaischier.models.Favorite

class FavoriteRepository(app: Application) {

    private var favoriteDao : FavoriteDao

    init {
        favoriteDao = AppDatabase.getInstance(app).favoriteDao()
    }

    fun getFavorites() : LiveData<List<Favorite>> {
        return favoriteDao.getAll()
    }

    fun getFavorite(id: Long) : LiveData<Favorite?> {
        return favoriteDao.getByOsmId(id)
    }


    suspend fun addFavorite(favorite : Favorite) {
        favoriteDao.create(favorite)
    }

    suspend fun removeFavorite(id:Long) {
        favoriteDao.delete(id)
    }
}