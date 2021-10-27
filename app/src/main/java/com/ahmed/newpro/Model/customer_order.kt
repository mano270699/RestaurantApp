package com.ahmed.newpro.Model

import com.google.gson.annotations.Expose

import com.google.gson.annotations.SerializedName




class customer_order{

    @SerializedName("order_id")
    @Expose
     var orderId: Int? = null

    @SerializedName("status")
    @Expose
     var status: String? = null

    @SerializedName("date_time")
    @Expose
     var dateTime: String? = null

    @SerializedName("total_price")
    @Expose
     var totalPrice: String? = null

    @SerializedName("payment_method")
    @Expose
     var paymentMethod: String? = null

    @SerializedName("delivery_method")
    @Expose
     var deliveryMethod: String? = null

    @SerializedName("cutomer_id")
    @Expose
     var cutomerId: Int? = null

    @SerializedName("isOrdered")
    @Expose
     var isOrdered: Int = 0



}