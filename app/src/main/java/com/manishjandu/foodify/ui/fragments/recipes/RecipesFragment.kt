package com.manishjandu.foodify.ui.fragments.recipes

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.manishjandu.foodify.R
import com.manishjandu.foodify.databinding.FragmentRecipesBinding

class RecipesFragment : Fragment(R.layout.fragment_recipes) {
    private var _binding:FragmentRecipesBinding?=null
    private val binding get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentRecipesBinding.bind(view)

        binding.recyclerView.showShimmer()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}