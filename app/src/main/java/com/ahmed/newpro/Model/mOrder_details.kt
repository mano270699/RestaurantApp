package com.ahmed.newpro.Model

import com.google.gson.annotations.Expose

import com.google.gson.annotations.SerializedName

class mOrder_details{

@SerializedName("order_id")
@Expose
var orderId: Int? = null

@SerializedName("meal_price")
@Expose
 val mealPrice: String? = null

@SerializedName("meal_id")
@Expose
var mealId: Int? = null

@SerializedName("meal_image")
@Expose
 val mealImage: String? = null

@SerializedName("meal_name")
@Expose
 val mealName: String? = null

@SerializedName("quantity")
@Expose
var quantity: Int=1

 @SerializedName("error")
 @Expose
 var error: Boolean? = null

 @SerializedName("msg")
 @Expose
 var msg: String? = null

@SerializedName("total_price")
@Expose
 val totalPrice: String? = null

 constructor(orderId: Int,mealId: Int) {
  this.orderId = orderId
  this.mealId = mealId
 }

 constructor(orderId: Int,mealId: Int,quantity: Int) {
  this.orderId = orderId
  this.mealId = mealId
  this.quantity = quantity
 }
}