package com.manishjandu.foodify.ui.fragments.ingredients

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.manishjandu.foodify.R
import com.manishjandu.foodify.databinding.FragmentIngredientsBinding

class IngredientsFragment : Fragment(R.layout.fragment_ingredients) {
    private var _binding :FragmentIngredientsBinding?=null
    private val binding get() = _binding!!


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentIngredientsBinding.bind(view)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}