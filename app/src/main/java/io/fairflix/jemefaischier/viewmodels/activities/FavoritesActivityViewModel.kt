package io.fairflix.jemefaischier.viewmodels.activities

import android.app.Application
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.fairflix.jemefaischier.dao.FavoriteDao
import io.fairflix.jemefaischier.db.AppDatabase
import io.fairflix.jemefaischier.models.Favorite
import io.fairflix.jemefaischier.repository.FavoriteRepository
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class FavoritesActivityViewModel(app: Application) : ViewModel() {
    private var favoriteRepository : FavoriteRepository
    val favorites : MutableLiveData<List<Favorite>>

    init {
        favoriteRepository = FavoriteRepository(app)
        favorites = MutableLiveData<List<Favorite>>()
    }

    fun getFavorites() {
    }
}