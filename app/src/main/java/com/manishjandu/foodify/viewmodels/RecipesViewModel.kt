package com.manishjandu.foodify.viewmodels

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.manishjandu.foodify.data.DatastoreRepository
import com.manishjandu.foodify.util.Constants
import com.manishjandu.foodify.util.Constants.Companion.DEFAULT_DIET_TYPE
import com.manishjandu.foodify.util.Constants.Companion.DEFAULT_MEAL_TYPE
import com.manishjandu.foodify.util.Constants.Companion.DEFAULT_RECIPES_NUMBER
import com.manishjandu.foodify.util.Constants.Companion.QUERY_ADD_RECIPE_INFORMATION
import com.manishjandu.foodify.util.Constants.Companion.QUERY_API_KEY
import com.manishjandu.foodify.util.Constants.Companion.QUERY_DIET
import com.manishjandu.foodify.util.Constants.Companion.QUERY_FILL_INGREDIENTS
import com.manishjandu.foodify.util.Constants.Companion.QUERY_NUMBER
import com.manishjandu.foodify.util.Constants.Companion.QUERY_SEARCH
import com.manishjandu.foodify.util.Constants.Companion.QUERY_TYPE
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RecipesViewModel @Inject constructor(
    application: Application,
    private val datastoreRepository: DatastoreRepository
) : AndroidViewModel(application) {

    private var mealType = DEFAULT_MEAL_TYPE
    private var dietType = DEFAULT_DIET_TYPE

    var networkStatus = false
    var backOnline = false

    val readMealAndDietType = datastoreRepository.readMealAndDietType
    val readBackOnline = datastoreRepository.readBackOnline.asLiveData()

    fun saveMealAndDietType(
        mealType: String,
        mealTypeId: Int,
        dietType: String,
        dietTypeId: Int
    ) = viewModelScope.launch(Dispatchers.IO) {
        datastoreRepository.saveMealAndDietType(mealType, mealTypeId, dietType, dietTypeId)
    }

    fun saveBackOnline(backOnline: Boolean) = viewModelScope.launch(Dispatchers.IO) {
        datastoreRepository.saveBackOnline(backOnline)
    }

    fun applyQueries(): HashMap<String, String> {
        viewModelScope.launch {
            readMealAndDietType.collect { value ->
                mealType = value.selectedMealType
                dietType = value.selectedDietType
            }
        }

        val queries = HashMap<String, String>()
        queries[QUERY_API_KEY] = Constants.API_KEY
        queries[QUERY_NUMBER] = DEFAULT_RECIPES_NUMBER
        queries[QUERY_TYPE] = mealType
        queries[QUERY_DIET] = dietType
        queries[QUERY_ADD_RECIPE_INFORMATION] = "true"
        queries[QUERY_FILL_INGREDIENTS] = "true"
        return queries
    }

    fun applySearchQuery(searchQuery:String): HashMap<String, String> {
        val queries = HashMap<String, String>()
        queries[QUERY_SEARCH] = searchQuery
        queries[QUERY_NUMBER] = DEFAULT_RECIPES_NUMBER
        queries[QUERY_API_KEY] = Constants.API_KEY
        queries[QUERY_ADD_RECIPE_INFORMATION] = "true"
        queries[QUERY_FILL_INGREDIENTS] = "true"
        return queries
    }

    fun showNetworkStatus() {
        if (!networkStatus) {
            Toast.makeText(getApplication(), "No Internet Connection", Toast.LENGTH_SHORT).show()
            saveBackOnline(true)
        } else if (networkStatus) {
            if (backOnline) {
                Toast.makeText(getApplication(), "We are back Online.", Toast.LENGTH_SHORT).show()
                saveBackOnline(false)
            }
        }
    }

}