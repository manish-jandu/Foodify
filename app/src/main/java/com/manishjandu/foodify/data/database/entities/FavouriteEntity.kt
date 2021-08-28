package com.manishjandu.foodify.data.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.manishjandu.foodify.models.Result
import com.manishjandu.foodify.util.Constants.Companion.FAVOURITE_RECIPE_TABLE

@Entity(tableName = FAVOURITE_RECIPE_TABLE)
class FavouriteEntity(
    @PrimaryKey(autoGenerate = true)
    val id:Int,
    var result:Result
)