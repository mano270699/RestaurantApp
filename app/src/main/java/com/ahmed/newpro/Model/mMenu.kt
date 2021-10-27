package com.ahmed.newpro.Model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class mMenu(
  
    @Expose @SerializedName("meal_id") val meal_id: Int,
    @Expose @SerializedName("meal_name") val meal_name: String,
    @Expose @SerializedName("meal_image") val meal_image: String,
    @Expose @SerializedName("cat_id") val cat_id: Int,
    @Expose @SerializedName("meal_price") val meal_price: Double,
    @Expose @SerializedName("old_price") val old_price: Double,
    @Expose @SerializedName("description") val description: String,
    @Expose @SerializedName("image") val image: String,
    @Expose @SerializedName("slide_id") val slide_id: Int



)

