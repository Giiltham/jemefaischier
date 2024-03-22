package io.fairflix.jemefaischier.views.activities

import android.content.Intent
import android.os.Bundle
import androidx.activity.addCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentManager
import io.fairflix.jemefaischier.MainActivity
import io.fairflix.jemefaischier.R
import io.fairflix.jemefaischier.databinding.MapActivityBinding
import io.fairflix.jemefaischier.viewmodels.activities.MapActivityViewModel
import io.fairflix.jemefaischier.views.fragments.MapFragment

class MapActivity : AppCompatActivity(){
    private lateinit var viewModel : MapActivityViewModel
    private lateinit var binding : MapActivityBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = MapActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = MapActivityViewModel()


        val osmId = intent.getLongExtra("osmId", -1L)
        if(osmId != -1L) {
            viewModel.getOsmElementById(osmId)
        }

        viewModel.selectedOsmElement.observe(this) {
            val mapFragment = binding.map.getFragment<MapFragment>()
            mapFragment.openMarker(it)
        }

        binding.favoriteBtn.setOnClickListener {
            val intent = Intent(this, FavoritesActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)
        }
    }
}