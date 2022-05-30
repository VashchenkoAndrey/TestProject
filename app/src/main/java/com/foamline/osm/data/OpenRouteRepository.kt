package com.foamline.osm.data

import com.foamline.osm.api.LatLngTypeAdapter
import com.foamline.osm.api.OpenRouteApi
import com.foamline.osm.api.dtos.LatLng
import com.foamline.osm.api.dtos.RouteRequestBody
import com.foamline.osm.api.dtos.RouteResponse
import com.foamline.osm.api.dtos.SearchResponse
import com.google.gson.Gson
import java.util.concurrent.TimeUnit
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class OpenRouteRepository {
    private val okHttpClient: OkHttpClient by lazy {
        OkHttpClient.Builder()
            .addInterceptor(
                HttpLoggingInterceptor()
                    .setLevel(HttpLoggingInterceptor.Level.BASIC)
            )
            .readTimeout(20L, TimeUnit.SECONDS)
            .writeTimeout(20L, TimeUnit.SECONDS)
            .callTimeout(20L, TimeUnit.SECONDS)
            .connectTimeout(20L, TimeUnit.SECONDS)
            .build()
    }

    private val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl("https://api.openrouteservice.org/")
            .client(okHttpClient)
            .addConverterFactory(
                GsonConverterFactory.create(
                    Gson().newBuilder()
                        .registerTypeAdapter(LatLng::class.java, LatLngTypeAdapter())
                        .create()
                )
            )
            .build()
    }

    private val api: OpenRouteApi by lazy {
        OpenRouteApi(retrofit)
    }

    suspend fun getRoute(coordinates: List<LatLng>): RouteResponse {
        return api.getRoute(RouteRequestBody(coordinates))
    }

    suspend fun search(text: String): SearchResponse {
        return api.search(text)
    }
}