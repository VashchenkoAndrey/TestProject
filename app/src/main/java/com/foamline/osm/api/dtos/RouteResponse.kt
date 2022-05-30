package com.foamline.osm.api.dtos

import com.google.gson.annotations.SerializedName

data class RouteResponse(
    @SerializedName("routes")
    val routes: List<RouteApiDto>
)

data class RouteApiDto(
    @SerializedName("summary")
    val summary: RouteSummaryApiDto,

    @SerializedName("segments")
    val segments: List<RouteSegmentApiDto>,

    @SerializedName("geometry")
    val geometry: String
)

data class RouteSummaryApiDto(
    @SerializedName("distance")
    val distance: Double,

    @SerializedName("duration")
    val duration: Double
)

data class RouteSegmentApiDto(
    @SerializedName("distance")
    val distance: Double,

    @SerializedName("duration")
    val duration: Double,

    @SerializedName("steps")
    val steps: List<RouteStepApiDto>
)

data class RouteStepApiDto(
    @SerializedName("distance")
    val distance: Double,

    @SerializedName("duration")
    val duration: Double,

    @SerializedName("type")
    val type: Int,

    @SerializedName("instruction")
    val instruction: String,

    @SerializedName("name")
    val name: String,

    @SerializedName("way_points")
    val wayPoints: List<Int>
)