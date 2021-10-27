package com.ahmed.newpro.Model

import com.google.gson.annotations.Expose

import com.google.gson.annotations.SerializedName


class mOrder {


    @SerializedName("full_name")
    @Expose
    var fullName: String? = null

    @SerializedName("image")
    @Expose
    var image: String? = null

    @SerializedName("address")
    @Expose
    var address: String? = null

    @SerializedName("phone")
    @Expose
    var phone: String? = null

    @SerializedName("birth_date")
    @Expose
    var birthDate: String? = null

    @SerializedName("order_id")
    @Expose
    var orderId: Int? = null

    @SerializedName("status")
    @Expose
    var status: String? = null

    @SerializedName("date")
    @Expose
    var date: String? = null

    @SerializedName("time")
    @Expose
    var time: String? = null

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

    @SerializedName("Num_items")
    @Expose
    var Num_items: Int = 0

    @SerializedName("error")
    @Expose
    var error: Boolean? = null

    @SerializedName("msg")
    @Expose
    var msg: String? = null

    constructor(id: Int?,date: String?,time:String?,price: String?,status: String?,num_item: Int,paymentMethod: String?, deliveryMethod: String?) {
        this.orderId = id
        this.date = date
        this.time = time
        this.totalPrice = price
        this.status = status
        this.Num_items = num_item
        this.paymentMethod = paymentMethod
        this.deliveryMethod = deliveryMethod
    }

    constructor(paymentMethod: String?, deliveryMethod: String?) {
        this.paymentMethod = paymentMethod
        this.deliveryMethod = deliveryMethod
    }

}