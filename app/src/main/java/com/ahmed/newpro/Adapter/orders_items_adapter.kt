package com.ahmed.newpro.Adapter
import android.content.Context
import android.content.Intent
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
import com.ahmed.newpro.Model.mOrder_details
import com.ahmed.newpro.Model.order_data
import com.ahmed.newpro.R
import com.ahmed.newpro.order_path_details
import com.squareup.picasso.Picasso






class  orders_items_adapter (
    val MenuList: ArrayList<mOrder_details>,
    val context: Context
): RecyclerView.Adapter<orders_items_adapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): orders_items_adapter.ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.rec_order_details, parent, false)
        return orders_items_adapter.ViewHolder(v)
    }

    //this method is binding the data on the list
    override fun onBindViewHolder(holder: orders_items_adapter.ViewHolder, position: Int) {
        holder.bindItems(MenuList[position])

    }
    //this method is giving the size of the list
    override fun getItemCount(): Int {
        return MenuList.size
    }
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bindItems(m: mOrder_details) {

//            val cardMenu= itemView.findViewById(R.id.order_card) as CardView
            val meal_image= itemView.findViewById(R.id.meal_image) as ImageView
            val tv_qty = itemView.findViewById(R.id.tv_qty) as TextView
            val meal_price = itemView.findViewById(R.id.meal_price) as TextView
            val meal_title = itemView.findViewById(R.id.meal_title) as TextView






            tv_qty.text = "Quantity:"+m.quantity.toString()
            meal_price.text = m.totalPrice.toString()+"$"
            meal_title.text = m.mealName


            Picasso.get()
                .load(ApiClient.BASE_URL + "image/Meal_images/" +m.mealImage)
                .placeholder(R.drawable.loading)
                .error(R.drawable.no_image)
                .into(meal_image);

//            cardMenu.setOnClickListener(View.OnClickListener {
//
//
//                val intent: Intent
//                intent=   Intent(it.context, order_path_details::class.java)
//                intent.putExtra("order_id",m.id)
//                intent.putExtra("date",m.date)
//                intent.putExtra("price",m.price)
//                intent.putExtra("time", m.time)
//                intent.putExtra("status", m.status)
//                intent.putExtra("num_item", m.num_item)
//                intent.putExtra("cash_methods", m.cash_methods)
//                intent.putExtra("Delivery_methods", m.Delivery_methods)
//
//
//                it.context.startActivity(intent)
//            })


        }
    }

}