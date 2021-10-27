package com.ahmed.newpro.Model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class mFeedback {
    @Expose
    @SerializedName("full_name")
    val full_name: String = ""
    @Expose
    @SerializedName("image")
    val image: String = ""

    @Expose
    @SerializedName("comment")
    lateinit var comment: String


    @Expose
    @SerializedName("rate")
    var rate: Int? = null
    @Expose
    @SerializedName("customer_id")
    var cutomerId: Int? = null
    @Expose
    @SerializedName("meal_id")
    var meal_id: Int? = null
    @Expose
    @SerializedName("date_time")
    var date_time: String = ""
    @Expose
    @SerializedName("com_id")
    val com_id: Int = 0

    constructor(cutomerId: Int?, meal_id: Int?,comment: String, rate: Int? ) {
        this.comment = comment
        this.rate = rate
        this.cutomerId = cutomerId
        this.meal_id = meal_id


    }
}