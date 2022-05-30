package com.foamline.osm.api.dtos

import com.google.gson.annotations.SerializedName

data class SearchResponse(
    @SerializedName("features")
    val features: List<GeocodingApiDto>
)

data class GeocodingApiDto(
    @SerializedName("geometry")
    val geometry: GeometryApiDto,

    @SerializedName("properties")
    val properties: PropertiesApiDto
)

data class GeometryApiDto(
    @SerializedName("coordinates")
    val coordinates: LatLng
)

data class PropertiesApiDto(
    @SerializedName("name")
    val name: String,

    @SerializedName("street")
    val street: String,

    @SerializedName("postalcode")
    val postalcode: String,

    @SerializedName("match_type")
    val match_type: String,

    @SerializedName("country")
    val country: String,

    @SerializedName("region")
    val region: String,

    @SerializedName("county")
    val county: String,

    @SerializedName("continent")
    val continent: String,

    @SerializedName("label")
    val label: String
)
