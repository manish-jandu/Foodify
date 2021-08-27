package com.manishjandu.foodify.ui.fragments.instructions

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.manishjandu.foodify.R
import com.manishjandu.foodify.databinding.FragmentsInstructionsBinding

class InstructionsFragment : Fragment(R.layout.fragments_instructions) {

    private var _binding: FragmentsInstructionsBinding? = null
    private val binding get() = _binding!!


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentsInstructionsBinding.bind(view)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}