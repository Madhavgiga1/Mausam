package com.example.mausam.ui.fragments

import NetworkListener
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.example.mausam.databinding.FragmentCurrentLocationBinding
import com.example.mausam.util.Constants
import com.example.mausam.util.NetworkResult
import com.example.mausam.viewmodels.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
@AndroidEntryPoint
class CurrentLocationFragment : Fragment() {
    private lateinit var networkListener: NetworkListener
    private lateinit var mainViewModel: MainViewModel

    private var _binding:FragmentCurrentLocationBinding?=null
    private val binding get()=_binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainViewModel = ViewModelProvider(requireActivity()).get(MainViewModel::class.java)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = FragmentCurrentLocationBinding.inflate(inflater, container, false)
        requestApiData()
        _binding=view//important change
        return view.root
    }

    private fun requestApiData() {
        mainViewModel.getWeather(applyQueries())
        mainViewModel.weatherResponse.observe(viewLifecycleOwner, { response ->
            when(response){
                is NetworkResult.Success -> {

                    response.data?.let {
                        binding.humidityValue.text=it.current.humidity.toString()
                        binding.pressureValue.text=it.current.pressureMb.toString()
                        binding.TempearutreValue.text=it.current.tempC.toString()
                        binding.precipiationValue.text=it.current.precipMm.toString()

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
    fun applyQueries(): HashMap<String, String> {
        val queries: HashMap<String, String> = HashMap()
        queries["key"] = "e260af43c718433ca69162847230801"
        queries["q"] = "Delhi, India"

        return queries
    }



}