package com.example.myplaces

import android.content.pm.PackageManager
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.preference.PreferenceManager
import org.osmdroid.config.Configuration
import org.osmdroid.events.MapEventsReceiver
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.MapView
import org.osmdroid.views.overlay.MapEventsOverlay
import org.osmdroid.views.overlay.mylocation.GpsMyLocationProvider
import org.osmdroid.views.overlay.mylocation.MyLocationNewOverlay

class MapFragment : Fragment() {
    private lateinit var map: MapView
    private val locationViewModel:LocationViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_map, container, false)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val ctx = activity?.applicationContext
        Configuration.getInstance().load(ctx, PreferenceManager.getDefaultSharedPreferences(ctx))
        map = requireView().findViewById(R.id.map)
        map.setMultiTouchControls(true)
        setupLocation()
        map.controller.setZoom(15.0)

        //val startPoint = GeoPoint(43.3209, 21.8958)
        //map.controller.setCenter(startPoint)
    }

    private fun setupLocation() {
        if (ContextCompat.checkSelfPermission(requireContext(), android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
            ContextCompat.checkSelfPermission(requireContext(), android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            val requestPermissionLauncher = registerForActivityResult(
                ActivityResultContracts.RequestPermission()
            ) { isGranted: Boolean ->
                if (isGranted) {
                    setMyLocationOverlay()
                    setOnMapClickOverlay()
                }
            }

            // Pokretanje zahtjeva za dozvolom
            requestPermissionLauncher.launch(android.Manifest.permission.ACCESS_FINE_LOCATION)
        } else {
            setMyLocationOverlay()
            setOnMapClickOverlay()
        }
    }

    private fun setMyLocationOverlay() {
        val myLocationOverlay = MyLocationNewOverlay(GpsMyLocationProvider(requireContext()), map)
        myLocationOverlay.enableMyLocation()
        map.overlays.add(myLocationOverlay)
    }
    private fun setOnMapClickOverlay()
    {
       var recive=object:MapEventsReceiver{
           override fun singleTapConfirmedHelper(p: GeoPoint?): Boolean {
               var lon= p?.longitude.toString()
               var lati= p?.latitude.toString()
               locationViewModel.setLocation(lon,lati)
               findNavController().popBackStack()
               return true
           }

           override fun longPressHelper(p: GeoPoint?): Boolean {
               return false
           }
       }
        var overlayEvents=MapEventsOverlay(recive)
        map.overlays.add(overlayEvents)
    }

    override fun onResume() {
        super.onResume()
    }

    override fun onPause() {
        super.onPause()
    }
}
