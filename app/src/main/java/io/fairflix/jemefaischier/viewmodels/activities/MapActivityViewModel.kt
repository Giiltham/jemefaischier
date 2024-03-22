package io.fairflix.jemefaischier.viewmodels.activities

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.Gson
import io.fairflix.jemefaischier.overpass.Element
import io.fairflix.jemefaischier.overpass.OverpassApi
import io.fairflix.jemefaischier.overpass.OverpassResponse
import io.ktor.client.call.body
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class MapActivityViewModel : ViewModel() {
    val selectedOsmElement : MutableLiveData<Element>

    init {
        selectedOsmElement = MutableLiveData()
    }

    fun getOsmElementById(osmId : Long) : Job {
        return viewModelScope.launch {
            var response = OverpassApi.getElementById(osmId)
            val overpassResponse = Gson().fromJson(response.body<String>(), OverpassResponse::class.java)
            selectedOsmElement.value = overpassResponse.elements[0]
        }
    }
}