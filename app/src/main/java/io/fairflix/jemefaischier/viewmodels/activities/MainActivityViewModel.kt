package io.fairflix.jemefaischier.viewmodels.activities

import android.app.Application
import androidx.lifecycle.ViewModel
import io.fairflix.jemefaischier.repository.FavoriteRepository

class MainActivityViewModel constructor(app : Application): ViewModel() {

    lateinit var repository: FavoriteRepository

    init {
        repository = FavoriteRepository(app)
    }
}