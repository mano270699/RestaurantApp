package com.ahmed.newpro

import android.content.*
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ahmed.newpro.API.ApiClient
import com.ahmed.newpro.Adapter.orders_adapter
import com.ahmed.newpro.Adapter.saveAdapter
import com.ahmed.newpro.Adapter.shoping_cart_Adapter
import com.ahmed.newpro.Model.mOrder
import com.ahmed.newpro.Model.mSaved
import com.ahmed.newpro.Model.order_data
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class orders : AppCompatActivity() {

    lateinit var recyclerView: RecyclerView;
    lateinit var adapter: orders_adapter;
    val orders = ArrayList<mOrder>()

    private var PRIVATE_MODE = 0
    private val PREF_NAME = "userInfo"
    lateinit var sharedPref: SharedPreferences
    lateinit var editor: SharedPreferences.Editor;
    var USERID_KEY = "UserID"
    var USEREmail_KEY = "UserEmail"
    var USERName_KEY = "UserName"
    var USERImage_KEY = "UserImage"
    var USERPhone_KEY = "UserPhone"
    var USERAddress_KEY = "UserAddress"
    var USERBDate_KEY = "UserBDate"



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_orders)
        val intentFilter = IntentFilter()
        intentFilter.addAction("com.package.ACTION_LOGOUT")
        registerReceiver(object : BroadcastReceiver() {
            override fun onReceive(context: Context?, intent: Intent?) {
                Log.d("onReceive", "Logout in progress")
                //At this point you should start the login activity and finish this one
                finish()
            }
        }, intentFilter)


        recyclerView = findViewById(R.id.order_recycleView) as RecyclerView
        //adding a layoutmanager
        recyclerView.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)




        //SET SHARDpRES
        sharedPref = getSharedPreferences(PREF_NAME, PRIVATE_MODE)

        editor = sharedPref.edit()
        editor.apply()
        getData()



    }

    fun gotomain(view: View) {
        val intent = Intent(this, MainActivity::class.java)
        this.startActivity(intent)}

    private fun getData() {

        val call: Call<List<mOrder>> = ApiClient.getClient.getOrders(sharedPref.getInt(USERID_KEY, 0))
        call.enqueue(object : Callback<List<mOrder>> {

            override fun onResponse(call: Call<List<mOrder>>?, response: Response<List<mOrder>>?) {

                orders.addAll(response!!.body()!!)
                //creating our adapter
                adapter = orders_adapter(orders, baseContext);

                //now adding the adapter to recyclerview
                recyclerView.adapter = adapter



            }

            override fun onFailure(call: Call<List<mOrder>>?, t: Throwable?) {
                Log.i("onFailure:", t!!.message.toString())
            }

        })
    }



}
