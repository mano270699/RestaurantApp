package com.ahmed.newpro


import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ahmed.newpro.API.ApiClient
import com.ahmed.newpro.Adapter.orders_items_adapter
import com.ahmed.newpro.Model.mOrder_details
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * A simple [Fragment] subclass.
 */
class order_details :  BaseFragment() {
    //store login data in shard
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


    lateinit var recyclerView: RecyclerView;
    lateinit var tvName: TextView;
    lateinit var tvAddress: TextView;
    lateinit var tvPhone: TextView;
    lateinit var txtDelivery_method: TextView;
    lateinit var txtCash_method: TextView;
    lateinit var txtSuptotal: TextView;
    lateinit var txtShipping: TextView;
    lateinit var txtTotal: TextView;
    var shhipong:Float=5.00f;

    lateinit var adapter: orders_items_adapter;
    val Orders_details = ArrayList<mOrder_details>()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_order_details, container, false)

    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
//SET SHARDpRES
        sharedPref = requireContext().getSharedPreferences(PREF_NAME, PRIVATE_MODE)

        editor = sharedPref.edit()
        editor.apply()
        //getting recyclerview from xml
        recyclerView = requireView().findViewById(R.id.menu_recycleView) as RecyclerView
        tvName = getView()!!.findViewById(R.id.tvName) as TextView
        tvAddress = getView()!!.findViewById(R.id.tvAddress) as TextView
        tvPhone = getView()!!.findViewById(R.id.tvPhone) as TextView
        txtDelivery_method = getView()!!.findViewById(R.id.txtDelivery_method) as TextView
        txtCash_method = getView()!!.findViewById(R.id.txtCash_method) as TextView
        txtSuptotal = getView()!!.findViewById(R.id.txtSuptotal) as TextView
        txtShipping = getView()!!.findViewById(R.id.txtShipping) as TextView
        txtTotal = getView()!!.findViewById(R.id.txtTotal) as TextView
        recyclerView.layoutManager = LinearLayoutManager(activity, RecyclerView.VERTICAL, false)


        val i: Float = ACTIVITY.price.toFloat()
        txtDelivery_method.text=ACTIVITY.Delivery_methods
        txtCash_method.text=ACTIVITY.cash_methods
        txtSuptotal.text=(i-shhipong).toString()

        txtShipping.text=shhipong.toString()
        txtTotal.text=ACTIVITY.price.toString()



        tvName.text=sharedPref.getString(USERName_KEY, "Nan")
        tvAddress.text=sharedPref.getString(USERAddress_KEY, "Nan")
        tvPhone.text=sharedPref.getString(USERPhone_KEY, "Nan")


        getData()
    }

    private fun getData() {

        val call: Call<List<mOrder_details>> = ApiClient.getClient.getMealsOrders(ACTIVITY.order_id)
        call.enqueue(object : Callback<List<mOrder_details>> {

            override fun onResponse(call: Call<List<mOrder_details>>?, response: Response<List<mOrder_details>>?) {

                Orders_details.addAll(response!!.body()!!)
                //creating our adapter
                adapter = orders_items_adapter(Orders_details, requireContext());

                //now adding the adapter to recyclerview
                recyclerView.adapter = adapter



            }

            override fun onFailure(call: Call<List<mOrder_details>>?, t: Throwable?) {
                Log.i("onFailure:", t!!.message.toString())
            }

        })
    }


}
