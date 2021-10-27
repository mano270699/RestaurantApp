package com.ahmed.newpro.Model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName


class mSaved {
    @SerializedName("meal_name")
    @Expose
     var mealName: String? = null

    @SerializedName("meal_image")
    @Expose
     var mealImage: String? = null

    @SerializedName("meal_price")
    @Expose
     var mealPrice: String? = null

    @SerializedName("description")
    @Expose
     var description: String? = null

    @SerializedName("id")
    @Expose
     var id: Int = 0

    @SerializedName("customer_id")
    @Expose
     var customerId: Int? = null

    @SerializedName("meal_id")
    @Expose
     var mealId: Int? = null

    constructor(customerId: Int?, mealId: Int?) {
        this.customerId = customerId
        this.mealId = mealId
    }




}