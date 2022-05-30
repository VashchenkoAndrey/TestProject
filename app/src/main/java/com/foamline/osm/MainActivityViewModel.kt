package com.foamline.osm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.foamline.osm.api.dtos.LatLng
import com.foamline.osm.data.GetCoordinatesUseCase
import com.foamline.osm.data.GetRouteUseCase
import com.foamline.osm.data.OpenRouteRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class MainActivityViewModel : ViewModel() {
    private val repository by lazy {
        OpenRouteRepository()
    }

    private val getRouteUseCase by lazy {
        GetRouteUseCase(repository)
    }

    private val getCoordinatesUseCase by lazy {
        GetCoordinatesUseCase(repository)
    }

    private val places = listOf(
        "Омск",
        "Татарск",
        "Новосибирск"
    )

    private val _route = MutableStateFlow<List<LatLng>>(listOf())
    val route: Flow<List<LatLng>> = _route

    private val _markers = MutableStateFlow<List<LatLng>>(listOf())
    val markers: Flow<List<LatLng>> = _markers

    init {
        viewModelScope.launch {
            val coordinates = places.mapNotNull {
                getCoordinatesUseCase.invoke(it)
            }
            _markers.value = coordinates
            val route = getRouteUseCase.invoke(coordinates)
            _route.value = route
        }
    }
}