package com.ahmed.newpro

import android.annotation.SuppressLint
import android.content.*
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.RelativeLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContentProviderCompat.requireContext
import com.ahmed.newpro.API.ApiClient
import com.ahmed.newpro.Model.mOrder
import com.ahmed.newpro.Model.mOrder_details
import com.ahmed.newpro.Model.mTabelOrder
import com.pranavpandey.android.dynamic.toasts.DynamicToast
import kotlinx.android.synthetic.main.dialog_confirm.*
import kotlinx.android.synthetic.main.dialog_confirm.view.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class Checkout : AppCompatActivity() {
    lateinit var btn_chechout: Button
    lateinit var btn_delivery_toolbar: TextView
    lateinit var btn_payment_toolbar: TextView
    lateinit var btn_summary_toolbar: TextView

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
    var USERlanguage_KEY = "Language"


    var USERB_txtSuptotal_KEY = "txtSuptotal"
    var USERB_txtTotal_KEY = "txtTotal"
    var USERB_Payment_Method_KEY = "Payment_Method"
    var USERB_Delivery_Method_KEY = "Delivery_Method"

    var USER_num_people_mytabel_KEY = "numpeoplemytabel"
    var USER_tabel_id_mytabel_KEY = "tabel_id_mytabel"
    var USER_time_mytabel_KEY = "timemytabel"
    var USERB_date_mytabel_KEY = "date_mytabel"
    var USER_note_mytabel_KEY = "note_mytabel"


    @SuppressLint("ResourceType")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_checkout)

        val intentFilter = IntentFilter()
        intentFilter.addAction("com.package.ACTION_LOGOUT")
        registerReceiver(object : BroadcastReceiver() {
            override fun onReceive(context: Context?, intent: Intent?) {
                Log.d("onReceive", "Logout in progress")
                //At this point you should start the login activity and finish this one
                finish()
            }
        }, intentFilter)


        btn_chechout = findViewById(R.id.btn_chechout)
        btn_delivery_toolbar = findViewById(R.id.btn_delivery_toolbar)
        btn_payment_toolbar = findViewById(R.id.btn_payment_toolbar)
        btn_summary_toolbar = findViewById(R.id.btn_summary_toolbar)

        //SET SHARDpRES
        sharedPref = getSharedPreferences(PREF_NAME, PRIVATE_MODE)

        editor = sharedPref.edit()
        editor.apply()

        val fm = supportFragmentManager
        val ft = fm.beginTransaction()
        val fb = fr_delivery()
        ft.replace(R.id.fr_ly, fb)
        //ft.addToBackStack(null)
        ft.commit()

        btn_chechout.setOnClickListener(View.OnClickListener {




                when (btn_chechout.text) {


                    "PAYMENT" -> goPayment()
                    "SUMMARY" -> goSummary()
                    "CONFIRM" -> goConfirm()

                    "ادفع" -> goPayment()
                    "تقرير" -> goSummary()
                    "تأكيد" -> goConfirm()
                }

        })

    }

    fun gotocart(view: View) {
        val intent = Intent(this, com.ahmed.newpro.shaping_cart::class.java)
        this.startActivity(intent)


    }


    @SuppressLint("ResourceAsColor")
    fun goPayment() {
        if(sharedPref.getString(USERAddress_KEY, "Nan")==null|| sharedPref.getString(USERAddress_KEY, "Nan")=="Nan"){

            DynamicToast.makeError(baseContext, "You can set address first choose change to change it ")
                .show();
        }else {

           if(sharedPref.getString(USERlanguage_KEY, "")=="en") {
               val fm = supportFragmentManager
               val ft = fm.beginTransaction()
               val fb = fr_payment()
               ft.replace(R.id.fr_ly, fb)
               //ft.addToBackStack(null)
               ft.commit()
               btn_chechout.text = "SUMMARY"

               btn_delivery_toolbar.setBackgroundResource(R.drawable.rec__btn_gray)
               btn_delivery_toolbar.setTextColor(Color.parseColor("#3E5474"));

               btn_payment_toolbar.setBackgroundResource(R.drawable.rec_btn)
               btn_payment_toolbar.setTextColor(Color.parseColor("#ffffff"));
           }else{
               val fm = supportFragmentManager
               val ft = fm.beginTransaction()
               val fb = fr_payment()
               ft.replace(R.id.fr_ly, fb)
               //ft.addToBackStack(null)
               ft.commit()
               btn_chechout.text = "تقرير"

               btn_delivery_toolbar.setBackgroundResource(R.drawable.rec__btn_gray)
               btn_delivery_toolbar.setTextColor(Color.parseColor("#3E5474"));

               btn_payment_toolbar.setBackgroundResource(R.drawable.rec_btn)
               btn_payment_toolbar.setTextColor(Color.parseColor("#ffffff"));
           }
        }
    }

    @SuppressLint("ResourceAsColor")
    fun goSummary() {



        if(sharedPref.getString(USERlanguage_KEY, "")=="en") {
            val fm = supportFragmentManager
            val ft = fm.beginTransaction()
            val fb = fr_summary()
            ft.replace(R.id.fr_ly, fb)
            // ft.addToBackStack(null)
            ft.commit()
            btn_chechout.text = "CONFIRM"



            btn_payment_toolbar.setBackgroundResource(R.drawable.rec__btn_gray)
            btn_payment_toolbar.setTextColor(Color.parseColor("#3E5474"));

            btn_summary_toolbar.setBackgroundResource(R.drawable.rec_btn)
            btn_summary_toolbar.setTextColor(Color.parseColor("#ffffff"));
        }else{
            val fm = supportFragmentManager
            val ft = fm.beginTransaction()
            val fb = fr_summary()
            ft.replace(R.id.fr_ly, fb)
            // ft.addToBackStack(null)
            ft.commit()
            btn_chechout.text = "تأكيد"



            btn_payment_toolbar.setBackgroundResource(R.drawable.rec__btn_gray)
            btn_payment_toolbar.setTextColor(Color.parseColor("#3E5474"));

            btn_summary_toolbar.setBackgroundResource(R.drawable.rec_btn)
            btn_summary_toolbar.setTextColor(Color.parseColor("#ffffff"));
        }

    }

    fun goConfirm() {

        if (sharedPref.getInt(USER_num_people_mytabel_KEY, 0) != 0) {
            saveTabel();
            confirmOrder();
        } else {

            confirmOrder()
        }


    }


    private fun saveTabel() {


        val saveTabel = mTabelOrder(
            sharedPref.getInt(USERID_KEY, 0)
            ,sharedPref.getInt(USER_tabel_id_mytabel_KEY, 0)
            ,sharedPref.getInt(USER_num_people_mytabel_KEY, 0)
            ,sharedPref.getString(USERB_date_mytabel_KEY, "Nan")
            ,sharedPref.getString(USER_time_mytabel_KEY, "Nan")
            ,sharedPref.getString(USER_note_mytabel_KEY,"Nan")
            ,sharedPref.getInt(USERORDERID_KEY, 0)
        )

        val call: Call<mTabelOrder> = ApiClient.getClient.addOrderToTabel(saveTabel)
        call.enqueue(object : Callback<mTabelOrder> {

            override fun onResponse(
                call: Call<mTabelOrder>?,
                response: Response<mTabelOrder>?
            ) {
                if (response!!.isSuccessful()) {


                } else {

                    DynamicToast.makeError(baseContext, "can not add this meal now try letter.")
                        .show();

                }

            }

            override fun onFailure(call: Call<mTabelOrder>?, t: Throwable?) {
                Log.i("onFailure:", t!!.message.toString())

            }

        })

    }

    private fun confirmOrder() {


        val saveOrder = mOrder(
            sharedPref.getString(USERB_Payment_Method_KEY, "Nan"),
            sharedPref.getString(USERB_Delivery_Method_KEY, "Nan")
        )

        val call: Call<mOrder> =
            ApiClient.getClient.updateisOrder(sharedPref.getInt(USERORDERID_KEY, 0), saveOrder)
        call.enqueue(object : Callback<mOrder> {

            override fun onResponse(
                call: Call<mOrder>?,
                response: Response<mOrder>?
            ) {
                if (response!!.isSuccessful()) {

                    showDialogConfirmOrder()

                } else {

                    DynamicToast.makeError(baseContext, "can not add this order now try letter.")
                        .show();

                }

            }

            override fun onFailure(call: Call<mOrder>?, t: Throwable?) {
                Log.i("onFailure:", t!!.message.toString())

            }

        })

    }

    private fun showDialogConfirmOrder() {

        val mbulder = AlertDialog.Builder(this)
        val myview: View = LayoutInflater.from(this).inflate(R.layout.dialog_confirm, null);

        mbulder.setView(myview)

        mbulder.show().window?.setBackgroundDrawableResource(R.drawable.rec_dilog_ly)



        myview.btn_ok.setOnClickListener(View.OnClickListener {

            val intent = Intent(this, orders::class.java)
            this.startActivity(intent)
            finish()

        })

        editor.remove(USERORDERID_KEY);
        editor.remove(USERB_txtSuptotal_KEY);
        editor.remove(USERB_txtTotal_KEY);
        editor.remove(USERB_Payment_Method_KEY);
        editor.remove(USERB_Delivery_Method_KEY);
        editor.remove(USER_num_people_mytabel_KEY);
        editor.remove(USER_tabel_id_mytabel_KEY);
        editor.remove(USER_time_mytabel_KEY);
        editor.remove(USERB_date_mytabel_KEY);
        editor.remove(USER_note_mytabel_KEY);
        editor.commit();
    }
}
