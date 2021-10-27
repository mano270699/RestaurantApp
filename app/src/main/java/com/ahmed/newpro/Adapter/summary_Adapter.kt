package com.ahmed.newpro.Adapter

import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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
import com.pranavpandey.android.dynamic.toasts.DynamicToast
import com.squareup.picasso.Picasso
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class summary_Adapter(
    val MenuList: ArrayList<mOrder_details>,
    val context: Context
) : RecyclerView.Adapter<summary_Adapter.ViewHolder>() {

    //this method is returning the view for each item in the list
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): summary_Adapter.ViewHolder {
        val v =
            LayoutInflater.from(parent.context).inflate(R.layout.rec_summary_items, parent, false)
        return ViewHolder(v)
    }

    //this method is binding the data on the list
    override fun onBindViewHolder(holder: summary_Adapter.ViewHolder, position: Int) {
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



            textTitle.text = m.mealName
            textDesc.text = m.mealName
            textPrice.text = m.totalPrice
            tvnum.text = "quantity:" + m.quantity.toString()


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

