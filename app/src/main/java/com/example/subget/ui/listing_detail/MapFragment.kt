package com.example.subget.ui.listing_detail

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.subget.R
import com.example.subget.databinding.FragmentMapBinding
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

class MapFragment : Fragment() {

    private var _binding: FragmentMapBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentMapBinding.inflate(inflater, container, false)

        val mapSupport = childFragmentManager.findFragmentById(R.id.myMap) as SupportMapFragment
        mapSupport.getMapAsync { mMap ->
            mMap.mapType = GoogleMap.MAP_TYPE_NORMAL
            mMap.clear()
            val lat = arguments?.getDouble("lat")
            val lng = arguments?.getDouble("lng")
            val title = arguments?.getString("title")
            if(lat != null && lng != null) {
                val googlePlex = CameraPosition.builder()
                    .target(LatLng(lat, lng))
                    .zoom(10f)
                    .bearing(0f)
                    .tilt(45f)
                    .build()

                mMap.animateCamera(CameraUpdateFactory.newCameraPosition(googlePlex), 5000, null)
                mMap.addMarker(MarkerOptions().position(LatLng(lat, lng)).title(title))
            }
        }
        return binding.root
    }
}