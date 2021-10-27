package com.ahmed.newpro

import android.opengl.Visibility
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.view.WindowManager
import android.widget.EditText
import android.widget.ProgressBar
import com.ahmed.newpro.API.ApiClient
import com.ahmed.newpro.Model.mUser
import com.pranavpandey.android.dynamic.toasts.DynamicToast
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class lostPassword : AppCompatActivity() {
    lateinit var txtEmail: EditText
    lateinit var loading: ProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lost_password)

        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )

        txtEmail = findViewById(R.id.login_email)
        loading = findViewById(R.id.loading)
    }


    fun restPass(view: View) {
        loading.visibility= View.VISIBLE
        sendRestPasswordEmail()
    }


    private fun sendRestPasswordEmail() {


        if (TextUtils.isEmpty(txtEmail.text)) {
            txtEmail.setError("email required")
            txtEmail.requestFocus()
            return

        }


        val user = mUser(
            txtEmail.text.toString()


        )

        val call: Call<mUser> = ApiClient.getClient.lostPassword(user)
        call.enqueue(object : Callback<mUser> {

            override fun onResponse(call: Call<mUser>?, response: Response<mUser>?) {
                if (response!!.isSuccessful()) {
                    loading.visibility= View.INVISIBLE

                    DynamicToast.makeSuccess(baseContext, "Check Your Email" )
                        .show();

                } else {
                    loading.visibility= View.INVISIBLE
                    DynamicToast.makeError(baseContext, "erro:" + "Your account not exist :(")
                        .show();

                }

            }

            override fun onFailure(call: Call<mUser>?, t: Throwable?) {
                Log.i("onFailure:", t!!.message.toString())
                loading.visibility= View.INVISIBLE
                DynamicToast.makeSuccess(baseContext, "Check Your Email" )
                    .show();
            }

        })

    }
}
