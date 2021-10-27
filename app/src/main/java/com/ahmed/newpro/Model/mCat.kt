package com.ahmed.newpro.Model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class mCat(

    @Expose @SerializedName("cat_id") val cat_id : Int,
    @Expose @SerializedName("cat_name") val cat_name : String,
    @Expose@SerializedName("cat_image") val cat_image : String

)

