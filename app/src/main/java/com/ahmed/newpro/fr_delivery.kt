package com.ahmed.newpro


import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import androidx.cardview.widget.CardView
import android.app.Activity
import android.content.SharedPreferences
import android.util.Log
import android.widget.TextView
import android.widget.Toast
import com.ahmed.newpro.API.ApiClient
import com.ahmed.newpro.Adapter.orders_adapter
import com.ahmed.newpro.Model.mOrder
import com.ahmed.newpro.Model.mTabelOrder
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


/**
 * A simple [Fragment] subclass.
 */
class fr_delivery : Fragment() {
    lateinit var rb_delivery: RadioButton
    lateinit var rb_tabel: RadioButton
    lateinit var card_delivery_method: CardView
    lateinit var card_table_method: CardView
    lateinit var tvName: TextView
    lateinit var tvAddress: TextView
    lateinit var tvPhone: TextView
    lateinit var tvChangeAddress: TextView
    lateinit var txtSuptotal: TextView
    lateinit var txtTotal: TextView

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
    var USERB_Delivery_Method_KEY = "Delivery_Method"




    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        //SET SHARDpRES
        sharedPref = requireContext().getSharedPreferences(PREF_NAME, PRIVATE_MODE)

        editor = sharedPref.edit()
        editor.apply()


        rb_delivery = getView()!!.findViewById(R.id.rb_delivery) as RadioButton
        rb_tabel = getView()!!.findViewById(R.id.rb_tabel) as RadioButton

        card_delivery_method = getView()!!.findViewById(R.id.card_delivery_method) as CardView
        card_table_method = getView()!!.findViewById(R.id.card_table_method) as CardView

        tvName = getView()!!.findViewById(R.id.tvName) as TextView
        tvAddress = getView()!!.findViewById(R.id.tvAddress) as TextView
        tvPhone = getView()!!.findViewById(R.id.tvPhone) as TextView
        tvChangeAddress = getView()!!.findViewById(R.id.tvChangeAddress) as TextView
        txtSuptotal = getView()!!.findViewById(R.id.txtSuptotal) as TextView
        txtTotal = getView()!!.findViewById(R.id.txtTotal) as TextView



        tvName.text = sharedPref.getString(USERName_KEY, "Nan")
        tvAddress.text = sharedPref.getString(USERAddress_KEY, "Nan")
        tvPhone.text = sharedPref.getString(USERPhone_KEY, "Nan")


        tvChangeAddress.setOnClickListener(View.OnClickListener {

            val intent: Intent

            intent = Intent(requireContext(), edit_text::class.java)
            startActivity(intent)
            getActivity()!!.finish()
        })

        rb_delivery.setOnClickListener(View.OnClickListener {
            setRb_delivery()

        })

        rb_tabel.setOnClickListener(View.OnClickListener {
            setRb_tabel()
            goTabel()

        })

        editor.putString(USERB_Delivery_Method_KEY,"delivery")
        editor.apply()

        card_delivery_method.setOnClickListener(View.OnClickListener {

            setRb_delivery()

        })

        card_table_method.setOnClickListener(View.OnClickListener {

            setRb_tabel()
            goTabel()

        })
        getsubToatal()

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_fr_delivery, container, false)
    }

    fun setRb_delivery() {
        rb_delivery.isChecked = true;
        rb_tabel.isChecked = false;
        editor.putString(USERB_Delivery_Method_KEY,"delivery")
        editor.apply()

    }

    fun setRb_tabel() {
        rb_delivery.isChecked = false;
        rb_tabel.isChecked = true;
        editor.putString(USERB_Delivery_Method_KEY,"tabel")
        editor.apply()

    }

    fun goTabel() {
        val intent: Intent

        intent = Intent(requireContext(), BOOK_Tabel::class.java)

        startActivityForResult(intent, 1)

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == 1) {
            if (resultCode == Activity.RESULT_OK) {
                val num_people = data!!.getIntExtra("num_people", 0)
                val tabel_id = data.getIntExtra("tabel_id", 0)
                val time = data.getStringExtra("time")
                val date = data.getStringExtra("date")
                val note = data.getStringExtra("note")

                Toast.makeText(
                    requireContext(),
                    "num_people:" + num_people + "\n" + "tabel_id:${tabel_id}\ntime:${time}\ndate:${date}\nnote:${note}\n",
                    Toast.LENGTH_LONG
                ).show()









            }
            if (resultCode == Activity.RESULT_CANCELED) {
                //Write your code if there's no result
                Toast.makeText(requireContext(), "not book table", Toast.LENGTH_LONG).show()








            }
        }
    }//onActivityResult

    private fun getsubToatal() {

        val call: Call<mOrder> = ApiClient.getClient.getTotalPriceorder(
            sharedPref.getInt(USERID_KEY, 0),
            sharedPref.getInt(USERORDERID_KEY, 0)
        )
        call.enqueue(object : Callback<mOrder> {

            override fun onResponse(call: Call<mOrder>?, response: Response<mOrder>?) {

                val myorder: mOrder = response!!.body()!!

                txtSuptotal.text = myorder.totalPrice
                txtTotal.text= (myorder.totalPrice!!.toFloat()+5.00f).toString()
                editor.putString(USERB_txtSuptotal_KEY,txtSuptotal.text.toString())
                editor.putString(USERB_txtTotal_KEY,txtTotal.text.toString())
                editor.apply()



            }

            override fun onFailure(call: Call<mOrder>?, t: Throwable?) {
                Log.i("onFailure:", t!!.message.toString())
            }

        })
    }

}
