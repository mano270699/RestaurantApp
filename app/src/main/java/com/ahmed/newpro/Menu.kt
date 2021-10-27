package com.ahmed.newpro

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.view.GravityCompat
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ahmed.newpro.API.ApiClient
import com.ahmed.newpro.Adapter.catAdapter
import com.ahmed.newpro.Adapter.menuAdapter
import com.ahmed.newpro.Model.mCat
import com.ahmed.newpro.Model.mMenu
import com.google.android.material.navigation.NavigationView
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class Menu : AppCompatActivity() {
    lateinit var recyclerView: RecyclerView;
    lateinit var imageView1: ImageView;
    lateinit var imageView2: ImageView;
    lateinit var adapter: menuAdapter;
    var cat_id: Int = 0;
    val meals = ArrayList<mMenu>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu)

        val intentFilter = IntentFilter()
        intentFilter.addAction("com.package.ACTION_LOGOUT")
        registerReceiver(object : BroadcastReceiver() {
            override fun onReceive(context: Context?, intent: Intent?) {
                Log.d("onReceive", "Logout in progress")
                //At this point you should start the login activity and finish this one
                finish()
            }
        }, intentFilter)
        imageView1 = findViewById(R.id.image1)
        imageView2 = findViewById(R.id.image2)


        val i = intent
        cat_id = i.getIntExtra("cat_id", 0)



        //getting recyclerview from xml
        recyclerView = findViewById(R.id.menu_recycleView) as RecyclerView

        //adding a layoutmanager
        recyclerView.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)


        //crating an arraylist to store users using the data class user







        getData()
    }




    private fun getData() {

        val call: Call<List<mMenu>> = ApiClient.getClient.getMeals(cat_id)
        call.enqueue(object : Callback<List<mMenu>> {

            override fun onResponse(call: Call<List<mMenu>>?, response: Response<List<mMenu>>?) {

                meals.addAll(response!!.body()!!)
                //creating our adapter
                adapter = menuAdapter(meals, baseContext);

                //now adding the adapter to recyclerview
                recyclerView.adapter = adapter



            }

            override fun onFailure(call: Call<List<mMenu>>?, t: Throwable?) {
                Log.i("onFailure:", t!!.message.toString())
            }

        })
    }


    fun one_image(@Suppress("UNUSED_PARAMETER")view: View) {



        //adding a layoutmanager
        recyclerView.layoutManager = GridLayoutManager(this, 2)
        recyclerView.adapter = adapter
        Toast.makeText(this, "Image 1 licked", Toast.LENGTH_LONG).show()
    }

    fun more_image(@Suppress("UNUSED_PARAMETER") view: View) {



        recyclerView.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        recyclerView.adapter = adapter
        Toast.makeText(this, "Image 2 licked", Toast.LENGTH_LONG).show()


    }

    fun gomainMeanufromMenu(view: View) {
        finish()
    }


}
