package com.ahmed.newpro

import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.SpannableString
import android.text.Spanned
import android.text.TextUtils
import android.text.style.ForegroundColorSpan
import android.util.Log
import android.view.View
import android.view.WindowManager
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.ahmed.newpro.API.ApiClient
import com.ahmed.newpro.Model.mUser
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.messaging.FirebaseMessaging
import com.pranavpandey.android.dynamic.toasts.DynamicToast
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.content_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class login : AppCompatActivity() {
    lateinit var txtEmail: EditText
    lateinit var txtPassword: EditText


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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )

        txtEmail = findViewById(R.id.login_email)
        txtPassword = findViewById(R.id.login_password)

        var ecolor=TitleLogin.text.toString();
        TitleLogin.text = subtextcolorText(ecolor);


        sharedPref = getSharedPreferences(PREF_NAME, PRIVATE_MODE)

        editor = sharedPref.edit()
        editor.apply()


    }

    fun gostart(@Suppress("UNUSED_PARAMETER")view: View) {

        loginUser()
    }

    private fun loginUser() {


        if (TextUtils.isEmpty(txtEmail.text)) {
            txtEmail.setError("email required")
            txtEmail.requestFocus()
            return

        }

        if (TextUtils.isEmpty(txtPassword.text)) {
            txtPassword.setError("Password required")
            txtPassword.requestFocus()
            return

        }


        val user = mUser(
            txtEmail.text.toString(),
            txtPassword.text.toString()

        )

        val call: Call<mUser> = ApiClient.getClient.singIn(user)
        call.enqueue(object : Callback<mUser> {

            override fun onResponse(call: Call<mUser>?, response: Response<mUser>?) {

                if (response!!.isSuccessful()) {

                    var myuser: mUser = response.body()!!

                    if (myuser.error== true) {


                        DynamicToast.makeError(baseContext, "erro:" + myuser.msg)
                            .show();


                    } else {


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


                        } else {

                                FirebaseMessaging.getInstance().token.addOnCompleteListener(
                                    OnCompleteListener { task ->
                                    if (!task.isSuccessful) {
                                        //Log.w(Start.TAG, "Fetching FCM registration token failed", task.exception)
                                        return@OnCompleteListener
                                    }

                                    // Get new FCM registration token
                                    val token = task.result

                                    // Log and toast

                                    //Log.d(Start.TAG, token)
                                    Toast.makeText(baseContext, token, Toast.LENGTH_SHORT).show()



                                    saveToken(myuser.customerId!! ,token.toString())




                                })


                            editor.putInt(USERID_KEY, myuser.customerId!!)
                            editor.putString(USEREmail_KEY, myuser.email)
                            editor.putString(USERName_KEY, myuser.fullName)
                            editor.putString(USERAddress_KEY, myuser.address)
                            editor.putString(USERImage_KEY, myuser.image)
                            editor.putString(USERPhone_KEY, myuser.phone)
                            editor.putString(USERBDate_KEY, myuser.birthDate)
                            editor.apply()

                            val intent = Intent(baseContext, MainActivity::class.java)

                            startActivity(intent)

                            finish()
                        }


                    }
                } else {
                    DynamicToast.makeError(baseContext, "erro:" + "Your account not exist :(")
                        .show();

                }

            }

            override fun onFailure(call: Call<mUser>?, t: Throwable?) {
                Log.i("onFailure:", t!!.message.toString())
                DynamicToast.makeError(baseContext, "nFailure:"+t!!.message.toString() )
                    .show();
            }

        })
    }

    fun gotolostPass(@Suppress("UNUSED_PARAMETER")view: View) {

        val intent = Intent(baseContext, lostPassword::class.java)
        baseContext.startActivity(intent)


    }
    fun subtextcolorText(text: String): SpannableString {
        val mSpannableString = SpannableString(text)
        val g1 = ForegroundColorSpan(ContextCompat.getColor(this, R.color.colorPrimary));

        mSpannableString.setSpan(g1, 0, 1, Spanned.SPAN_INCLUSIVE_EXCLUSIVE)



        return mSpannableString;

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




}
