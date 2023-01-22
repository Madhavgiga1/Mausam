package com.example.mausam.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mausam.R
import com.example.mausam.adapters.Forecastadapter
import com.example.mausam.databinding.FragmentForecastBinding
import com.example.mausam.util.Constants
import com.example.mausam.util.NetworkResult
import com.example.mausam.viewmodels.MainViewModel
import com.example.mausam.viewmodels.ForecastViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
@AndroidEntryPoint
class ForecastFragment : Fragment() {
    private  var _binding:FragmentForecastBinding?=null
    private val binding get()=_binding!!
    private lateinit var forecastViewModel: ForecastViewModel
    private val mAdapter by lazy { Forecastadapter() }
    private var mLatitude: Double = 0.0 // A variable which will hold the latitude value.
    private var mLongitude: Double = 0.0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        forecastViewModel=ViewModelProvider(requireActivity()).get(ForecastViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding=FragmentForecastBinding.inflate(layoutInflater,container,false)
        val view=binding.root
        setupRecyclerView()
        requestApiDataforecast()

        return view


    }

    private fun setupRecyclerView() {
        binding.forecastRecyclerview.adapter=mAdapter
        binding.forecastRecyclerview.layoutManager=LinearLayoutManager(requireActivity())
    }

    private fun requestApiDataforecast() {
        forecastViewModel.getForecast(applyQueries())
        forecastViewModel.forecastResponse.observe(viewLifecycleOwner,{response ->
            when(response){
                is NetworkResult.Success -> {

                    response.data?.let {
                        mAdapter.setData(it.forecast)
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
        queries["days"]="5"
        queries["alerts"]="no"

        return queries
    }


}