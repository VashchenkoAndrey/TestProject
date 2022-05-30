package com.foamline.osm

import android.graphics.Canvas
import android.graphics.Point
import android.graphics.Rect
import android.graphics.drawable.Drawable
import android.view.MotionEvent
import org.osmdroid.tileprovider.BitmapPool
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.MapView
import org.osmdroid.views.Projection
import org.osmdroid.views.overlay.OverlayWithIW

class Marker(
    private val mapView: MapView
) : OverlayWithIW() {
    var icon: Drawable? = null
    var position = GeoPoint(0.0, 0.0)
    var onMarkerClickListener: ((Marker, MapView?) -> Boolean)? = null
    var iconText: String? = null

    private var positionPixels = Point()
    private val resources = mapView.context.resources
    private val mapViewRepository = mapView.repository
    private val orientedMarkerRect = Rect()

    private val isInfoWindowShown: Boolean
        get() = if (mInfoWindow is MarkerInfoWindow) {
            (mInfoWindow as MarkerInfoWindow).let { it.isOpen && it.markerRef == this }
        } else {
            super.isInfoWindowOpen()
        }

    init {
        infoWindow = mapViewRepository.defaultMarkerInfoWindow
    }

    override fun draw(pCanvas: Canvas?, pProjection: Projection?) {
        icon ?: return
        val rotation = (pProjection?.orientation?.unaryMinus() ?: 0)
        drawAt(pCanvas, positionPixels.x, positionPixels.y, rotation.toFloat())
        if (isInfoWindowShown) {
            mInfoWindow.draw()
        }
    }

    override fun onDetach(mapView: MapView?) {
        BitmapPool.getInstance().asyncRecycle(icon)
        icon = null
        onMarkerClickListener = null
        relatedObject = null
        if (isInfoWindowShown) closeInfoWindow()
        mInfoWindow = null
        onDestroy()

        super.onDetach(mapView)
    }

    override fun onSingleTapConfirmed(e: MotionEvent?, mapView: MapView?): Boolean {
        val touched: Boolean = hitTest(e)
        return if (touched) {
            onMarkerClickListener?.invoke(this, mapView) ?: false
        } else touched
    }

    private fun drawAt(pCanvas: Canvas?, pX: Int, pY: Int, rotation: Float) {
        val markerWidth = mapView.context.resources.getDimensionPixelSize(R.dimen.marker_width)
        val markerHeight = mapView.context.resources.getDimensionPixelSize(R.dimen.marker_height)
    }

    fun hitTest(event: MotionEvent?): Boolean {
        return icon != null && orientedMarkerRect.contains(
            event?.x?.toInt() ?: 0,
            event?.y?.toInt() ?: 0
        )
    }

    fun setInfoWindow(infoWindow: MarkerInfoWindow) {
        mInfoWindow = infoWindow
    }
}