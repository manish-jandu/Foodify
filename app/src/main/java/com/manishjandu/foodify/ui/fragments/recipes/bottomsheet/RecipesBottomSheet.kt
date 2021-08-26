package com.manishjandu.foodify.ui.fragments.recipes.bottomsheet

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import androidx.navigation.fragment.findNavController
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import com.manishjandu.foodify.databinding.BottomSheetRecipesBinding
import com.manishjandu.foodify.util.Constants
import com.manishjandu.foodify.viewmodels.RecipesViewModel

class RecipesBottomSheet : BottomSheetDialogFragment() {
    private lateinit var recipesViewModel: RecipesViewModel
    private var _binding: BottomSheetRecipesBinding? = null
    private val binding get() = _binding!!

    private var mealTypeChip = Constants.DEFAULT_MEAL_TYPE
    private var mealTypeChipId = 0
    private var dietType = Constants.DEFAULT_DIET_TYPE
    private var dietTypeChipId = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        recipesViewModel = ViewModelProvider(requireActivity()).get(RecipesViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = BottomSheetRecipesBinding.inflate(inflater, container, false)

        recipesViewModel.readMealAndDietType.asLiveData().observe(viewLifecycleOwner) { value ->
            mealTypeChip = value.selectedMealType
            dietType = value.selectedDietType
            updateChip(value.selectedMealTypeId, binding.chipGroupMealType)
            updateChip(value.selectedDietTypeId, binding.chipGroupDietType)
        }

        binding.chipGroupMealType.setOnCheckedChangeListener { group, checkedId ->
            val chip = group.findViewById<Chip>(checkedId)
            val selectedMealType = chip.text.toString().lowercase()
            mealTypeChip = selectedMealType
            mealTypeChipId = checkedId
        }

        binding.chipGroupDietType.setOnCheckedChangeListener { group, checkedId ->
            val chip = group.findViewById<Chip>(checkedId)
            val selectedDietType = chip.text.toString().lowercase()
            dietType = selectedDietType
            dietTypeChipId = checkedId
        }

        binding.buttonApply.setOnClickListener {
            recipesViewModel.saveMealAndDietType(
                mealTypeChip,
                mealTypeChipId,
                dietType,
                dietTypeChipId
            )
            val action =
                RecipesBottomSheetDirections.actionRecipesBottomSheetToRecipesFragment(true)
            findNavController().navigate(action)
        }

        return binding.root
    }

    private fun updateChip(chipId: Int, chipGroupMealType: ChipGroup) {
        if (chipId != 0) {
            try {
                chipGroupMealType.findViewById<Chip>(chipId).isChecked = true
            } catch (e: Exception) {
                Log.d("RecipesBottomSheet", e.message.toString())
            }
        }
    }

}