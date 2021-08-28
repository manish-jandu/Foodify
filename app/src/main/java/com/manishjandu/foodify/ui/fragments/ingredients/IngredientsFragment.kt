package com.manishjandu.foodify.ui.fragments.ingredients

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.manishjandu.foodify.R
import com.manishjandu.foodify.adapters.IngredientsAdapter
import com.manishjandu.foodify.databinding.FragmentIngredientsBinding
import com.manishjandu.foodify.models.Result
import com.manishjandu.foodify.util.Constants.Companion.RECIPE_RESULT_KEY

class IngredientsFragment : Fragment(R.layout.fragment_ingredients) {
    private var _binding: FragmentIngredientsBinding? = null
    private val binding get() = _binding!!
    private val ingredientsAdapter by lazy { IngredientsAdapter() }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentIngredientsBinding.bind(view)

        val args = arguments
        val resultBundle: Result? = args?.getParcelable(RECIPE_RESULT_KEY)

        binding.recyclerViewIngredients.apply {
            adapter = ingredientsAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }

        ingredientsAdapter.submitList(resultBundle?.extendedIngredients)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}