package com.manishjandu.foodify.ui.fragments.recipes.bottomsheet

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.manishjandu.foodify.databinding.BottomSheetRecipesBinding

class RecipesBottomSheet : BottomSheetDialogFragment() {
    private var _binding: BottomSheetRecipesBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = BottomSheetRecipesBinding.inflate(inflater, container, false)

        return binding.root
    }

}