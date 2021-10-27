package com.ahmed.newpro

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ahmed.newpro.API.ApiClient
import com.ahmed.newpro.Adapter.menuAdapter
import com.ahmed.newpro.Adapter.offerAdapter
import com.ahmed.newpro.Model.mMenu
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class Offer : AppCompatActivity() {
    lateinit var recyclerView: RecyclerView;
    lateinit var adapter: offerAdapter;
    val offers = ArrayList<mMenu>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_offer)
        val intentFilter = IntentFilter()
        intentFilter.addAction("com.package.ACTION_LOGOUT")
        registerReceiver(object : BroadcastReceiver() {
            override fun onReceive(context: Context?, intent: Intent?) {
                Log.d("onReceive", "Logout in progress")
                //At this point you should start the login activity and finish this one
                finish()
            }
        }, intentFilter)
        recyclerView = findViewById(R.id.offer_recycleView) as RecyclerView

        //adding a layoutmanager
        recyclerView.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)

        getmealOffer()


        //creating our adapter
        adapter = offerAdapter(offers, this);

        //now adding the adapter to recyclerview
        recyclerView.adapter = adapter


    }
    private fun getmealOffer() {

        val call: Call<List<mMenu>> = ApiClient.getClient.getOffer()
        call.enqueue(object : Callback<List<mMenu>> {

            override fun onResponse(call: Call<List<mMenu>>?, response: Response<List<mMenu>>?) {

                offers.addAll(response!!.body()!!)
                //creating our adapter
                adapter = offerAdapter(offers, baseContext);

                //now adding the adapter to recyclerview
                recyclerView.adapter = adapter



            }

            override fun onFailure(call: Call<List<mMenu>>?, t: Throwable?) {
                Log.i("onFailure:", t!!.message.toString())
            }

        })
    }

    fun gomainMeanu(view: View) {
        finish()
    }

}

