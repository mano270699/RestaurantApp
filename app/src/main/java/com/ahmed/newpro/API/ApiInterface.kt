package com.ahmed.newpro.API;

import com.ahmed.newpro.Model.*
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.*


interface ApiInterface {

    @GET("allcategory")
    fun getCategorys(): Call<List<mCat>>

    @GET("allMealin_Category/{catID}")
    fun getMeals(@Path("catID") catID: Int): Call<List<mMenu>>

    @GET("allCustomerComments/{mealID}")
    fun getMealsFeed(@Path("mealID") mealID: Int): Call<List<mFeedback>>

    @GET("alloffers")
    fun getOffer(): Call<List<mMenu>>

    @POST("addnewCustomer")
    fun singUp(@Body user: mUser): Call<mUser>

    @POST("customerLogin")
    fun singIn(@Body user: mUser): Call<mUser>

    @GET("finduserbyID/{userID}")
    fun getUserById(@Path("userID") userID: Int): Call<mUser>

    @POST("lostUserPassword")
    fun lostPassword(@Body user: mUser): Call<mUser>

    @GET("allCustomerSavedMeal/{userID}")
    fun getSaved(@Path("userID") userID: Int): Call<List<mSaved>>

    @DELETE("deleteCustomerSavedMeal/{customer_id}/meal/{meal_id}")
    fun unSaved(@Path("customer_id") customer_id: Int, @Path("meal_id") meal_id: Int): Call<mSaved>

    @POST("addCustomerSavedMeal")
    fun SavedMeal(@Body save: mSaved): Call<mSaved>

    @GET("orderforspacialcustomer/{userID}")
    fun getOrders(@Path("userID") userID: Int): Call<List<mOrder>>

    @GET("orderInOrderInfo/{orderID}")
    fun getMealsOrders(@Path("orderID") orderID: Int): Call<List<mOrder_details>>

    @GET("getCurrentOrderID/{userID}")
    fun getCurrentOrderID(@Path("userID") userID: Int): Call<customer_order>

    @POST("addNewMealOrder")
    fun addNewMealOrder(@Body newMeal: mOrder_details): Call<mOrder_details>

    @POST("addNewOrder")
    fun addNewOrder(@Body user: mUser): Call<mUser>

    @HTTP(method = "DELETE", path = "deleteMealCart", hasBody = true)
    fun deleteMealCart(@Body newMeal: mOrder_details): Call<mOrder_details>

    @PUT("addMealQty")
    fun addMealQty(@Body newMeal: mOrder_details): Call<mOrder_details>

    @PUT("updateAddressCustomer/{userID}")
   fun updateAddressCustomer(@Path("userID") userID: Int, @Body user: mUser): Call<mUser>

    @PUT("updateUserData/{userID}")
   fun updateUserData(@Path("userID") userID: Int, @Body user: mUser): Call<mUser>

    //tabel
    @POST("addOrderToTabel")
    fun addOrderToTabel(@Body mytabel: mTabelOrder): Call<mTabelOrder>

    @GET("getAvalibleTable/{date}")
    fun getAvalibleTable(@Path("date") date: String): Call<List<mTabelOrder>>

    //get totlal price for order in cart
    @GET("TotalPriceorderforspacialcustomer/{userID}/order/{orderID}")
    fun getTotalPriceorder(@Path("userID") userID: Int, @Path("orderID") orderID: Int): Call<mOrder>

    @PUT("updateisOrder/{orderID}")
    fun updateisOrder(@Path("orderID") orderID: Int, @Body order: mOrder): Call<mOrder>

    @GET("getMostPopularMeal")
    fun getMostPopularMeal(): Call<List<mMenu>>
    @GET("allSlider")
    fun getSliderimage(): Call<List<mSlider>>

    @POST("addCustomerFeedbackMeal")
    fun addfeedback(@Body myfeedback: mFeedback): Call<mFeedback>

    @PUT("userToken/{userID}")
    fun updateUsertoken(@Path("userID") userID: Int, @Body user: mUser): Call<mUser>





}