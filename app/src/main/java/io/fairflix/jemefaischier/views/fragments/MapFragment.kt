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
import android.net.Uri
import io.fairflix.jemefaischier.api.OverpassApi
import io.fairflix.jemefaischier.databinding.FragmentMapBinding
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import org.osmdroid.config.Configuration
import org.osmdroid.util.GeoPoint
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import io.ktor.client.engine.cio.*
import kotlinx.coroutines.runBlocking

//private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [MapFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class MapFragment : Fragment() {

    private var _binding: FragmentMapBinding? = null
    private val binding get() = _binding!!

    private lateinit var map : MapView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMapBinding.inflate(inflater, container, false)
        map = binding.map
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
        map.minZoomLevel = (15.0)
        map.maxZoomLevel = (21.0)
    }

    fun requirePermissionsIfNecessary(){
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

    override fun onPause() {
        super.onPause()
        map.onPause()
    }

    override fun onResume() {
        super.onResume()
        map.onResume()
    }

}