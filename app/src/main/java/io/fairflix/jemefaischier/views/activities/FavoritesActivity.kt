package io.fairflix.jemefaischier.views.activities

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import io.fairflix.jemefaischier.adapters.FavoriteItemAdapter
import io.fairflix.jemefaischier.databinding.FavoritesActivityBinding
import io.fairflix.jemefaischier.models.Favorite
import io.fairflix.jemefaischier.utils.OnItemClickListener
import io.fairflix.jemefaischier.utils.OnItemDeleteListener
import io.fairflix.jemefaischier.viewmodels.activities.FavoritesActivityViewModel

class FavoritesActivity : AppCompatActivity(), OnItemClickListener<Favorite>, OnItemDeleteListener<Favorite> {
    private lateinit var viewModel : FavoritesActivityViewModel
    private lateinit var binding : FavoritesActivityBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = FavoritesActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = FavoritesActivityViewModel(application)

        binding.recycler.layoutManager = LinearLayoutManager(application, RecyclerView.VERTICAL, false)

        viewModel.favorites.observe(this) { it: List<Favorite> ->
            val itemAdapter = FavoriteItemAdapter.ItemAdapter(it.toMutableList(), this, this)
            binding.recycler.adapter = itemAdapter

            if(it.isEmpty()){
                binding.noFavoriteText.visibility = View.VISIBLE
            }
            else {
                binding.noFavoriteText.visibility = View.INVISIBLE
            }
        }
    }

    override fun onItemClick(item: Favorite) {

        val intent = Intent(this, MapActivity::class.java)
        intent.putExtra("osmId",item.osmId)
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        startActivity(intent)

    }

    override fun onItemDelete(item: Favorite) {
        viewModel.deleteFavorite(item)
    }
}