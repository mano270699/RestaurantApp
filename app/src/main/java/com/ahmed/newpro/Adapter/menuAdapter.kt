package com.ahmed.newpro.Adapter

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.ahmed.newpro.API.ApiClient
import com.ahmed.newpro.Model.mMenu
import com.ahmed.newpro.Model.mSaved
import com.ahmed.newpro.R
import com.ahmed.newpro.meal_details
import com.pranavpandey.android.dynamic.toasts.DynamicToast
import com.squareup.picasso.Picasso
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class  menuAdapter(
    val MenuList: ArrayList<mMenu>,
    val context: Context
) : RecyclerView.Adapter<menuAdapter.ViewHolder>() {

    //this method is returning the view for each item in the list
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): menuAdapter.ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.rec_menu, parent, false)
        return ViewHolder(v)
    }

    //this method is binding the data on the list
    override fun onBindViewHolder(holder: menuAdapter.ViewHolder, position: Int) {
        holder.bindItems(MenuList[position])

    }

    //this method is giving the size of the list
    override fun getItemCount(): Int {
        return MenuList.size
    }

    //the class is hodling the list view
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bindItems(m: mMenu) {
            val cardMenu = itemView.findViewById(R.id.menu_card) as CardView
            val textTitle = itemView.findViewById(R.id.meal_Title) as TextView
            val textDesc = itemView.findViewById(R.id.rec_menu_meal_Desc) as TextView
            val textPrice = itemView.findViewById(R.id.meal_price) as TextView
            val txtUrlImage = itemView.findViewById(R.id.meal_image) as ImageView

            val fav = itemView.findViewById(R.id.fav) as ImageView
            val favSaved = itemView.findViewById(R.id.favSaved) as ImageView
            val PREF_NAME = "userInfo"
            var sharedPref =
                itemView.context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
            var editor: SharedPreferences.Editor;
            var USERID_KEY = "UserID"
            editor = sharedPref.edit()
            editor.apply()


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
                        if (response.body()!![i].mealId == m.meal_id) {
                            favSaved.visibility = View.VISIBLE
                            fav.visibility = View.INVISIBLE
                            Log.i(
                                "meal_id_Saved_in_if",
                                response.body()!![i].mealId.toString() + "==" + m.meal_id
                            )
                            break
                        } else {

                            fav.visibility = View.VISIBLE
                            favSaved.visibility = View.INVISIBLE
                            Log.i(
                                "meal_id_Saved_in_else",
                                response.body()!![i].mealId.toString() + "!=" + m.meal_id
                            )
                        }
                    }

                }

                override fun onFailure(call: Call<List<mSaved>>?, t: Throwable?) {
                    Log.i("onFailure:", t!!.message.toString())
                }

            })



            textTitle.text = m.meal_name
            textDesc.text = m.description
            textPrice.text = m.meal_price.toString()
            fav.setOnClickListener(View.OnClickListener {

                Log.i("USERID_KEY", sharedPref.getInt(USERID_KEY, 0).toString())
                Log.i("meal_id", m.meal_id.toString())

                var save = mSaved(sharedPref.getInt(USERID_KEY, 0), m.meal_id)
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
                Log.i("meal_id", m.meal_id.toString())

                val call: Call<mSaved> =
                    ApiClient.getClient.unSaved(sharedPref.getInt(USERID_KEY, 0), m.meal_id!!)
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




            cardMenu.setOnClickListener(View.OnClickListener {

                Toast.makeText(it.context, "" + textTitle.text, Toast.LENGTH_LONG).show()
                val intent: Intent
                intent = Intent(it.context, meal_details::class.java)

                intent.putExtra("id", m.meal_id)
                intent.putExtra("desc", m.description)
                intent.putExtra("price", m.meal_price.toString())
                intent.putExtra("title", m.meal_name)
                intent.putExtra("urlImage", m.meal_image)

                it.context.startActivity(intent)
            })
            Picasso.get()
                .load(ApiClient.BASE_URL + "image/Meal_images/" + m.meal_image)
                .placeholder(R.drawable.loading)
                .error(R.drawable.no_image)
                .into(txtUrlImage);


        }
    }


}