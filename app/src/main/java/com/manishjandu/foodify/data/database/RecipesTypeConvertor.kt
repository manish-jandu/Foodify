package com.manishjandu.foodify.data.database

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.manishjandu.foodify.models.FoodRecipe

class RecipesTypeConvertor {
    var gson = Gson()

    @TypeConverter
    fun foodRecipeToString(foodRecipe: FoodRecipe): String {
        return gson.toJson(foodRecipe)
    }

    @TypeConverter
    fun stringToFoodRecipe(data: String): FoodRecipe {
        val listTye = object : TypeToken<List<FoodRecipe>>() {}.type
        return gson.fromJson(data, listTye)
    }
}