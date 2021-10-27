package com.ahmed.newpro.Model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.io.File

class mUser {
    @SerializedName("customer_id")
    @Expose
    var customerId: Int? = null

    @SerializedName("order_id")
    @Expose
    var order_id: Int? = null

    @SerializedName("email")
    @Expose
     var email: String=""

    @SerializedName("full_name")
    @Expose
    lateinit var fullName: String

    @SerializedName("password")
    @Expose
    lateinit var password: String

    @SerializedName("address")
    @Expose
     var address: String=""

    @SerializedName("birth_date")
    @Expose
     var birthDate: String=""

    @SerializedName("phone")
    @Expose
     var phone: String=""

    @SerializedName("cridet_card")
    @Expose
    var cridetCard: Any? = null

    @SerializedName("image")
    @Expose
     var image: String=""

    @SerializedName("Blocked")
    @Expose
    var blocked: Int? = null

    @SerializedName("error")
    @Expose
    var error: Boolean? = null

    @SerializedName("msg")
    @Expose
    var msg: String? = null

    @SerializedName("token")
    @Expose
    lateinit var token: String

    constructor(
        email: String,
        fullName: String,
        password: String,
        phone: String
    ) {
        this.email = email
        this.fullName = fullName
        this.password = password
        this.phone = phone
    }


    constructor(
        email: String,
        fullName: String,

        phone: String
    ) {
        this.email = email
        this.fullName = fullName

        this.phone = phone
    }



    constructor(token: String) {
        this.token = token

    }



    constructor(
        customerId: Int?,
        email: String,
        fullName: String,
        password: String,
        address: String,
        birthDate: String,
        phone: String,
        cridetCard: String,
        image: String,
        blocked: Int?
    ) {
        this.customerId = customerId
        this.email = email
        this.fullName = fullName
        this.password = password
        this.address = address
        this.birthDate = birthDate
        this.phone = phone
        this.cridetCard = cridetCard
        this.image = image
        this.blocked = blocked
    }
    constructor(
        email: String,
        password: String
    ) {
        this.email = email
        this.password = password
    }

    constructor(customerId: Int?) {
        this.customerId = customerId
    }

   constructor(customerId: Int?,token:String){
       this.customerId=customerId
       this.token=token
   }



}