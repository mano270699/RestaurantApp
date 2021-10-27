package com.ahmed.newpro

import android.content.*
import android.os.Bundle
import android.text.SpannableString
import android.text.Spanned
import android.text.TextUtils
import android.text.style.ForegroundColorSpan
import android.util.Log
import android.view.View
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.ahmed.newpro.API.ApiClient
import com.ahmed.newpro.Model.mUser
import com.pranavpandey.android.dynamic.toasts.DynamicToast
import kotlinx.android.synthetic.main.activity_edit_text.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class edit_text : AppCompatActivity() {
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


    lateinit var name_text: EditText
    lateinit var address_text: EditText
    lateinit var phone: EditText


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_text)

        val intentFilter = IntentFilter()
        intentFilter.addAction("com.package.ACTION_LOGOUT")
        registerReceiver(object : BroadcastReceiver() {
            override fun onReceive(context: Context?, intent: Intent?) {
                Log.d("onReceive", "Logout in progress")
                //At this point you should start the login activity and finish this one
                finish()
            }
        }, intentFilter)
        val ctex1 = address_title.text.toString();
        address_title.text = subtextcolorText1(ctex1);


        name_text=findViewById(R.id.name_text)
        address_text=findViewById(R.id.address_text)
        phone=findViewById(R.id.phone)

        //SET SHARDpRES
        sharedPref = getSharedPreferences(PREF_NAME, PRIVATE_MODE)

        editor = sharedPref.edit()
        editor.apply()

        name_text.setText(sharedPref.getString(USERName_KEY, ""))
        address_text.setText(sharedPref.getString(USERAddress_KEY, ""))
        phone.setText(sharedPref.getString(USERPhone_KEY, ""))


        name_text.setEnabled(false);
        phone.setEnabled(false);







    }

    fun gotomanageSetting(view: View) {
        val intent = Intent(this, shaping_cart::class.java)
        startActivity(intent)

    }

    fun subtextcolorText1(text: String): SpannableString {
        val mSpannableString = SpannableString(text)
        val g1 = ForegroundColorSpan(ContextCompat.getColor(this, R.color.colorPrimary));
        val g2 = ForegroundColorSpan(ContextCompat.getColor(this, R.color.colorPrimary));
        mSpannableString.setSpan(g1, 0, 1, Spanned.SPAN_INCLUSIVE_EXCLUSIVE)
        mSpannableString.setSpan(g2, 4, 5, Spanned.SPAN_INCLUSIVE_EXCLUSIVE)


        return mSpannableString;

    }


    private fun setNewDataCustomer() {


            val user = mUser(
                0,
                "email",
                "fullName",
                "password",
                address_text.text.toString(),
                "birthDate",
                "phone",
                "cridetCard",
                "image",
                0
            )

            val call: Call<mUser> = ApiClient.getClient.updateAddressCustomer(
                sharedPref.getInt(
                    USERID_KEY,
                    0
                ), user
            )
            call.enqueue(object : Callback<mUser> {

                override fun onResponse(
                    call: Call<mUser>?,
                    response: Response<mUser>?
                ) {
                    if (response!!.isSuccessful()) {
                        var myUSER: mUser = response.body()!!

                        editor.putString(USERAddress_KEY, address_text.text.toString())
                        editor.apply()


                        DynamicToast.makeSuccess(
                            baseContext,
                            "Your address has been updated"
                        )
                            .show();

                    } else {

                        DynamicToast.makeError(
                            baseContext,
                            "can not add  address now try letter."
                        )
                            .show();

                    }

                }

                override fun onFailure(call: Call<mUser>?, t: Throwable?) {
                    Log.i("onFailure:", t!!.message.toString())
                    DynamicToast.makeError(
                        baseContext,
                        "can not add  address now try letter."
                    )
                        .show();

                }

            })


    }

    fun saveAddress(view: View) {
        if (TextUtils.isEmpty(address_text.text)) {
            address_text.setError("Address required*")
            address_text.requestFocus()
            return

        }else {
            setNewDataCustomer()
            finish()
        }

    }

    fun openMap(view: View) {
        finish()
        val intent = Intent(this, MapsActivity::class.java)
        this.startActivity(intent)


    }

}
