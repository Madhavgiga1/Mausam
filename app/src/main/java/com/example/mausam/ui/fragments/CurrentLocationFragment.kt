package com.example.mausam.ui.fragments

import NetworkListener
import android.Manifest
import android.annotation.SuppressLint
import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Geocoder
import android.location.Location
import android.location.LocationManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Looper
import android.provider.Settings
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.SearchView
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.mausam.R
import com.example.mausam.databinding.FragmentCurrentLocationBinding
import com.example.mausam.util.Constants.Companion.API_KEY
import com.example.mausam.util.NetworkResult
import com.example.mausam.viewmodels.MainViewModel
import com.google.android.gms.location.*
import com.google.android.libraries.places.api.Places

import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi

import java.util.*
import kotlin.collections.HashMap


@ExperimentalCoroutinesApi
@AndroidEntryPoint
class CurrentLocationFragment : Fragment(),SearchView.OnQueryTextListener  {
    private lateinit var networkListener: NetworkListener
    private lateinit var mainViewModel: MainViewModel

    private var _binding:FragmentCurrentLocationBinding?=null
    private val binding get()=_binding!!
    private var mLatitude: Double = 0.0 // A variable which will hold the latitude value.
    private var mLongitude: Double = 0.0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainViewModel = ViewModelProvider(requireActivity()).get(MainViewModel::class.java)



    }
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        if (!Places.isInitialized()) {
            Places.initialize(requireContext(), resources.getString(R.string.google_maps_api_key))
        }
        val view = FragmentCurrentLocationBinding.inflate(inflater, container, false)
        requestApiData()
        _binding=view//important change

        val menuHost: MenuHost = requireActivity()
        menuHost.addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menuInflater.inflate(R.menu.title_menu, menu)

                val search = menu.findItem(R.id.search)
                val searchView = search.actionView as? SearchView
                searchView?.isSubmitButtonEnabled = true
                searchView?.setOnQueryTextListener(this@CurrentLocationFragment)
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                return true
            }
        }, viewLifecycleOwner, Lifecycle.State.RESUMED)
        return view.root

    }

    private fun applyQueries(): HashMap<String, String> {
            val queries: HashMap<String, String> = HashMap()
            queries["key"] = API_KEY
            queries["q"] = "Jaipur India"
            queries["aqi"]="yes"

            return queries
    }
    private fun requestApiData() {
        mainViewModel.getWeather(applyQueries())
        mainViewModel.weatherResponse.observe(viewLifecycleOwner, { response ->
            when(response){
                is NetworkResult.Success -> {

                    response.data?.let {
                        binding.humidityValue.text=it.current.humidity.toString()+" %"
                        binding.pressureValue.text=it.current.pressureMb.toString()+" Mb"
                        binding.tempValue.text=it.current.tempC.toString()+" C"
                        binding.feelsLikeValue.text=it.current.feelslikeC.toString()+" C"
                        binding.precipiationValue.text=it.current.precipMm.toString()+" Mm"
                        binding.aqiValue.text=it.current.airQuality.usEpaIndex.toString()+" us-epa"
                        binding.windspeedValue.text=it.current.windKph.toString()+" kph"
                        binding.coValue.text=it.current.airQuality.co.toString()
                        binding.no2Value.text=it.current.airQuality.no2.toString()
                        binding.so2Value.text=it.current.airQuality.so2.toString()
                        binding.o3Value.text=it.current.airQuality.o3.toString()
                        binding.uvValue.text=it.current.uv.toString()
                        if(it.current.airQuality.usEpaIndex in 1..2){
                            binding.airQualityImage22.setImageResource(R.drawable.plant)
                            binding.aqiDesc.text="Awesome air Outthere"
                        }
                        else if(it.current.airQuality.usEpaIndex in 3..4){
                            binding.airQualityImage22.setImageResource(R.drawable.uncomfortable)
                            binding.aqiDesc.text="Awesome air Outthere"
                        }
                        else if(it.current.airQuality.usEpaIndex in 5..6){
                            binding.airQualityImage22.setImageResource(R.drawable.sorethroat)
                            binding.aqiDesc.text="Ohh god shits real bad"
                        }

                    }
                }
                is NetworkResult.Error -> {

                    Toast.makeText(
                        requireContext(),
                        response.message.toString(),
                        Toast.LENGTH_SHORT
                    ).show()
                }

            }
        })
    }


    override fun onQueryTextSubmit(query: String?): Boolean {
        /*if (query != null) {
            searchApiData(query)
        }*/
        return true
    }

    override fun onQueryTextChange(p0: String?): Boolean {
        return true
    }
}
/* <SearchView
        app:layout_constraintBottom_toTopOf="@+id/Location"
        app:layout_constraintEnd_toEndOf="parent"
        app:layout_constraintStart_toStartOf="parent"
        android:id="@+id/SearchLocation"
        app:layout_constraintTop_toTopOf="parent"
        android:layout_width="match_parent"
        android:layout_height="wrap_content">
    </SearchView>*/


