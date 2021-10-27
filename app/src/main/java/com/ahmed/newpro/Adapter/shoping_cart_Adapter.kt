package com.ahmed.newpro.Adapter

import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.ahmed.newpro.API.ApiClient
import com.ahmed.newpro.Model.mMenu
import com.ahmed.newpro.Model.mOrder_details
import com.ahmed.newpro.Model.mSaved
import com.ahmed.newpro.Model.mUser
import com.ahmed.newpro.R
import com.ahmed.newpro.shaping_cart
import com.pranavpandey.android.dynamic.toasts.DynamicToast
import com.squareup.picasso.Picasso
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class shoping_cart_Adapter(
    val MenuList: ArrayList<mOrder_details>,
    val context: Context,
    val shapingCart: shaping_cart
) : RecyclerView.Adapter<shoping_cart_Adapter.ViewHolder>() {

    //this method is returning the view for each item in the list
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): shoping_cart_Adapter.ViewHolder {
        val v =
            LayoutInflater.from(parent.context).inflate(R.layout.rec_cart_shoping, parent, false)
        return ViewHolder(v)
    }

    //this method is binding the data on the list
    override fun onBindViewHolder(holder: shoping_cart_Adapter.ViewHolder, position: Int) {
        holder.bindItems(MenuList[position])


    }

    //this method is giving the size of the list
    override fun getItemCount(): Int {
        return MenuList.size
    }

    //the class is hodling the list view
    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bindItems(m: mOrder_details) {

            val PREF_NAME = "userInfo"
            var sharedPref =
                itemView.context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
            var editor: SharedPreferences.Editor;
            var USERID_KEY = "UserID"
            editor = sharedPref.edit()
            editor.apply()

            val textTitle = itemView.findViewById(R.id.tvDesc) as TextView
            val textDesc = itemView.findViewById(R.id.meal_desc) as TextView
            val tvnum = itemView.findViewById(R.id.tvnum) as TextView
            val textPrice = itemView.findViewById(R.id.meal_price) as TextView
            val txtUrlImage = itemView.findViewById(R.id.meal_image) as ImageView
            val iv_plus = itemView.findViewById(R.id.iv_plus) as ImageView
            val iv_min = itemView.findViewById(R.id.iv_min) as ImageView
            val iv_del = itemView.findViewById(R.id.iv_del) as ImageView

            val fav = itemView.findViewById(R.id.iv_fav) as ImageView
            val favSaved = itemView.findViewById(R.id.iv_favSaved) as ImageView


            //get saved meal
            val call: Call<List<mSaved>> =
                ApiClient.getClient.getSaved(sharedPref.getInt(USERID_KEY, 0))
            call.enqueue(object : Callback<List<mSaved>> {

                override fun onResponse(
                    call: Call<List<mSaved>>?,
                    response: Response<List<mSaved>>?
                ) {


                    for (i in 0 until response!!.body()!!.size) {
                        Log.i("meal_id_Saved", response.body()!![i].mealId.toString())
                        if (response.body()!![i].mealId == m.mealId) {
                            favSaved.visibility = View.VISIBLE
                            fav.visibility = View.INVISIBLE
                            Log.i(
                                "meal_id_Saved_in_if",
                                response.body()!![i].mealId.toString() + "==" + m.mealId
                            )
                            break
                        } else {

                            fav.visibility = View.VISIBLE
                            favSaved.visibility = View.INVISIBLE
                            Log.i(
                                "meal_id_Saved_in_else",
                                response.body()!![i].mealId.toString() + "!=" + m.mealId
                            )
                        }
                    }

                }

                override fun onFailure(call: Call<List<mSaved>>?, t: Throwable?) {
                    Log.i("onFailure:", t!!.message.toString())
                }

            })


//واقف هنا   stay hare
            iv_plus.setOnClickListener(View.OnClickListener {

                m.quantity++
                tvnum.text = m.quantity.toString()

                textPrice.text = (m.mealPrice!!.toFloat() * m.quantity.toFloat()).toString()

                val plusMeal = mOrder_details(m.orderId!!, m.mealId!!, m.quantity)

                Log.i("meal_id", m.mealId.toString())
                Log.i("order_id", m.orderId.toString())
                val call: Call<mOrder_details> = ApiClient.getClient.addMealQty(plusMeal)
                call.enqueue(object : Callback<mOrder_details> {

                    override fun onResponse(
                        call: Call<mOrder_details>?,
                        response: Response<mOrder_details>?
                    ) {

                        if (response!!.isSuccessful()) {
                            var myMeal: mOrder_details = response.body()!!


                            if (myMeal.error == true)
                                DynamicToast.makeWarning(it.context, "" + myMeal.msg)
                                    .show();
                            else {

//                                DynamicToast.makeSuccess(it.context, "" + myMeal.msg)
//                                    .show();

                            }


                        } else {

                            DynamicToast.makeError(
                                it.context,
                                "can not increase this meal now try letter."
                            )
                                .show();

                        }

                    }

                    override fun onFailure(call: Call<mOrder_details>?, t: Throwable?) {
                        Log.i("onFailure:", t!!.message.toString())

                    }

                })


            })

            iv_del.setOnClickListener(View.OnClickListener {
                val delMeal = mOrder_details(m.orderId!!, m.mealId!!)

                Log.i("meal_id", m.mealId.toString())
                Log.i("order_id", m.orderId.toString())
                val call: Call<mOrder_details> = ApiClient.getClient.deleteMealCart(delMeal)
                call.enqueue(object : Callback<mOrder_details> {

                    override fun onResponse(
                        call: Call<mOrder_details>?,
                        response: Response<mOrder_details>?
                    ) {
                        Log.i("onResponse:", response.toString())
                        Log.i("onResponseBody:", response!!.body().toString())
                        if (response!!.isSuccessful()) {
                            var myMeal: mOrder_details = response.body()!!


                            if (myMeal.error == true)
                                DynamicToast.makeWarning(it.context, "" + myMeal.msg)
                                    .show();
                             else {

                                DynamicToast.makeSuccess(it.context, "" + myMeal.msg)
                                    .show();
                                updateAdapterData(getAdapterPosition())
                                if(getItemCount()==0)
                                shapingCart.btn_cart_check.visibility = View.INVISIBLE
                            }


                        } else {

                            DynamicToast.makeError(
                                it.context,
                                "can not delete this meal now try letter."
                            )
                                .show();

                        }

                    }

                    override fun onFailure(call: Call<mOrder_details>?, t: Throwable?) {
                        Log.i("onFailure:", t!!.message.toString())

                    }

                })


            })

            fav.setOnClickListener(View.OnClickListener {

                Log.i("USERID_KEY", sharedPref.getInt(USERID_KEY, 0).toString())
                Log.i("meal_id", m.mealId.toString())

                var save = mSaved(sharedPref.getInt(USERID_KEY, 0), m.mealId)
                val call: Call<mSaved> = ApiClient.getClient.SavedMeal(save)
                call.enqueue(object : Callback<mSaved> {

                    override fun onResponse(call: Call<mSaved>?, response: Response<mSaved>?) {


                        DynamicToast.makeSuccess(it.context, "Meal saved")
                            .show();
                        favSaved.visibility = View.VISIBLE
                        fav.visibility = View.INVISIBLE


                    }

                    override fun onFailure(call: Call<mSaved>?, t: Throwable?) {
                        Log.i("onFailure:", t!!.message.toString())
                    }

                })

            })

            favSaved.setOnClickListener(View.OnClickListener {

                Log.i("USERID_KEY", sharedPref.getInt(USERID_KEY, 0).toString())
                Log.i("meal_id", m.mealId.toString())

                val call: Call<mSaved> =
                    ApiClient.getClient.unSaved(sharedPref.getInt(USERID_KEY, 0), m.mealId!!)
                call.enqueue(object : Callback<mSaved> {

                    override fun onResponse(call: Call<mSaved>?, response: Response<mSaved>?) {

                        fav.visibility = View.VISIBLE
                        favSaved.visibility = View.INVISIBLE
                        DynamicToast.makeSuccess(it.context, "Meal unsaved")
                            .show();


                    }

                    override fun onFailure(call: Call<mSaved>?, t: Throwable?) {
                        Log.i("onFailure:", t!!.message.toString())
                    }

                })

            })




            iv_min.setOnClickListener(View.OnClickListener {

                if (m.quantity == 1) Toast.makeText(
                    it.context,
                    "can not less qty than 1",
                    Toast.LENGTH_LONG
                ).show()
                else {
                    m.quantity--
                    tvnum.text = m.quantity.toString()

                    textPrice.text = (m.mealPrice!!.toFloat() * m.quantity.toFloat()).toString()

                    val minMeal = mOrder_details(m.orderId!!, m.mealId!!, m.quantity)

                    Log.i("meal_id", m.mealId.toString())
                    Log.i("order_id", m.orderId.toString())
                    val call: Call<mOrder_details> = ApiClient.getClient.addMealQty(minMeal)
                    call.enqueue(object : Callback<mOrder_details> {

                        override fun onResponse(
                            call: Call<mOrder_details>?,
                            response: Response<mOrder_details>?
                        ) {

                            if (response!!.isSuccessful()) {
                                var myMeal: mOrder_details = response.body()!!


                                if (myMeal.error == true)
                                    DynamicToast.makeWarning(it.context, "" + myMeal.msg)
                                        .show();
                                else {

//                                DynamicToast.makeSuccess(it.context, "" + myMeal.msg)
//                                    .show();

                                }


                            } else {

                                DynamicToast.makeError(
                                    it.context,
                                    "can not increase this meal now try letter."
                                )
                                    .show();

                            }

                        }

                        override fun onFailure(call: Call<mOrder_details>?, t: Throwable?) {
                            Log.i("onFailure:", t!!.message.toString())

                        }

                    })
                }
            })
            textTitle.text = m.mealName
            textDesc.text = m.mealName
            textPrice.text = m.totalPrice
            tvnum.text = m.quantity.toString()


            Picasso.get()
                .load(ApiClient.BASE_URL + "image/Meal_images/" + m.mealImage)
                .placeholder(R.drawable.loading)
                .error(R.drawable.no_image)
                .into(txtUrlImage);
        }
    }

    fun updateAdapterData(pos: Int) {
        MenuList.removeAt(pos)

        notifyDataSetChanged()
    }


}

