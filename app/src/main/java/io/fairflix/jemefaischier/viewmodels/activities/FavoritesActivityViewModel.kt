package io.fairflix.jemefaischier.viewmodels.activities

import android.app.Application
import androidx.lifecycle.ViewModel
import io.fairflix.jemefaischier.dao.FavoriteDao
import io.fairflix.jemefaischier.db.AppDatabase
import io.fairflix.jemefaischier.models.Favorite
import io.fairflix.jemefaischier.repository.FavoriteRepository

class FavoritesActivityViewModel(app: Application) : ViewModel() {
    private var favoriteRepository : FavoriteRepository

    init {
        favoriteRepository = FavoriteRepository(app)
    }

    fun getFavorites() : List<Favorite>{
        return favoriteRepository.getFavorites()
    }
}