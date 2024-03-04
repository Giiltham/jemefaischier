package io.fairflix.jemefaischier.overpass

import org.osmdroid.util.GeoPoint
import org.osmdroid.views.MapView
import org.osmdroid.views.overlay.Marker
import java.lang.Exception

object Utils {
    fun createMarkersFromElements(map : MapView, elements : List<Element>) : MutableList<MarkerElement>{
        val markers = mutableListOf<MarkerElement>()
        for(e in elements) {
            val point = getGeoPoint(e)
            val marker = createMarker(map, point)
            val markerElement = MarkerElement(marker, e)
            markers.add(markerElement)
        }
        return markers
    }

    private fun createMarker(map : MapView, geoPoint : GeoPoint) : Marker {
        val marker = Marker(map)
        marker.position = geoPoint
        marker.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM);
        return marker
    }

    private fun getGeoPoint(e : Element) : GeoPoint = when (e.type)
    {
        ElementType.way -> {
            e.bounds!!.toCoordinates().toGeoPoint()
        }

        ElementType.node -> {
            GeoPoint(e.lat!!, e.lon!!)
        }

        ElementType.relation -> {
            e.bounds!!.toCoordinates().toGeoPoint()
        }
    }
}