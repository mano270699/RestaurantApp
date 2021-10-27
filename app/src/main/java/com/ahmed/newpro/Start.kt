package com.ahmed.newpro

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.content.res.Configuration
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.os.Bundle
import android.text.SpannableString
import android.text.Spanned
import android.text.style.ForegroundColorSpan
import android.util.Log
import android.view.View
import android.view.WindowManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.ahmed.newpro.API.ApiClient
import com.ahmed.newpro.Adapter.NotificationHelper.displayNotification
import com.ahmed.newpro.Model.customer_order
import com.ahmed.newpro.Model.mOrder
import com.ahmed.newpro.Model.mUser
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.messaging.FirebaseMessaging
import com.pranavpandey.android.dynamic.toasts.DynamicToast
import kotlinx.android.synthetic.main.activity_start.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*


class Start : AppCompatActivity() {

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


    @SuppressLint("CommitPrefEdits")
    override fun onCreate(savedInstanceState: Bundle?) {


        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )

        super.onCreate(savedInstanceState)
        //SET SHARDpRES
        sharedPref = getSharedPreferences(PREF_NAME, PRIVATE_MODE)

        editor = sharedPref.edit()
        editor.apply()

        setLanguage(sharedPref.getString(USERlanguage_KEY, "en"))
        setContentView(R.layout.activity_start)


        val ctex3 = textstart.text.toString();

        textstart.text = subtextcolorText3(ctex3);



        getCuurentOrderID()
    }

    fun goMain(view: View) {

        Log.i("goMain:", "Before if")
        if(isNetworkAvailable(this)) {
            if (sharedPref.getInt(USERID_KEY, 0) != 0) {
                Log.i("goMain:", "in if")
                checkUser()
            } else {
                Log.i("goMain:", "in else")

                val intent: Intent
                intent = Intent(this, onBoardering::class.java)
                startActivity(intent)
            }

        }else{
                    DynamicToast.makeError(baseContext,   "Check Ur internet Connection!")
                        .show();
  }
        }





    fun subtextcolorText3(text: String): SpannableString {
        val mSpannableString = SpannableString(text)
        val g1 = ForegroundColorSpan(ContextCompat.getColor(this, R.color.colorPrimary));
        val g2 = ForegroundColorSpan(ContextCompat.getColor(this, R.color.colorPrimary));
        mSpannableString.setSpan(g1, 0, 1, Spanned.SPAN_INCLUSIVE_EXCLUSIVE)
        mSpannableString.setSpan(g2, 11, 12, Spanned.SPAN_INCLUSIVE_EXCLUSIVE)


        return mSpannableString;

    }

    private fun checkUser() {


        val call: Call<mUser> = ApiClient.getClient.getUserById(sharedPref.getInt(USERID_KEY, 0))
        call.enqueue(object : Callback<mUser> {

            override fun onResponse(call: Call<mUser>?, response: Response<mUser>?) {
                Log.i("checkUser:", "before Successful")
                if (response!!.isSuccessful()) {

                    var myuser: mUser = response!!.body()!!

                    Log.i("checkUser:", "in  Successful")
                    if (myuser.blocked == 1) {
                        DynamicToast.makeWarning(baseContext, "Your account Blocked").show();
                        editor.remove(USERID_KEY);
                        editor.remove(USERName_KEY);
                        editor.remove(USEREmail_KEY);
                        editor.remove(USERAddress_KEY);
                        editor.remove(USERImage_KEY);
                        editor.remove(USERPhone_KEY);
                        editor.remove(USERBDate_KEY);
                        editor.commit();
                        Log.i("checkUser:", "in  block")

                    } else {

                        editor.putInt(USERID_KEY, myuser.customerId!!)
                        editor.putString(USEREmail_KEY, myuser.email)
                        editor.putString(USERName_KEY, myuser.fullName)
                        editor.putString(USERAddress_KEY, myuser.address)
                        editor.putString(USERImage_KEY, myuser.image)
                        editor.putString(USERPhone_KEY, myuser.phone)
                        editor.putString(USERBDate_KEY, myuser.birthDate)
                        editor.apply()
                        Log.i("checkUser:", "in unblock Successful")
                        val intent = Intent(baseContext, MainActivity::class.java)
                        startActivity(intent)
                        finish()
                    }


                } else {
                    DynamicToast.makeError(baseContext, "erro:" + "Your account not exist :(")
                        .show();
                    editor.remove(USERID_KEY);
                    editor.remove(USERName_KEY);
                    editor.remove(USEREmail_KEY);
                    editor.remove(USERAddress_KEY);
                    editor.remove(USERImage_KEY);
                    editor.remove(USERPhone_KEY);
                    editor.remove(USERBDate_KEY);
                    editor.commit();
                    val intent = Intent(baseContext, onBoardering::class.java)
                    startActivity(intent)

                }

            }

            override fun onFailure(call: Call<mUser>?, t: Throwable?) {
                Log.i("onFailure:", t!!.message.toString())
                Log.i("checkUser:", t!!.message.toString())
            }

        })
    }
    fun setLanguage(lang: String?) {
        val locale_new = Locale(lang)
        Locale.setDefault(locale_new)
        val res = this.resources
        val newConfig = Configuration(res.configuration)
        newConfig.locale = locale_new
        newConfig.setLayoutDirection(locale_new)
        res.updateConfiguration(newConfig, res.displayMetrics)
        //Save Language  in   Shared Preferences
        sharedPref = getSharedPreferences(PREF_NAME, PRIVATE_MODE)

        editor = sharedPref.edit()

        editor.putString(USERlanguage_KEY, lang)

        editor.apply()


    }
    private fun getCuurentOrderID() {

        val call: Call<customer_order> =
            ApiClient.getClient.getCurrentOrderID(sharedPref.getInt(USERID_KEY, 0))
        call.enqueue(object : Callback<customer_order> {

            override fun onResponse(
                call: Call<customer_order>?,
                response: Response<customer_order>?
            ) {
                if (response!!.isSuccessful) {

                    editor.putInt(USERORDERID_KEY, response.body()!!.orderId!!);
                    editor.apply()
                    Log.i(USERORDERID_KEY, response.body()!!.orderId!!.toString())
                } else {
                    editor.putInt(USERORDERID_KEY, 0);
                    Log.i(USERORDERID_KEY, "0")
                }


            }

            override fun onFailure(call: Call<customer_order>?, t: Throwable?) {
                Log.i("onFailure:", t!!.message.toString())
            }

        })
    }
    companion object {

        private const val TAG = "MyFirebaseMsgService"
    }
    fun saveToken(userID:Int,token:String){

        val tokens = mUser(token)
        val call: Call<mUser> =
            ApiClient.getClient.updateUsertoken(userID, tokens)
        call.enqueue(object : Callback<mUser> {

            override fun onResponse(
                call: Call<mUser>?,
                response: Response<mUser>?
            ) {
                if (response!!.isSuccessful) {
                    DynamicToast.makeSuccess(baseContext, "Token created Done :)" ).show();


                } else {
                    DynamicToast.makeError(baseContext, "erro:" + response!!.body()!!.msg).show();

                             }


            }

            override fun onFailure(call: Call<mUser>, t: Throwable) {
                Log.i("onFailure:", t!!.message.toString())
            }

        })
    }

    fun isNetworkAvailable(context: Context?): Boolean {
        if (context == null) return false
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            val capabilities = connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
            if (capabilities != null) {
                when {
                    capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> {
                        return true
                    }
                    capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> {
                        return true
                    }
                    capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> {
                        return true
                    }
                }
            }
        } else {
            val activeNetworkInfo = connectivityManager.activeNetworkInfo
            if (activeNetworkInfo != null && activeNetworkInfo.isConnected) {
                return true
            }
        }
        return false
    }

}
