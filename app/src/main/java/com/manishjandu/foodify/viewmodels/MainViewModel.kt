package com.manishjandu.foodify.viewmodels

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import androidx.lifecycle.*
import com.manishjandu.foodify.data.Repository
import com.manishjandu.foodify.data.database.entities.FavouriteEntity
import com.manishjandu.foodify.data.database.entities.FoodJokeEntity
import com.manishjandu.foodify.data.database.entities.RecipesEntity
import com.manishjandu.foodify.models.FoodJoke
import com.manishjandu.foodify.models.FoodRecipe
import com.manishjandu.foodify.util.NetworkResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val repository: Repository,
    application: Application
) : AndroidViewModel(application) {

    //** ROOM DATABASE */
    val readRecipes: LiveData<List<RecipesEntity>> = repository.local.readRecipes().asLiveData()

    val readFavouriteRecipes: LiveData<List<FavouriteEntity>> =
        repository.local.readFavouriteRecipes().asLiveData()

    val readFoodJoke: LiveData<List<FoodJokeEntity>> = repository.local.readFoodJoke().asLiveData()

    private fun insertRecipes(recipesEntity: RecipesEntity) =
        viewModelScope.launch(Dispatchers.IO) {
            repository.local.insertRecipes(recipesEntity)
        }

    fun insertFavouriteRecipe(favouriteEntity: FavouriteEntity) =
        viewModelScope.launch(Dispatchers.IO) {
            repository.local.insertFavouriteRecipe(favouriteEntity)
        }

    fun insertFoodJoke(foodJokeEntity: FoodJokeEntity) =
        viewModelScope.launch(Dispatchers.IO) {
            repository.local.insertFoodJoke(foodJokeEntity)
        }

    fun deleteFavouriteRecipe(favouriteEntity: FavouriteEntity) =
        viewModelScope.launch(Dispatchers.IO) {
            repository.local.deleteFavouriteRecipe(favouriteEntity)
        }

    fun deleteAllFavouriteRecipes() = viewModelScope.launch(Dispatchers.IO) {
        repository.local.deleteAllFavouriteRecipes()
    }

    //** RETROFIT */
    private var _recipesResponse = MutableLiveData<NetworkResult<FoodRecipe>>()
    val recipesResponse: LiveData<NetworkResult<FoodRecipe>> get() = _recipesResponse

    private var _searchRecipesResponse = MutableLiveData<NetworkResult<FoodRecipe>>()
    val searchRecipesResponse: LiveData<NetworkResult<FoodRecipe>> get() = _searchRecipesResponse

    private var _foodJokeResponse = MutableLiveData<NetworkResult<FoodJoke>>()
    val foodJokeResponse: LiveData<NetworkResult<FoodJoke>> get() = _foodJokeResponse

    fun getRecipes(queries: Map<String, String>) = viewModelScope.launch {
        getRecipesSafeCall(queries)
    }

    fun searchRecipes(searchQuery: Map<String, String>) = viewModelScope.launch {
        getSearchRecipeSafeCall(searchQuery)
    }

    fun getFoodJoke(apiKey: String) = viewModelScope.launch {
        getFoodJokeSafeCall(apiKey)
    }

    private suspend fun getRecipesSafeCall(queries: Map<String, String>) {
        _recipesResponse.value = NetworkResult.Loading()
        if (hasInternetConnection()) {
            try {
                val result = repository.remote.getRecipes(queries)
                _recipesResponse.value = handleFoodRecipeResponse(result)

                val foodRecipes = _recipesResponse.value!!.data
                if (foodRecipes != null) {
                    offlineCacheRecipes(foodRecipes)
                }
            } catch (e: Exception) {
                _recipesResponse.value = NetworkResult.Error("Recipes not found.")
            }
        } else {
            _recipesResponse.postValue(NetworkResult.Error("No Internet connection."))
        }
    }

    private suspend fun getSearchRecipeSafeCall(searchQuery: Map<String, String>) {
        _searchRecipesResponse.value = NetworkResult.Loading()
        if (hasInternetConnection()) {
            try {
                val result = repository.remote.searchRecipes(searchQuery)
                _searchRecipesResponse.value = handleFoodRecipeResponse(result)
            } catch (e: Exception) {
                _searchRecipesResponse.value = NetworkResult.Error("Recipes not found.")
            }
        } else {
            _searchRecipesResponse.postValue(NetworkResult.Error("No Internet connection."))
        }
    }

    private suspend fun getFoodJokeSafeCall(apiKey: String) {
        _foodJokeResponse.value = NetworkResult.Loading()
        if (hasInternetConnection()) {
            try {
                val result = repository.remote.getFoodJoke(apiKey)
                _foodJokeResponse.value = handleFoodJokeResponse(result)

                val data = _foodJokeResponse.value!!.data
                if(data !=null){
                    offlineCacheFoodJoke(data)
                }
            } catch (e: Exception) {
                _foodJokeResponse.value = NetworkResult.Error(e.message.toString())
            }
        } else {
            _foodJokeResponse.value = NetworkResult.Error("No Internet connection.")
        }
    }

    private fun offlineCacheRecipes(foodRecipes: FoodRecipe) {
        val recipesEntity = RecipesEntity(foodRecipes)
        insertRecipes(recipesEntity)
    }

    private fun offlineCacheFoodJoke(foodJoke: FoodJoke) {
        val foodJokeEntity = FoodJokeEntity(foodJoke)
        insertFoodJoke(foodJokeEntity)
    }

    private fun handleFoodRecipeResponse(response: Response<FoodRecipe>): NetworkResult<FoodRecipe>? {
        when {
            response.message().contains("timeout") -> {
                return NetworkResult.Error("Timeout")
            }
            response.code() == 402 -> {
                return NetworkResult.Error("API Key Limited.")
            }
            response.body()!!.results.isNullOrEmpty() -> {
                return NetworkResult.Error("Recipes not found.")
            }
            response.isSuccessful -> {
                val result = response.body()!!
                return NetworkResult.Success(result)
            }
            else -> {
                return NetworkResult.Error(response.message())
            }
        }
    }

    private fun handleFoodJokeResponse(response: Response<FoodJoke>): NetworkResult<FoodJoke>? {
        return when {
            response.message().contains("timeout") -> {
                NetworkResult.Error("Timeout")
            }
            response.code() == 402 -> {
                NetworkResult.Error("API Key Limited.")
            }
            response.isSuccessful -> {
                val foodJoke = response.body()!!
                NetworkResult.Success(foodJoke)
            }
            else -> {
                NetworkResult.Error(response.message())
            }
        }
    }

    private fun hasInternetConnection(): Boolean {
        val connectivityManager = getApplication<Application>().getSystemService(
            Context.CONNECTIVITY_SERVICE
        ) as ConnectivityManager
        val activeNetwork = connectivityManager.activeNetwork ?: return false
        val capabilities = connectivityManager.getNetworkCapabilities(activeNetwork) ?: return false

        return when {
            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
            else -> false
        }
    }


}






