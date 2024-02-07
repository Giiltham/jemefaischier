package io.fairflix.jemefaischier.api

import io.ktor.client.HttpClient
import io.ktor.client.engine.cio.CIO
import io.ktor.client.request.request
import io.ktor.client.request.setBody
import io.ktor.client.statement.HttpResponse
import io.ktor.client.statement.bodyAsText
import io.ktor.client.statement.readText
import io.ktor.http.HttpMethod
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

object OverpassApi {
    suspend fun getAmenityInCity(amenity : String, city : String): HttpResponse {
        val str = "[out:json][timeout:25];\n" +
                "area[name=\"Lyon\"]->.searchArea;\n" +
                "nwr[\"amenity\"=\"cinema\"](area.searchArea);\n" +
                "out geom;"
        return makeCall(str)
    }

    private suspend fun makeCall(body : String) : HttpResponse {
        val client = HttpClient(CIO) {
        }

        val response =  client.request("https://overpass-api.de/api/interpreter",) {
            method = HttpMethod.Post
            setBody(body)
        }
        return response
    }
}