package io.fairflix.jemefaischier.viewmodels.fragments

import android.app.Application
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import com.google.gson.Gson
import io.fairflix.jemefaischier.models.Favorite
import io.fairflix.jemefaischier.overpass.Element
import io.fairflix.jemefaischier.overpass.OverpassApi
import io.fairflix.jemefaischier.overpass.OverpassResponse
import io.fairflix.jemefaischier.overpass.Utils
import io.fairflix.jemefaischier.repository.FavoriteRepository
import io.ktor.client.call.body
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import org.osmdroid.views.MapView

class MapFragmentViewModel(app: Application) : ViewModel() {
    private val favoriteRepository : FavoriteRepository

    val markerClickedLiveData: MutableLiveData<Element> = MutableLiveData()

    init {
        favoriteRepository = FavoriteRepository(app)
    }

    fun addMarkersForAmenityInCity(map : MapView, amenity : String, city : String) : Job {
        return viewModelScope.launch {
            val response = OverpassApi.getAmenityInCity(amenity, city)

            val overpassResponse = Gson().fromJson(response.body<String>(), OverpassResponse::class.java)
            val markers = Utils.createMarkersFromElements(map, overpassResponse.elements)

            markers.forEach() { markerElement ->
                markerElement.marker.setOnMarkerClickListener { marker,map ->
                    markerClickedLiveData.postValue(markerElement.element)
                    map.controller.animateTo(marker.position)
                    false
                }
            }
            map.overlays.addAll(markers.map { markerElement -> markerElement.marker })
            map.invalidate()
        }
    }

    fun addToFavorites(id:Long){
        viewModelScope.launch {
            try {
                favoriteRepository.addFavorite(Favorite(0,id))
            }
            catch (e: Exception) {
                Log.e(null, "Tried to create a favorite already existing", e)
            }
        }
    }

    fun removeFromFavorites(id:Long){
        viewModelScope.launch {
            try {
                favoriteRepository.removeFavorite(id)
            }
            catch (e: Exception) {
                Log.e(null, "Tried to remove a favorite which doesn't exist : ", e)
            }
        }
    }

    fun getFavorite(id: Long) : LiveData<Favorite?>  {
        return favoriteRepository.getFavorite(id)
    }

}

class MapFragmentViewModelFactory(private val application: Application) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MapFragmentViewModel::class.java)) {
            return MapFragmentViewModel(application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}