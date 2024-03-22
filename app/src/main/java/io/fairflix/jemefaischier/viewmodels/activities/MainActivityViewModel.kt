package io.fairflix.jemefaischier.viewmodels.activities

import android.app.Application
import androidx.lifecycle.ViewModel
import io.fairflix.jemefaischier.repository.FavoriteRepository

class MainActivityViewModel(app : Application): ViewModel() {

    private lateinit var repository: FavoriteRepository

    init {
        repository = FavoriteRepository(app)
    }
}