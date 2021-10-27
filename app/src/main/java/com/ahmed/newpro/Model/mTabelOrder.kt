package com.ahmed.newpro.Model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class mTabelOrder {


    @Expose
    @SerializedName("customer_id")
    var customer_id: Int = 0

    @SerializedName("table_id")
    @Expose
    var table_id: Int = 0

    @SerializedName("num_of_people")
    @Expose
    var num_of_people: Int = 0

    @SerializedName("date")
    @Expose
    var date: String? = null


    @SerializedName("time")
    @Expose
    var time: String? = null


    @SerializedName("note")
    @Expose
    var note: String? = null

    @SerializedName("order_id")
    @Expose
    var order_id: Int = 0

    constructor(
        customer_id: Int,
        table_id: Int,
        num_of_people: Int,
        date: String?,
        time: String?,
        note: String?,
        order_id: Int
    ) {
        this.customer_id = customer_id
        this.table_id = table_id
        this.num_of_people = num_of_people
        this.date = date
        this.time = time
        this.note = note
        this.order_id = order_id
    }

    constructor(date: String?) {
        this.date = date
    }


}