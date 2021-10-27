package com.ahmed.newpro

import android.content.*
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ahmed.newpro.API.ApiClient
import com.ahmed.newpro.Adapter.menuAdapter
import com.ahmed.newpro.Adapter.orders_items_adapter
import com.ahmed.newpro.Adapter.shoping_cart_Adapter
import com.ahmed.newpro.Model.mCat
import com.ahmed.newpro.Model.mMenu
import com.ahmed.newpro.Model.mOrder_details
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class shaping_cart : AppCompatActivity() {
    //store login data in shard
    private var PRIVATE_MODE = 0
    private val PREF_NAME = "userInfo"
    lateinit var sharedPref: SharedPreferences
    lateinit var editor: SharedPreferences.Editor;
    var USERID_KEY = "UserID"
    var USERORDERID_KEY = "UserOrderId"
    var USEREmail_KEY = "UserEmail"
    var USERName_KEY = "UserName"
    var USERImage_KEY = "UserImage"
    var USERPhone_KEY = "UserPhone"
    var USERAddress_KEY = "UserAddress"
    var USERBDate_KEY = "UserBDate"

    val Orders_details_cart = ArrayList<mOrder_details>()
    lateinit var recyclerView: RecyclerView;
    lateinit var adapter: shoping_cart_Adapter;

    lateinit var card_AddCart: CardView;
    lateinit var btn_cart_check: Button;


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_shaping_cart)
        val intentFilter = IntentFilter()
        intentFilter.addAction("com.package.ACTION_LOGOUT")
        registerReceiver(object : BroadcastReceiver() {
            override fun onReceive(context: Context?, intent: Intent?) {
                Log.d("onReceive", "Logout in progress")
                //At this point you should start the login activity and finish this one
                finish()
            }
        }, intentFilter)


        //SET SHARDpRES
        sharedPref = getSharedPreferences(PREF_NAME, PRIVATE_MODE)

        editor = sharedPref.edit()
        editor.apply()

        recyclerView = findViewById(R.id.cart_recycleView) as RecyclerView
        card_AddCart = findViewById(R.id.card_AddCart) as CardView
        btn_cart_check = findViewById(R.id.btn_cart_check) as Button

        //adding a layoutmanager
        recyclerView.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)






        getData()
    }

    fun gotomain(view: View) {


        val intent = Intent(this, MainActivity::class.java)
        this.startActivity(intent)
    }

    fun goToCheckout(view: View) {


        if (sharedPref.getString(USERAddress_KEY, "Nan").toString() == "Nan")
            go_to_setAddress();
        else {
            val intent = Intent(this, Checkout::class.java)
            this.startActivity(intent)


        }

    }

    private fun getData() {

        val call: Call<List<mOrder_details>> =
            ApiClient.getClient.getMealsOrders(sharedPref.getInt(USERORDERID_KEY, 0))
        call.enqueue(object : Callback<List<mOrder_details>> {

            override fun onResponse(
                call: Call<List<mOrder_details>>?,
                response: Response<List<mOrder_details>>?
            ) {

                Orders_details_cart.addAll(response!!.body()!!)
                //creating our adapter
                adapter = shoping_cart_Adapter(Orders_details_cart, baseContext, this@shaping_cart);

                //now adding the adapter to recyclerview
                recyclerView.adapter = adapter


                if (adapter.itemCount == 0) {
                    card_AddCart.visibility = View.INVISIBLE
                    btn_cart_check.visibility = View.INVISIBLE

                } else {
                    card_AddCart.visibility = View.VISIBLE
                    btn_cart_check.visibility = View.VISIBLE

                }


            }

            override fun onFailure(call: Call<List<mOrder_details>>?, t: Throwable?) {
                Log.i("onFailure:", t!!.message.toString())
            }

        })
    }


    private fun go_to_setAddress() {

        val intent = Intent(this, edit_text::class.java)
        this.startActivity(intent)
    }


}
