package com.manishjandu.foodify.ui

import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.navArgs
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.tabs.TabLayoutMediator
import com.manishjandu.foodify.R
import com.manishjandu.foodify.adapters.PagerAdapter
import com.manishjandu.foodify.data.database.entities.FavouriteEntity
import com.manishjandu.foodify.databinding.ActivityDetailsBinding
import com.manishjandu.foodify.ui.fragments.ingredients.IngredientsFragment
import com.manishjandu.foodify.ui.fragments.instructions.InstructionsFragment
import com.manishjandu.foodify.ui.fragments.overview.OverviewFragment
import com.manishjandu.foodify.util.Constants.Companion.RECIPE_RESULT_KEY
import com.manishjandu.foodify.viewmodels.MainViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailsActivity : AppCompatActivity() {
    private var _binding: ActivityDetailsBinding? = null
    private val binding get() = _binding!!

    private val mainViewModel: MainViewModel by viewModels()
    private val args by navArgs<DetailsActivityArgs>()
    private var recipeSaved = false
    private var savedRecipeId = 0

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

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.details_menu, menu)
        val menuItem = menu?.findItem(R.id.save_to_favourites_menu)
        changeMenuItemColor(menuItem!!, R.color.white)
        checkSavedRecipe(menuItem)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            finish()
        } else if (item.itemId == R.id.save_to_favourites_menu && !recipeSaved) {
            saveToFavourites(item)
        }else if(item.itemId == R.id.save_to_favourites_menu && recipeSaved){
            removeFromFavourites(item)
        }
        return super.onOptionsItemSelected(item)
    }

    private fun checkSavedRecipe(menuItem: MenuItem) {
        mainViewModel.readFavouriteRecipes.observe(this) { favouritesEntity ->
            try {
                for (savedRecipe in favouritesEntity) {
                    if (savedRecipe.result.recipeId == args.result.recipeId) {
                        changeMenuItemColor(menuItem, R.color.yellow)
                        savedRecipeId = savedRecipe.id
                        recipeSaved = true
                    }
                }
            } catch (e: Exception) {
                Log.d("DetailsActivity", e.message.toString())
            }
        }
    }

    private fun saveToFavourites(item: MenuItem) {
        val favouriteEntity = FavouriteEntity(0, args.result)
        mainViewModel.insertFavouriteRecipe(favouriteEntity)
        changeMenuItemColor(item, R.color.yellow)
        showSnackBar("Recipe Saved.")
        recipeSaved = true
    }

    private fun removeFromFavourites(menuItem: MenuItem) {
        val favouriteEntity = FavouriteEntity(savedRecipeId, args.result)
        mainViewModel.deleteFavouriteRecipe(favouriteEntity)
        changeMenuItemColor(menuItem, R.color.white)
        showSnackBar("Recipe removed from Favourites.")
        recipeSaved = false
    }

    private fun showSnackBar(message: String) {
        Snackbar.make(binding.detailsLayout, message, Snackbar.LENGTH_SHORT).setAction("Okay") {}
            .show()
    }

    private fun changeMenuItemColor(menuItem: MenuItem, color: Int) {
        menuItem.icon.setTint(ContextCompat.getColor(this, color))
    }
}