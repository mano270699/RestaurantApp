package com.ahmed.newpro.Adapter

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.ahmed.newpro.API.ApiClient
import com.ahmed.newpro.Menu
import com.ahmed.newpro.Model.mCat
import com.ahmed.newpro.Model.mFeedback
import com.ahmed.newpro.R
import com.squareup.picasso.Picasso
import de.hdodenhof.circleimageview.CircleImageView

class feedBackAdapter(
    val feedList: ArrayList<mFeedback>,
    val context: Context
) : RecyclerView.Adapter<feedBackAdapter.ViewHolder>() {

    //this method is returning the view for each item in the list
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): feedBackAdapter.ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.rec_feed, parent, false)
        return ViewHolder(v)
    }

    //this method is binding the data on the list
    override fun onBindViewHolder(holder: feedBackAdapter.ViewHolder, position: Int) {
        holder.bindItems(feedList[position])

    }

    //this method is giving the size of the list
    override fun getItemCount(): Int {
        return feedList.size
    }

    //the class is hodling the list view
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bindItems(feed: mFeedback) {

            val textName = itemView.findViewById(R.id.feedCard_cust_name) as TextView
            val textComment = itemView.findViewById(R.id.feedCard_cust_comment) as TextView
            val textDate = itemView.findViewById(R.id.feedCard_feed_date) as TextView
            val ratebar = itemView.findViewById(R.id.feedCard_feed_rate) as RatingBar
            val txtUrlImage = itemView.findViewById(R.id.feedCard_profile_image) as CircleImageView

            textName.text = feed.full_name
            textComment.text = feed.comment
            textDate.text = (feed.date_time).split("T")[0]
            ratebar.rating= feed.rate!!.toFloat()




            Picasso.get()
                .load(ApiClient.BASE_URL + "image/customer/" + feed.image)
                .placeholder(R.drawable.loading)
                .error(R.drawable.no_image)
                .into(txtUrlImage);
        }
    }

}