package com.foamline.osm.data

import com.foamline.osm.api.dtos.LatLng

class GetCoordinatesUseCase(
    private val repository: OpenRouteRepository
) : CoroutineUseCase<String, LatLng?> {
    override suspend fun invoke(params: String): LatLng? {
        val places = repository.search(params).features
        return if (places.isEmpty()) null else places[0].geometry.coordinates
    }
}