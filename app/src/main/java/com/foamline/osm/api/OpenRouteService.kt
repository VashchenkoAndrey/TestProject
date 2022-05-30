package com.foamline.osm.api

import com.foamline.osm.api.dtos.RouteRequestBody
import com.foamline.osm.api.dtos.RouteResponse
import com.foamline.osm.api.dtos.SearchResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Query

interface OpenRouteService {
    @POST("v2/directions/driving-hgv/json")
    suspend fun getRoute(
        @Body body: RouteRequestBody,
        @Header("Authorization") apiKey: String
    ): RouteResponse

    @GET("/geocode/search?size=1")
    suspend fun search(
        @Query("text") text: String,
        @Query("api_key") apiKey: String
    ): SearchResponse
}