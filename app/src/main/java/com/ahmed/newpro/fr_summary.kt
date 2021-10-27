package com.ahmed.newpro


import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ahmed.newpro.API.ApiClient
import com.ahmed.newpro.Adapter.shoping_cart_Adapter
import com.ahmed.newpro.Adapter.summary_Adapter
import com.ahmed.newpro.Model.mOrder_details
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * A simple [Fragment] subclass.
 */
class fr_summary : Fragment() {
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


    var USERB_txtSuptotal_KEY = "txtSuptotal"
    var USERB_txtTotal_KEY = "txtTotal"
    var USERB_Payment_Method_KEY = "Payment_Method"
    var USERB_Delivery_Method_KEY = "Delivery_Method"

    var USER_num_people_mytabel_KEY = "numpeoplemytabel"
    var USER_tabel_id_mytabel_KEY = "tabel_id_mytabel"
    var USER_time_mytabel_KEY = "timemytabel"
    var USERB_date_mytabel_KEY = "date_mytabel"
    var USER_note_mytabel_KEY = "note_mytabel"



    lateinit var txtSuptotal: TextView
    lateinit var txtTotal: TextView

    lateinit var tvName: TextView
    lateinit var tvAddress: TextView
    lateinit var tvPhone: TextView
    lateinit var tvDelivery_method: TextView
    lateinit var tvCash_method: TextView


    val Orders_details_cart = ArrayList<mOrder_details>()
    lateinit var recyclerView: RecyclerView;
    lateinit var adapter: summary_Adapter;
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        //SET SHARDpRES
        sharedPref = requireContext().getSharedPreferences(PREF_NAME, PRIVATE_MODE)

        editor = sharedPref.edit()
        editor.apply()

        txtSuptotal = getView()!!.findViewById(R.id.txtSuptotal) as TextView
        txtTotal = getView()!!.findViewById(R.id.txtTotal) as TextView

        tvName = getView()!!.findViewById(R.id.tvName) as TextView
        tvAddress = getView()!!.findViewById(R.id.tvAddress) as TextView
        tvPhone = getView()!!.findViewById(R.id.tvPhone) as TextView


        tvDelivery_method = getView()!!.findViewById(R.id.txtDelivery_method) as TextView
        tvCash_method = getView()!!.findViewById(R.id.txtCash_method) as TextView



        //set data
        txtSuptotal.text=sharedPref.getString(USERB_txtSuptotal_KEY,"Nan")
        txtTotal.text=sharedPref.getString(USERB_txtTotal_KEY,"Nan")

        tvName.text = sharedPref.getString(USERName_KEY, "Nan")
        tvAddress.text = sharedPref.getString(USERAddress_KEY, "Nan")
        tvPhone.text = sharedPref.getString(USERPhone_KEY, "Nan")


        tvDelivery_method.text = sharedPref.getString(USERB_Delivery_Method_KEY, "Nan")
        tvCash_method.text = sharedPref.getString(USERB_Payment_Method_KEY, "Nan")

        Log.i("book_tabel_num_people",sharedPref.getInt(USER_num_people_mytabel_KEY,0).toString())
        Log.i("book_tabel_time", sharedPref.getString(USER_time_mytabel_KEY,"Nan").toString())
        Log.i("book_tabel_date",  sharedPref.getString(USERB_date_mytabel_KEY,"Nan").toString())
        Log.i("book_tabel_tabel_id",sharedPref.getInt(USER_tabel_id_mytabel_KEY,0).toString())
        Log.i("book_tabel_note", sharedPref.getString(USER_note_mytabel_KEY,"Nan").toString())


        recyclerView = getView()!!.findViewById(R.id.menu_recycleView) as RecyclerView

        //adding a layoutmanager
        recyclerView.layoutManager = LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)


        getData()

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
                adapter = summary_Adapter(Orders_details_cart, requireContext());

                //now adding the adapter to recyclerview
                recyclerView.adapter = adapter


            }

            override fun onFailure(call: Call<List<mOrder_details>>?, t: Throwable?) {
                Log.i("onFailure:", t!!.message.toString())
            }

        })
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_fr_summary, container, false)
    }


}
