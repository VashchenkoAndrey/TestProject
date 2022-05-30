package com.foamline.osm

import androidx.annotation.LayoutRes
import org.osmdroid.views.MapView
import org.osmdroid.views.overlay.infowindow.BasicInfoWindow

class MarkerInfoWindow(
    @LayoutRes layoutId: Int,
    mapView: MapView
) : BasicInfoWindow(layoutId, mapView) {
    var markerRef: Marker? = null

    override fun onOpen(item: Any?) {
        super.onOpen(item)
        markerRef = item as? Marker
    }

    override fun onClose() {
        super.onClose()
        markerRef = null
    }
}