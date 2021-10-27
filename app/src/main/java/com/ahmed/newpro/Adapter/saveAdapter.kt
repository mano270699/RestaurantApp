package com.ahmed.newpro.Adapter

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.ahmed.newpro.API.ApiClient
import com.ahmed.newpro.Model.mSaved
import com.ahmed.newpro.R
import com.ahmed.newpro.meal_details
import com.pranavpandey.android.dynamic.toasts.DynamicToast
import com.squareup.picasso.Picasso
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class saveAdapter(

) : RecyclerView.Adapter<saveAdapter.ViewHolder>() {

    lateinit var MenuList: ArrayList<mSaved>
    lateinit var context: Context



    constructor(MenuList: ArrayList<mSaved>, context: Context) : this() {
        this.MenuList = MenuList
        this.context = context
    }


    //this method is returning the view for each item in the list
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): saveAdapter.ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.rec_saved, parent, false)
        return ViewHolder(v)
    }

    //this method is binding the data on the list
    override fun onBindViewHolder(holder: saveAdapter.ViewHolder, position: Int) {
        holder.bindItems(MenuList[position])


    }

    //this method is giving the size of the list
    override fun getItemCount(): Int {
        return MenuList.size
    }

    //the class is hodling the list view
    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bindItems(m: mSaved) {
            val Save_card = itemView.findViewById(R.id.saved_card) as CardView

            val textPrice = itemView.findViewById(R.id.meal_price) as TextView
            val saved_meal_Title = itemView.findViewById(R.id.saved_meal_Title) as TextView
            val txtUrlImage = itemView.findViewById(R.id.meal_image) as ImageView
            val deleteSaved = itemView.findViewById(R.id.deleteSaved) as ImageView


            textPrice.text = m.mealPrice
            saved_meal_Title.text = m.mealName

            Save_card.setOnClickListener(View.OnClickListener {


                val intent: Intent
                intent = Intent(it.context, meal_details::class.java)
                intent.putExtra("id", m.mealId)
                intent.putExtra("desc", m.description)
                intent.putExtra("price", m.mealPrice)
                intent.putExtra("title", m.mealName)
                intent.putExtra("urlImage", m.mealImage)

                it.context.startActivity(intent)
            })

            deleteSaved.setOnClickListener(View.OnClickListener {

                val call: Call<mSaved> = ApiClient.getClient.unSaved(m.customerId!!, m.mealId!!)
                call.enqueue(object : Callback<mSaved> {

                    override fun onResponse(call: Call<mSaved>?, response: Response<mSaved>?) {



                        DynamicToast.makeSuccess(it.context, "Meal unsaved")
                            .show();

                        updateReceiptsList(getAdapterPosition())




                    }

                    override fun onFailure(call: Call<mSaved>?, t: Throwable?) {
                        Log.i("onFailure:", t!!.message.toString())
                    }

                })


            })


            Picasso.get()
                .load(ApiClient.BASE_URL + "image/Meal_images/" + m.mealImage)
                .placeholder(R.drawable.loading)
                .error(R.drawable.no_image)
                .into(txtUrlImage);
        }


    }


    fun updateReceiptsList(pos: Int) {
        MenuList.removeAt(pos)

        notifyDataSetChanged()
    }



}