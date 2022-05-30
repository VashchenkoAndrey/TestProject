package com.foamline.osm.api

import com.foamline.osm.api.dtos.RouteRequestBody
import com.foamline.osm.api.dtos.RouteResponse
import com.foamline.osm.api.dtos.SearchResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Retrofit

private const val API_KEY = "5b3ce3597851110001cf62487fc456b885f4449bb5942b33293fc187"

class OpenRouteApi(retrofit: Retrofit) {
    private val service: OpenRouteService = retrofit.create(OpenRouteService::class.java)

    suspend fun getRoute(body: RouteRequestBody): RouteResponse {
        return withContext(Dispatchers.IO) {
            service.getRoute(body, API_KEY)
        }
    }

    suspend fun search(text: String): SearchResponse {
        return withContext(Dispatchers.IO) {
            service.search(text, API_KEY)
        }
    }
}