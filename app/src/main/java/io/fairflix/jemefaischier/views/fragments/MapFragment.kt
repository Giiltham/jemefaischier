package io.fairflix.jemefaischier.views.fragments

import android.content.pm.PackageManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import org.osmdroid.tileprovider.tilesource.TileSourceFactory
import org.osmdroid.views.MapView
import android.Manifest
import android.widget.ProgressBar
import androidx.cardview.widget.CardView
import androidx.fragment.app.activityViewModels
import io.fairflix.jemefaischier.databinding.FragmentMapBinding
import io.fairflix.jemefaischier.overpass.Element
import io.fairflix.jemefaischier.viewmodels.fragments.MapFragmentViewModel
import io.fairflix.jemefaischier.viewmodels.fragments.MapFragmentViewModelFactory
import kotlinx.coroutines.Job
import org.osmdroid.config.Configuration
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.CustomZoomButtonsController

class MapFragment : Fragment() {

    val viewModel: MapFragmentViewModel by activityViewModels() {
        MapFragmentViewModelFactory(requireActivity().application)
    }
    private var _binding: FragmentMapBinding? = null
    private val binding get() = _binding!!

    private lateinit var map : MapView
    private lateinit var errorCard : CardView
    private lateinit var loadingCircle : ProgressBar

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMapBinding.inflate(inflater, container, false)
        map = binding.map
        errorCard = binding.errorCard
        loadingCircle = binding.loadingCircle
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        Configuration.getInstance().userAgentValue = "JeMeFaisChier"

        requirePermissionsIfNecessary()

        map.setTileSource(TileSourceFactory.MAPNIK)
        map.controller.setZoom(15.0)
        map.controller.setCenter(GeoPoint(45.763420, 4.834277))
        map.zoomController.setVisibility(CustomZoomButtonsController.Visibility.NEVER)
        map.minZoomLevel = (15.0)
        map.maxZoomLevel = (21.0)
        map.setMultiTouchControls(true)

        addMarkersForAmenityInCity("cafe", "Lyon").invokeOnCompletion {
            // In case we come from the favorites, the markerclicked live data is initialised
            val clickedLiveData = viewModel.markerClickedLiveData.value
            if(clickedLiveData != null) {
              map.controller.animateTo(clickedLiveData.lon!!.toInt(), clickedLiveData.lat!!.toInt())
            }
        }
    }

    private fun addMarkersForAmenityInCity(amenity : String, city : String): Job {
        loadingCircle.visibility = ProgressBar.VISIBLE
        val job = viewModel.addMarkersForAmenityInCity(map, amenity, city)
        job.invokeOnCompletion { throwable ->
            loadingCircle.visibility = ProgressBar.INVISIBLE

            if(throwable != null) {
                errorCard.visibility = CardView.VISIBLE
            }
        }
        return job
    }

    private fun requirePermissionsIfNecessary(){
        val activity = requireActivity()
        val permissionCheckFine = ContextCompat.checkSelfPermission(
            activity,
            Manifest.permission.ACCESS_FINE_LOCATION
        )
        val permissionCheckCoarse = ContextCompat.checkSelfPermission(
            activity,
            Manifest.permission.ACCESS_COARSE_LOCATION
        )
        if (permissionCheckFine != PackageManager.PERMISSION_GRANTED || permissionCheckCoarse != PackageManager.PERMISSION_GRANTED) {
            // Request permissions
            ActivityCompat.requestPermissions(
                activity,
                arrayOf(
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                ),
                0
            )
        }
    }

    fun openMarker(el : Element){
        viewModel.markerClickedLiveData.postValue(el)
    }

    override fun onPause() {
        super.onPause()
        map.onPause()
    }

    override fun onResume() {
        super.onResume()
        map.onResume()
    }

    override fun onDestroy() {
        super.onDestroy()
        map.onDetach()
    }
}