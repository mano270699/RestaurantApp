package com.ahmed.newpro.Model

import java.io.Serializable

data class  order_data(val id: Int,val date: String,val time: String,val price: String,   val status: String,val num_item:Int,val cash_methods:String,val Delivery_methods:String): Serializable