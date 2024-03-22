package io.fairflix.jemefaischier.views.fragments

import android.content.res.Resources
import android.graphics.drawable.Drawable
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import com.google.android.material.button.MaterialButton
import com.google.android.material.card.MaterialCardView
import io.fairflix.jemefaischier.R
import io.fairflix.jemefaischier.databinding.FragmentElementDetailBinding
import io.fairflix.jemefaischier.models.Favorite
import io.fairflix.jemefaischier.overpass.Element
import io.fairflix.jemefaischier.utils.observeOnce
import io.fairflix.jemefaischier.viewmodels.fragments.MapFragmentViewModel
import io.fairflix.jemefaischier.viewmodels.fragments.MapFragmentViewModelFactory


class ElementDetailFragment : Fragment() {

    private val viewModel: MapFragmentViewModel by activityViewModels {
        MapFragmentViewModelFactory(requireActivity().application)
    }

    private var _binding: FragmentElementDetailBinding? = null
    private val binding get() = _binding!!

    private lateinit var card : MaterialCardView
    private lateinit var title : TextView
    private lateinit var description : TextView
    private lateinit var actionButtons : LinearLayout
    private lateinit var closeBtn : ImageButton

    override fun onViewCreated(view : View, savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        card.visibility = View.INVISIBLE


        addActionButton(R.drawable.ic_not_favorite, R.id.favoriteBtn) { btn -> onLoveButtonClicked(btn) }

        viewModel.markerClickedLiveData.observe(viewLifecycleOwner) {
            title.text = it.tags.getOrDefault("name", "Nom introuvable")
            card.visibility = View.VISIBLE

            viewModel.getFavorite(it.id).observeOnce(viewLifecycleOwner) { favorite ->
                val btn = actionButtons.findViewById<MaterialButton>(R.id.favoriteBtn)
                if (favorite == null) {
                    btn.icon = AppCompatResources.getDrawable(requireContext(), R.drawable.ic_not_favorite)
                } else {
                    btn.icon = AppCompatResources.getDrawable(requireContext(), R.drawable.ic_favorite)
                }
            }
        }

        closeBtn.setOnClickListener {
            card.visibility = View.INVISIBLE
        }

    }

    private fun onLoveButtonClicked(btn : MaterialButton){
        if(viewModel.markerClickedLiveData.value != null){
            val element = viewModel.markerClickedLiveData.value!!

            viewModel.getFavorite(element.id).observeOnce(viewLifecycleOwner) { favorite ->
                if (favorite == null) {
                    btn.icon = ResourcesCompat.getDrawable(resources, R.drawable.ic_favorite, null)
                    val favorite = Favorite(0,element.id, binding.title.text.toString())
                    viewModel.addToFavorites(favorite)
                } else {
                    btn.icon = ResourcesCompat.getDrawable(resources, R.drawable.ic_not_favorite, null)
                    viewModel.removeFromFavorites(element.id)
                }

            }
        }
    }

    private fun addActionButton(icon : Int, id : Int, callback: (btn : MaterialButton) -> Unit){
        val btn = View.inflate(context, R.layout.icon_material_button, null) as MaterialButton
        btn.icon = ResourcesCompat.getDrawable(resources, icon, null)
        btn.id = id
        btn.setOnClickListener{
            callback(btn)
        }
        val w = (30 * Resources.getSystem().displayMetrics.density).toInt()
        val h = (37 * Resources.getSystem().displayMetrics.density).toInt()
        actionButtons.addView(btn, w,h)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentElementDetailBinding.inflate(inflater, container, false)

        card = binding.card
        closeBtn = binding.closeBtn
        title = binding.title
        description = binding.description
        actionButtons = binding.actionButtons
        return binding.root
    }
}