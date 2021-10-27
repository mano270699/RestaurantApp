package com.ahmed.newpro


import android.Manifest
import android.app.Activity
import android.content.*
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.net.Uri
import android.os.Build.VERSION
import android.os.Build.VERSION_CODES
import android.os.Bundle
import android.util.Base64
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.ahmed.newpro.API.ApiClient
import com.ahmed.newpro.Model.mUser
import com.pranavpandey.android.dynamic.toasts.DynamicToast
import com.squareup.picasso.Picasso
import de.hdodenhof.circleimageview.CircleImageView
import kotlinx.android.synthetic.main.activity_acount_setting.*
import okhttp3.MediaType
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import java.io.ByteArrayOutputStream
import java.io.File


class acount_setting : AppCompatActivity() {

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


    lateinit var profile_image: CircleImageView
    lateinit var t_name: TextView
    lateinit var t_mail: TextView

    lateinit var name_text: EditText
    lateinit var Email_text: EditText
    lateinit var phone: EditText


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_acount_setting)

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

        profile_image = findViewById(R.id.profile_image) as CircleImageView

        t_name = findViewById(R.id.t_name) as TextView
        t_mail = findViewById(R.id.t_mail) as TextView


        name_text = findViewById(R.id.name_text) as EditText
        Email_text = findViewById(R.id.Email_text) as EditText
        phone = findViewById(R.id.phone) as EditText

        t_name.text=sharedPref.getString(USERName_KEY, "")
        t_mail.text=sharedPref.getString(USEREmail_KEY, "")


        name_text.setText(sharedPref.getString(USERName_KEY, ""))
        Email_text.setText(sharedPref.getString(USEREmail_KEY, ""))
        phone.setText(sharedPref.getString(USERPhone_KEY, ""))

        Picasso.get()
            .load(R.drawable.u)
            .placeholder(R.drawable.loading)
            .error(R.drawable.u)
            .into(profile_image);


    }








    fun goback(view: View) {

        val intent = Intent(this, MainActivity::class.java)
        this.startActivity(intent)

    }


    private fun setNewDataCustomer() {





        val user = mUser(
            Email_text.text.toString(),
            name_text.text.toString(),
            phone.text.toString()
        )

        val call: Call<mUser> = ApiClient.getClient.updateUserData(
            sharedPref.getInt(USERID_KEY, 0),
            user
        )
        call.enqueue(object : Callback<mUser> {

            override fun onResponse(
                call: Call<mUser>?,
                response: Response<mUser>?
            ) {
                if (response!!.isSuccessful()) {


                    editor.putString(USEREmail_KEY, Email_text.text.toString())
                    editor.putString(USERName_KEY, name_text.text.toString())
                    editor.putString(USERPhone_KEY, phone.text.toString())
                    editor.apply()

                    val image: mUser? = response.body()
                    DynamicToast.makeSuccess(
                        baseContext,
                        "Your Data has been updated"
                    )
                        .show();



                } else {

                    DynamicToast.makeError(
                        baseContext,
                        "can not updated your data  now try letter."
                    )
                        .show();

                }

            }

            override fun onFailure(call: Call<mUser>?, t: Throwable?) {

                DynamicToast.makeError(
                    baseContext,
                    "can not updated your data  now try letter.onFailure"
                )
                    .show();

            }

        })

    }

    fun SaveData(view: View) {

        setNewDataCustomer()
    }




}
