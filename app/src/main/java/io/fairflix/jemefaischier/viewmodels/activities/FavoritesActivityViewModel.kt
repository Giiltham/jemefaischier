package io.fairflix.jemefaischier.viewmodels.activities

import android.app.Application
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.fairflix.jemefaischier.models.Favorite
import io.fairflix.jemefaischier.repository.FavoriteRepository
import kotlinx.coroutines.launch

class FavoritesActivityViewModel(app: Application) : ViewModel() {
    private var favoriteRepository : FavoriteRepository
    val favorites : LiveData<List<Favorite>>

    init {
        favoriteRepository = FavoriteRepository(app)
        favorites = favoriteRepository.getFavorites()
    }

    fun deleteFavorite(favorite: Favorite){
        viewModelScope.launch {
            try {
                favoriteRepository.removeFavorite(favorite.osmId)
            }
            catch (e: Exception) {
                Log.e(null, "Tried to delete a favorite which doesn't existing", e)
            }
        }
    }


}
