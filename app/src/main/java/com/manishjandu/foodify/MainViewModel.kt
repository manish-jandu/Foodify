package com.manishjandu.foodify

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.manishjandu.foodify.data.Repository
import com.manishjandu.foodify.models.FoodRecipe
import com.manishjandu.foodify.util.NetworkResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val repository: Repository,
    application: Application
) : AndroidViewModel(application) {

    private var _recipesResponse = MutableLiveData<NetworkResult<FoodRecipe>>()
    val recipesResponse: LiveData<NetworkResult<FoodRecipe>> get() = _recipesResponse


    fun getRecipes(queries: Map<String, String>) = viewModelScope.launch {
        getRecipesSafeCall(queries)
    }

    private suspend fun getRecipesSafeCall(queries: Map<String, String>) {
        _recipesResponse.value = NetworkResult.Loading()
        if (hasInternetConnection()) {
            try {
                val result = repository.remote.getRecipes(queries)
                _recipesResponse.value = handleFoodRecipeResponse(result)
            } catch (e: Exception) {
                _recipesResponse.value = NetworkResult.Error("Recipes not found.")
            }
        } else {
            _recipesResponse.postValue(NetworkResult.Error("No Internet connection."))
        }
    }

    private fun handleFoodRecipeResponse(response: Response<FoodRecipe>): NetworkResult<FoodRecipe>? {
            when{
                response.message().contains("timeout") ->{
                    return NetworkResult.Error("Timeout")
                }
                response.code() == 402 ->{
                    return NetworkResult.Error("API Key Limited.")
                }
                response.body()!!.results.isNullOrEmpty() ->{
                    return NetworkResult.Error("Recipes not found.")
                }
                response.isSuccessful ->{
                    val result = response.body()!!
                    return NetworkResult.Success(result)
                }
                else->{
                    return NetworkResult.Error(response.message())
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






