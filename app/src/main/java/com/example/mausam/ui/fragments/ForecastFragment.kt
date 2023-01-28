package com.example.mausam.ui.fragments

import android.Manifest
import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.location.Location
import android.location.LocationManager
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import android.provider.Settings
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat.getSystemService
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager

import com.example.mausam.adapters.DayForecastadapter
import com.example.mausam.databinding.FragmentForecastBinding
import com.example.mausam.util.Constants
import com.example.mausam.util.NetworkResult
import com.example.mausam.viewmodels.ForecastViewModel
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.material.tabs.TabLayout
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
@AndroidEntryPoint
class ForecastFragment : Fragment() {
    private  var _binding:FragmentForecastBinding?=null
    private val binding get()=_binding!!
    private lateinit var forecastViewModel: ForecastViewModel
    private val mAdapter by lazy { DayForecastadapter() }
    private var mLatitude: Double = 0.0 // A variable which will hold the latitude value.
    private var mLongitude: Double = 0.0
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    val queries: HashMap<String, String> = HashMap()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        forecastViewModel=ViewModelProvider(requireActivity()).get(ForecastViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding= FragmentForecastBinding.inflate(inflater,container,false)
        val view=binding.root

        setupRecyclerView()
        requestApiDataforecast()
        setupRecyclerView()

        binding.autoDetectText.setOnClickListener {
            mLatitude.toString()
            mLongitude.toString()
            //requestLocationPermission()

        }

        return view
    }

    private fun setupRecyclerView() {
        binding.forecastRecyclerview.adapter=mAdapter
        binding.forecastRecyclerview.layoutManager=LinearLayoutManager(requireContext())
    }


    private fun requestApiDataforecast() {
        forecastViewModel.getForecast(applyQueries())
        forecastViewModel.forecastResponse.observe(viewLifecycleOwner,{response ->
            when(response){
                is NetworkResult.Success -> {
                    response.data?.let {
                        mAdapter.forecast=it.forecast.forecastday
                        //resultBundle.putParcelable("weatherBundle",it.forecast.forecastday)
                        setupRecyclerView()
                    }
                }
                is NetworkResult.Error -> {
                    Toast.makeText(requireContext(), response.message.toString(), Toast.LENGTH_SHORT).show()
                }
                is NetworkResult.Loading ->{
                    //showShimmerEffect()
                }
            }
        })
    }

    private fun applyQueries(): Map<String, String> {
        val queries: HashMap<String, String> = HashMap()
        queries["key"] = Constants.API_KEY
        queries["q"] = "Jaipur India"
        queries["aqi"]="yes"
        queries["days"]="3"
        queries["alerts"]="no"

        return queries
    }


    /*private fun showRationalDialog(title: String, message: String){
        val builder: AlertDialog.Builder = AlertDialog.Builder(requireContext())
        builder.setTitle(title)
            .setMessage(message)
            .setPositiveButton(
                "GO TO SETTINGS"
            ) { _, _ ->
                try {
                    val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
                    val uri = Uri.fromParts("package", requireContext().packageName, null)
                    intent.data = uri
                    startActivity((intent))
                } catch (e: ActivityNotFoundException) {
                    e.printStackTrace()
                }
            }
            .setNegativeButton("Cancel"){dialog, _->
                dialog.dismiss()
            }
        builder.create().show()
    }


    private fun isLocationEnabled():Boolean{
        val locationManager=requireContext().getSystemService(Context.LOCATION_SERVICE) as LocationManager
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)
    }
    private fun requestLocationPermission(){
        // If the user denied the permission earlier than show Rational dialog with the text
        if(ActivityCompat.shouldShowRequestPermissionRationale(
                requireActivity(),Manifest.permission.ACCESS_FINE_LOCATION)) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(requireActivity(), Manifest.permission.ACCESS_COARSE_LOCATION)) {

                showRationalDialog("Evaluation App", "To use this feature you need to allow the access to the Location")

            }
        }
        else{
            requestPermissionlocation.launch(Manifest.permission.ACCESS_COARSE_LOCATION)
        }
    }
    @SuppressLint("MissingPermission")
    private val requestPermissionlocation: ActivityResultLauncher<String> = registerForActivityResult(
        ActivityResultContracts.RequestPermission()) { isGranted ->
        if (isGranted) {
            if (!isLocationEnabled()) {
                Toast.makeText(
                    requireContext(),
                    "Your location provider is turned off. Please turn it on.",
                    Toast.LENGTH_SHORT
                ).show()
                val intent = Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
                startActivity(intent)
            } else {
                fusedLocationClient =
                    LocationServices.getFusedLocationProviderClient(requireActivity())
                fusedLocationClient.lastLocation.addOnSuccessListener { location: Location? ->
                    //binding?.lattitudeValue?.text=location?.latitude.toString()
                    //binding?.longitudeValue?.text= location?.longitude.toString()
                }.addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        val location = task.result
                        //item?.lattitude = location?.latitude.toString()
                        //item?.longitude = location?.longitude.toString()
                    }
                }

            }
        } else {
            Toast.makeText(
                requireContext(),
                "Oops, you just denied the permission.",
                Toast.LENGTH_LONG
            ).show()
        }
    }*/



}