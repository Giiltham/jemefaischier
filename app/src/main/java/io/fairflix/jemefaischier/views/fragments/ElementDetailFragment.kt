package io.fairflix.jemefaischier.views.fragments

import android.content.Intent
import android.content.res.Resources
import android.net.Uri
import android.os.Bundle
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewGroup.LayoutParams
import android.widget.ImageButton
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.google.android.material.button.MaterialButton
import com.google.android.material.card.MaterialCardView
import io.fairflix.jemefaischier.R
import io.fairflix.jemefaischier.databinding.FragmentElementDetailBinding
import io.fairflix.jemefaischier.models.Favorite
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



        viewModel.markerClickedLiveData.observe(viewLifecycleOwner) {
            actionButtons.removeAllViews()
            title.text = it.tags.getOrDefault("name", "Nom introuvable")
            card.visibility = View.VISIBLE

            addActionButton(R.drawable.ic_not_favorite, R.id.favoriteBtn) { btn -> onLoveButtonClicked(btn) }

            if(it.tags.containsKey("website")){
                addActionButton(R.drawable.website, R.id.websiteBtn) { btn -> onWebsiteButtonClicked(
                    it.tags["website"]!!) }
            }

            if(it.tags.containsKey("phone")){
                addActionButton(R.drawable.phone, R.id.phoneBtn) { btn -> onPhoneButtonClicked(it.tags["phone"]!!) }
            }
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

    private fun onWebsiteButtonClicked(website : String){
        val intent = Intent(Intent.ACTION_VIEW)
        intent.setData(Uri.parse(website))

        startActivity(intent)
    }
    private fun onPhoneButtonClicked(phone : String){
        val intent = Intent(Intent.ACTION_DIAL)
        intent.setData(Uri.parse("tel:$phone"))
        startActivity(intent)
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

        val params =  LinearLayout.LayoutParams(w, h)
        params.setMargins(0, 0, 10, 0)
        btn.layoutParams = params;
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