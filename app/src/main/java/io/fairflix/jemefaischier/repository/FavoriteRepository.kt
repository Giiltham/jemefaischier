package io.fairflix.jemefaischier.repository

import android.app.Application
import io.fairflix.jemefaischier.dao.FavoriteDao
import io.fairflix.jemefaischier.db.AppDatabase
import io.fairflix.jemefaischier.models.Favorite

class FavoriteRepository constructor(app: Application) {

    private var favoriteDao : FavoriteDao

    init {
        favoriteDao = AppDatabase.getInstance(app).favoriteDao()
    }

    fun getFavorites() : List<Favorite> {
        return favoriteDao.getAll()
    }

    fun addFavorite(favorite : Favorite) {
        favoriteDao.create(favorite)
    }

    fun removeFavorite(favorite: Favorite) {
        favoriteDao.delete(favorite)
    }
}