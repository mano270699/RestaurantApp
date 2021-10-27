package com.ahmed.newpro.Adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.ahmed.newpro.API.ApiClient
import com.ahmed.newpro.Menu
import com.ahmed.newpro.Model.mCat
import com.ahmed.newpro.R
import com.squareup.picasso.Picasso
import de.hdodenhof.circleimageview.CircleImageView

class catAdapter(
    val catList: ArrayList<mCat>,
    val context: Context
) : RecyclerView.Adapter<catAdapter.ViewHolder>() {

    //this method is returning the view for each item in the list
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): catAdapter.ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.rec_cat, parent, false)
        return ViewHolder(v)
    }

    //this method is binding the data on the list
    override fun onBindViewHolder(holder: catAdapter.ViewHolder, position: Int) {
        holder.bindItems(catList[position])

    }

    //this method is giving the size of the list
    override fun getItemCount(): Int {
        return catList.size
    }

    //the class is hodling the list view
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bindItems(cat: mCat) {

            val textName = itemView.findViewById(R.id.recCat_tvCatName) as TextView
            val txtUrlImage = itemView.findViewById(R.id.recCat_image) as CircleImageView

            textName.text = cat.cat_name
            val cat_id: Int = cat.cat_id

            txtUrlImage.setOnClickListener {
                Toast.makeText(it.context, "Cliked on "+cat_id, Toast.LENGTH_SHORT).show()

                val intent = Intent(it.context, Menu::class.java)
                intent.putExtra("cat_id",cat_id)
                it.context.startActivity(intent)
            }

            Picasso.get()
                .load(ApiClient.BASE_URL + "image/category_images/" + cat.cat_image)
                .placeholder(R.drawable.loading)
                .error(R.drawable.no_image)
                .into(txtUrlImage);
        }
    }

}