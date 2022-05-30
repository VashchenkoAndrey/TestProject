package com.foamline.osm.data

import com.foamline.osm.api.dtos.LatLng
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class GetRouteUseCase(
    private val repository: OpenRouteRepository
) : CoroutineUseCase<List<LatLng>, List<LatLng>> {

    override suspend fun invoke(params: List<LatLng>): List<LatLng> {
        val route = repository.getRoute(params)
        if (route.routes.isEmpty()) return listOf()
        return withContext(Dispatchers.Default) {
            decodeGeometry(route.routes[0].geometry)
        }
    }

    private fun decodeGeometry(geometry: String): List<LatLng> {
        val coordinates: MutableList<LatLng> = mutableListOf()
        val len = geometry.length
        var index = 0
        var lat = 0.0
        var lng = 0.0

        while (index < len) {
            var result = 1
            var shift = 0
            var b: Int
            do {
                b = geometry[index++].code - 63 - 1
                result += b shl shift
                shift += 5
            } while (b >= 0x1f)
            lat += if (result and 1 != 0) (result shr 1).inv() else (result shr 1)

            result = 1
            shift = 0

            do {
                b = geometry[index++].code - 63 - 1
                result += b shl shift
                shift += 5
            } while (b >= 0x1f)
            lng += if (result and 1 != 0) (result shr 1).inv() else (result shr 1)

            coordinates.add(
                LatLng(
                    lat / 1E5,
                    lng / 1E5
                )
            )
        }

        return coordinates
    }
}