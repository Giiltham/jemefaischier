package io.fairflix.jemefaischier.views.activities

import android.os.Bundle
import android.os.PersistableBundle
import androidx.appcompat.app.AppCompatActivity
import io.fairflix.jemefaischier.R
import io.fairflix.jemefaischier.databinding.FavoritesActivityBinding
import io.fairflix.jemefaischier.viewmodels.activities.FavoritesActivityViewModel

class FavoritesActivity : AppCompatActivity() {
    private lateinit var viewModel : FavoritesActivityViewModel
    private lateinit var binding : FavoritesActivityBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = FavoritesActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.test.text = "test"
        viewModel = FavoritesActivityViewModel(application)

    }
}