package io.fairflix.jemefaischier

import android.content.Intent
import android.os.Bundle
import android.renderscript.ScriptGroup.Binding
import androidx.appcompat.app.AppCompatActivity
import io.fairflix.jemefaischier.databinding.MainActivityBinding
import io.fairflix.jemefaischier.viewmodels.activities.MainActivityViewModel
import io.fairflix.jemefaischier.views.activities.FavoritesActivity
import io.fairflix.jemefaischier.views.activities.MapActivity

class MainActivity : AppCompatActivity() {

    private lateinit var binding : MainActivityBinding
    private lateinit var viewModel : MainActivityViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = MainActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.searchBtn.setOnClickListener {
            val intent = Intent(this, MapActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)
        }

        binding.favoriteBtn.setOnClickListener {
            val intent = Intent(this, FavoritesActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)
        }

        viewModel = MainActivityViewModel(application)

    }


}
