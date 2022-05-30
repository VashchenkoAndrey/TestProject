package com.foamline.osm.api.dtos

import com.google.gson.annotations.SerializedName

data class RouteRequestBody(
    @SerializedName("coordinates")
    val coordinates: List<LatLng>,

    @SerializedName("language")
    val language: String
) {
    constructor(coordinates: List<LatLng>) : this(coordinates, "ru")
}