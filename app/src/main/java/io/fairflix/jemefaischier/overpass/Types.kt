package io.fairflix.jemefaischier.overpass

import org.osmdroid.util.GeoPoint

data class OverpassResponse (
    val version: Double,
    val generator: String,
    val osm3S: Osm3S,
    val elements: List<Element>
)
data class Element (
    val type: ElementType,
    val id: Long,
    val lat: Double? = null,
    val lon: Double? = null,
    val tags: Map<String, String>,
    val bounds: Bounds? = null,
    val nodes: List<Long>? = null,
    val geometry: List<Coordinates>? = null
)

data class Bounds (
    val minlat: Double,
    val minlon: Double,
    val maxlat: Double,
    val maxlon: Double,
){
    fun toCoordinates() : Coordinates {
        return Coordinates(maxlat - minlat, maxlon - minlon)
    }
}

data class Coordinates (
    val lat: Double,
    val lon: Double
)
{
    fun toGeoPoint() : GeoPoint{
        return GeoPoint(lat,lon)
    }
}

enum class ElementType {
    node,
    way,
    relation
}

data class Osm3S (
    val timestampOsmBase: String,
    val timestampAreasBase: String,
    val copyright: String
)
