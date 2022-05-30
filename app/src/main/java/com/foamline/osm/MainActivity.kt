package com.foamline.osm

import android.graphics.BitmapFactory
import android.os.Bundle
import android.preference.PreferenceManager
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.content.res.ResourcesCompat
import androidx.core.graphics.drawable.toBitmap
import androidx.core.graphics.scale
import androidx.lifecycle.lifecycleScope
import org.osmdroid.bonuspack.routing.Road
import org.osmdroid.bonuspack.routing.RoadManager
import org.osmdroid.config.Configuration
import org.osmdroid.tileprovider.tilesource.TileSourceFactory
import org.osmdroid.util.BoundingBox
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.CustomZoomButtonsController
import org.osmdroid.views.MapView
import org.osmdroid.views.overlay.Marker

class MainActivity : AppCompatActivity() {

    private val mapView: MapView?
        get() = findViewById(R.id.map_view)

    private val viewModel by viewModels<MainActivityViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Configuration.getInstance().load(
            this,
            PreferenceManager.getDefaultSharedPreferences(applicationContext)
        )
        setContentView(R.layout.activity_main)
        initMap()
        viewModel.markers.launchWhenCreated(lifecycleScope) {
            it.forEachIndexed { i, p ->
                mapView?.overlays?.add(Marker(mapView).apply {
                    position = GeoPoint(p.lat, p.lng)
                    icon = AppCompatResources.getDrawable(baseContext, if (i == 0) R.drawable.ic_marker_loading else R.drawable.ic_marker_unloading)
                })
            }
            mapView?.postInvalidate()
        }
        viewModel.route.launchWhenCreated(lifecycleScope) {
            if (it.isEmpty()) return@launchWhenCreated
            val road = Road(ArrayList(it.map { GeoPoint(it.lat, it.lng) }))
            mapView?.overlays?.add(RoadManager.buildRoadOverlay(road))
            mapView?.postInvalidate()
        }
    }

    private fun initMap() = mapView?.apply {
        setMultiTouchControls(true)
        setTileSource(TileSourceFactory.MAPNIK)
        zoomController?.setVisibility(CustomZoomButtonsController.Visibility.NEVER)
        isHorizontalMapRepetitionEnabled = false
        isVerticalMapRepetitionEnabled = false
        minZoomLevel = 3.0
        setScrollableAreaLimitDouble(
            BoundingBox(80.0, 180.0, -80.0, -180.0)
        )
    }

    override fun onResume() {
        super.onResume()
        mapView?.onResume()
    }

    override fun onPause() {
        super.onPause()
        mapView?.onPause()
    }
}