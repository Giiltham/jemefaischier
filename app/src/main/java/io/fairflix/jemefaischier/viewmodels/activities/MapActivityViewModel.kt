package io.fairflix.jemefaischier.viewmodels.activities

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.fairflix.jemefaischier.api.OverpassApi
import io.ktor.client.call.body
import io.ktor.client.statement.HttpResponse
import kotlinx.coroutines.launch
import kotlinx.serialization.*
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.jsonArray
import kotlinx.serialization.json.jsonObject

class MapActivityViewModel : ViewModel() {

    var searchElements = MutableLiveData<List<JsonElement>>()
    init {
        searchElements.value = emptyList()
    }
    fun cityamenity(){
        viewModelScope.launch {
            val response = OverpassApi.getAmenityInCity("cafe", "lyon")
            val json = Json.parseToJsonElement(response.body())
            println("JSON===")
            searchElements.value = json.jsonObject["elements"]!!.jsonArray.toList()
        }
    }


}

class Cinema {

}
