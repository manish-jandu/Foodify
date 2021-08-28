package com.manishjandu.foodify.ui

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.navArgs
import com.google.android.material.tabs.TabLayoutMediator
import com.manishjandu.foodify.R
import com.manishjandu.foodify.adapters.PagerAdapter
import com.manishjandu.foodify.databinding.ActivityDetailsBinding
import com.manishjandu.foodify.ui.fragments.ingredients.IngredientsFragment
import com.manishjandu.foodify.ui.fragments.instructions.InstructionsFragment
import com.manishjandu.foodify.ui.fragments.overview.OverviewFragment
import com.manishjandu.foodify.util.Constants.Companion.RECIPE_RESULT_KEY

class DetailsActivity : AppCompatActivity() {
    private var _binding: ActivityDetailsBinding? = null
    private val binding get() = _binding!!

    private val args by navArgs<DetailsActivityArgs>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)
        binding.toolbar.setTitleTextColor(ContextCompat.getColor(this, R.color.white))
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        binding.viewPager.adapter = getPagerAdapter()

        val titles = arrayListOf<String>("Overview", "Ingredients", "Instructions")
        TabLayoutMediator(binding.tabsLayout, binding.viewPager) { tab, position ->
            tab.text = titles[position]
        }.attach()
    }

    private fun getPagerAdapter(): PagerAdapter {
        val fragments =
            arrayListOf<Fragment>(OverviewFragment(), IngredientsFragment(), InstructionsFragment())


        val resultsBundle = Bundle()
        resultsBundle.putParcelable(RECIPE_RESULT_KEY, args.result)

        return PagerAdapter(
            resultsBundle,
            fragments,
            this
        )
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            finish()
        }
        return super.onOptionsItemSelected(item)
    }
}