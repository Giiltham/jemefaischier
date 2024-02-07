package io.fairflix.jemefaischier.views.activities

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import io.fairflix.jemefaischier.databinding.MapActivityBinding
import io.fairflix.jemefaischier.viewmodels.activities.MapActivityViewModel
import io.ktor.client.call.body
import io.ktor.client.statement.bodyAsText
import io.ktor.client.statement.request
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class MapActivity : AppCompatActivity(){
    private lateinit var viewModel : MapActivityViewModel
    private lateinit var binding : MapActivityBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = MapActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.favoriteBtn.setOnClickListener {
            val intent = Intent(this, FavoritesActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)
        }

        viewModel = MapActivityViewModel()

        viewModel.cityamenity()
    }
}