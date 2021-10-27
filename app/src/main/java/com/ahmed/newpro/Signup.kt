package com.ahmed.newpro

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.text.SpannableString
import android.text.Spanned
import android.text.TextUtils
import android.text.style.ForegroundColorSpan
import android.util.Log
import android.view.View
import android.view.WindowManager
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.ahmed.newpro.API.ApiClient
import com.ahmed.newpro.Model.mUser
import com.pranavpandey.android.dynamic.toasts.DynamicToast
import kotlinx.android.synthetic.main.activity_signup.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class Signup : AppCompatActivity() {
    lateinit var txtFullName: EditText
    lateinit var txtEmail: EditText
    lateinit var txtPassword: EditText
    lateinit var txtPhone: EditText


    override fun onCreate(savedInstanceState: Bundle?) {
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)




        txtFullName = findViewById(R.id.full_name)
        txtEmail = findViewById(R.id.email)
        txtPassword = findViewById(R.id.password)
        txtPhone = findViewById(R.id.singup_phone)

        val ctex3 = signup_text.text.toString();

        signup_text.text = subtextcolorText3(ctex3);
    }

    fun subtextcolorText3(text: String): SpannableString {
        val mSpannableString = SpannableString(text)
        val g1 = ForegroundColorSpan(ContextCompat.getColor(this, R.color.colorPrimary));
        val g2 = ForegroundColorSpan(ContextCompat.getColor(this, R.color.colorPrimary));
        mSpannableString.setSpan(g1, 0, 1, Spanned.SPAN_INCLUSIVE_EXCLUSIVE)
        mSpannableString.setSpan(g2, 5, 6, Spanned.SPAN_INCLUSIVE_EXCLUSIVE)


        return mSpannableString;

    }

    fun Account_login(view: View) {

        val intent: Intent;
        intent = Intent(this, login::class.java)
        startActivity(intent)


    }

    fun go_tologin(view: View) {

        saveUser()


    }


    private fun saveUser() {

        if (TextUtils.isEmpty(txtFullName.text)) {
            txtFullName.setError("Full Name required")
            txtFullName.requestFocus()
            return

        }
        if (TextUtils.isEmpty(txtEmail.text)) {
            txtEmail.setError("email required")
            txtEmail.requestFocus()
            return

        }
        if (TextUtils.isEmpty(txtPhone.text)) {
            txtPhone.setError("Phone required")
            txtPhone.requestFocus()
            return

        }
        if (TextUtils.isEmpty(txtPassword.text)) {
            txtPassword.setError("Password required")
            txtPassword.requestFocus()
            return

        }


        val user = mUser(
            txtEmail.text.toString(),
            txtFullName.text.toString(),
            txtPassword.text.toString(),
            txtPhone.text.toString()
        )

        val call: Call<mUser> = ApiClient.getClient.singUp(user)
        call.enqueue(object : Callback<mUser> {

            override fun onResponse(call: Call<mUser>?, response: Response<mUser>?) {

                if (response!!.body()!!.error == true) {


                    DynamicToast.makeError(baseContext, "" + response!!.body()!!.msg).show();


                } else {


                    DynamicToast.makeSuccess(baseContext, "" + response!!.body()!!.msg).show();

                    val intent = Intent(baseContext, com.ahmed.newpro.login::class.java)
                    startActivity(intent)
                    finish()


                }


            }

            override fun onFailure(call: Call<mUser>?, t: Throwable?) {
                Log.i("onFailure:", t!!.message.toString())
            }

        })
    }


}
