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
import com.ahmed.newpro.Model.mMenu
import com.ahmed.newpro.Model.mOrder
import com.ahmed.newpro.Model.mOrder_details
import com.ahmed.newpro.Model.order_data
import com.ahmed.newpro.R
import com.ahmed.newpro.order_path_details
import com.squareup.picasso.Picasso






class orders_adapter (
    val MenuList: ArrayList<mOrder>,
    val context: Context
): RecyclerView.Adapter<orders_adapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): orders_adapter.ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.rec_orders, parent, false)
        return orders_adapter.ViewHolder(v)
    }

    //this method is binding the data on the list
    override fun onBindViewHolder(holder: orders_adapter.ViewHolder, position: Int) {
        holder.bindItems(MenuList[position])

    }
    //this method is giving the size of the list
    override fun getItemCount(): Int {
        return MenuList.size
    }
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bindItems(m: mOrder) {

            val cardMenu= itemView.findViewById(R.id.order_card) as CardView
            val textdate= itemView.findViewById(R.id.tv_date) as TextView
            val textnum_item = itemView.findViewById(R.id.tv_num_item) as TextView
            val textPrice = itemView.findViewById(R.id.tv_price) as TextView
            val tv_time = itemView.findViewById(R.id.tv_time) as TextView
            val tv_status = itemView.findViewById(R.id.tv_status) as TextView
            val tv_cash = itemView.findViewById(R.id.tv_cash) as TextView





            var str=     if(m.Num_items!! >1) " items order" else " item order"
            textdate.text = (m.date)
            textnum_item.text = m.Num_items.toString()+str
            tv_time.text = (m.time)
            textPrice.text = ((m.totalPrice)!!.toFloat()+5.00f).toString()
            tv_status.text = m.status
            tv_cash.text=m.paymentMethod


            cardMenu.setOnClickListener(View.OnClickListener {


                val intent: Intent
                intent=   Intent(it.context, order_path_details::class.java)
                intent.putExtra("order_id",m.orderId)
                intent.putExtra("date",(m.date))
                intent.putExtra("price",((m.totalPrice)!!.toFloat()+5.00f))
                intent.putExtra("time", (m.time))
                intent.putExtra("status", m.status)
                intent.putExtra("num_item", m.Num_items)
                intent.putExtra("cash_methods", m.paymentMethod)
                intent.putExtra("Delivery_methods", m.deliveryMethod)


                it.context.startActivity(intent)
            })


        }
    }

}