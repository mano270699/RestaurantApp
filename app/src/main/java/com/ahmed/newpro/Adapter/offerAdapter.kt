package com.ahmed.newpro.Adapter

import android.content.Context
import android.content.Intent
import android.graphics.Paint
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
import com.ahmed.newpro.R
import com.ahmed.newpro.meal_details
import com.squareup.picasso.Picasso

class offerAdapter(
    val MenuList: ArrayList<mMenu>,
    val context: Context
) : RecyclerView.Adapter<offerAdapter.ViewHolder>(){

    //this method is returning the view for each item in the list
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): offerAdapter.ViewHolder {
    val v = LayoutInflater.from(parent.context).inflate(R.layout.rec_offer, parent, false)
    return ViewHolder(v)
}

//this method is binding the data on the list
override fun onBindViewHolder(holder: offerAdapter.ViewHolder, position: Int) {
    holder.bindItems(MenuList[position])

}

//this method is giving the size of the list
override fun getItemCount(): Int {
    return MenuList.size
}

//the class is hodling the list view
class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    fun bindItems(m: mMenu) {
        val cardMenu= itemView.findViewById(R.id.menu_card) as CardView
        val textTitle= itemView.findViewById(R.id.meal_Title) as TextView
        val textDesc = itemView.findViewById(R.id.offer_meal_Desc) as TextView
        val textPrice = itemView.findViewById(R.id.meal_price) as TextView
        val textOldPrice = itemView.findViewById(R.id.old_meal_price) as TextView
        val txtUrlImage  = itemView.findViewById(R.id.meal_image) as ImageView
        textTitle.text = m.meal_name
        textDesc.text = m.description
        textPrice.text = m.meal_price.toString()
        textOldPrice.text = m.old_price.toString()


        textOldPrice.paintFlags = textOldPrice.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG



        cardMenu.setOnClickListener(View.OnClickListener {

            Toast.makeText(it.context,""+textTitle.text,Toast.LENGTH_LONG).show()
            val intent: Intent
            intent=   Intent(it.context, meal_details::class.java)
            intent.putExtra("id",m.meal_id)
            intent.putExtra("desc",m.description)
            intent.putExtra("price",m.meal_price.toString())
            intent.putExtra("title", m.meal_name)
            intent.putExtra("urlImage", m.meal_image)

            it.context.startActivity(intent)
        })
        Picasso.get()
            .load(ApiClient.BASE_URL + "image/Meal_images/" +m.meal_image)
            .placeholder(R.drawable.loading)
            .error(R.drawable.no_image)
            .into(txtUrlImage);
    }
}

}