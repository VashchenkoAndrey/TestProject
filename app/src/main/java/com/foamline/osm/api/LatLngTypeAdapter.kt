package com.foamline.osm.api

import com.foamline.osm.api.dtos.LatLng
import com.google.gson.JsonArray
import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import com.google.gson.JsonSerializationContext
import com.google.gson.JsonSerializer
import java.lang.reflect.Type

class LatLngTypeAdapter : JsonSerializer<LatLng>, JsonDeserializer<LatLng> {
    override fun serialize(
        src: LatLng?,
        typeOfSrc: Type?,
        context: JsonSerializationContext?
    ): JsonElement {
        return JsonArray(2).apply {
            add(src?.lng)
            add(src?.lat)
        }
    }

    override fun deserialize(
        json: JsonElement?,
        typeOfT: Type?,
        context: JsonDeserializationContext?
    ): LatLng {
        return json?.asJsonArray?.let {
            LatLng(it.get(1).asDouble, it.get(0).asDouble)
        } ?: LatLng(0.0, 0.0)
    }
}