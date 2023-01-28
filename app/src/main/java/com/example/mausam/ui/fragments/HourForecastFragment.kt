package com.example.mausam.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import androidx.navigation.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mausam.R
import com.example.mausam.adapters.HourForecastAdapter
import com.example.mausam.databinding.FragmentHourForecastBinding


class HourForecastFragment : Fragment() {
    private val args by navArgs<HourForecastFragmentArgs>()
    private var _binding:FragmentHourForecastBinding?=null
    private val binding get()=_binding!!
    private val mAdapter by lazy {HourForecastAdapter()}

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        _binding= FragmentHourForecastBinding.inflate(layoutInflater,container,false)
        mAdapter.hourer=args.result.hour
        setupRecyclerView()


        return binding.root
    }

    private fun setupRecyclerView() {
        binding.hourrecycylerView.adapter=mAdapter
        binding.hourrecycylerView.layoutManager=LinearLayoutManager(requireContext())
    }


}