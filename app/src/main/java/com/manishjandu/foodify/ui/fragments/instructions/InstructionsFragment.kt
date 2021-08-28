package com.manishjandu.foodify.ui.fragments.instructions

import android.os.Bundle
import android.view.View
import android.webkit.WebViewClient
import androidx.fragment.app.Fragment
import com.manishjandu.foodify.R
import com.manishjandu.foodify.databinding.FragmentsInstructionsBinding
import com.manishjandu.foodify.models.Result
import com.manishjandu.foodify.util.Constants.Companion.RECIPE_RESULT_KEY

class InstructionsFragment : Fragment(R.layout.fragments_instructions) {

    private var _binding: FragmentsInstructionsBinding? = null
    private val binding get() = _binding!!


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentsInstructionsBinding.bind(view)

        val args = arguments
        val resultsBundle:Result? = args?.getParcelable(RECIPE_RESULT_KEY)

        binding.webViewInstructions.webViewClient = object : WebViewClient(){}
        binding.webViewInstructions.loadUrl(resultsBundle?.sourceUrl.toString())
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}