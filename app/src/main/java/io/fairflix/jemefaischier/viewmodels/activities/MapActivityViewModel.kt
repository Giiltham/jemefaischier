package io.fairflix.jemefaischier.viewmodels.activities

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.Gson
import io.fairflix.jemefaischier.overpass.OverpassApi
import io.fairflix.jemefaischier.overpass.OverpassResponse
import io.fairflix.jemefaischier.overpass.Utils as OverpassUtils
import io.ktor.client.call.body
import io.ktor.client.statement.HttpResponse
import kotlinx.coroutines.launch
import org.osmdroid.views.MapView
import java.io.File

class MapActivityViewModel : ViewModel() {
}