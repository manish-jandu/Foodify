package com.manishjandu.foodify.ui.fragments.overview

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.manishjandu.foodify.R
import com.manishjandu.foodify.databinding.FragmentOverviewBinding

class OverviewFragment: Fragment(R.layout.fragment_overview) {

    private var _binding: FragmentOverviewBinding? = null
    private val binding get() = _binding!!


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentOverviewBinding.bind(view)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}