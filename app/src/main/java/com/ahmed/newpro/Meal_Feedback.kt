package com.ahmed.newpro

import android.annotation.SuppressLint
import android.content.*
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.SpannableString
import android.text.Spanned
import android.text.style.ForegroundColorSpan
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.ahmed.newpro.API.ApiClient
import com.ahmed.newpro.Model.mFeedback
import com.ahmed.newpro.Model.mTabelOrder
import com.pranavpandey.android.dynamic.toasts.DynamicToast
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_meal__feedback.*
import kotlinx.android.synthetic.main.activity_meal_details.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*

class Meal_Feedback : AppCompatActivity() {
    var meal_id: Int = 0
    var meal_image: String = ""
   lateinit var ratingbar : RatingBar;
   lateinit var TextFeedback : EditText;

    private var PRIVATE_MODE = 0
    private val PREF_NAME = "userInfo"
    lateinit var sharedPref: SharedPreferences
    lateinit var editor: SharedPreferences.Editor;
    var USERID_KEY = "UserID"

    @SuppressLint("CommitPrefEdits")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_meal__feedback)

        val intentFilter = IntentFilter()
        intentFilter.addAction("com.package.ACTION_LOGOUT")
        registerReceiver(object : BroadcastReceiver() {
            override fun onReceive(context: Context?, intent: Intent?) {
                Log.d("onReceive", "Logout in progress")
                //At this point you should start the login activity and finish this one
                finish()
            }
        }, intentFilter)

        ratingbar=findViewById(R.id.ratBar3);
        TextFeedback=findViewById(R.id.TextFeedback);










        //SET SHARDpRES
        sharedPref = getSharedPreferences(PREF_NAME, PRIVATE_MODE)

        editor = sharedPref.edit()
        editor.apply()





        val ctex4 = tvmeal_feedback.text.toString();

        tvmeal_feedback.text = subtextcolorText3(ctex4);
        val ctex5 = tvRate_feedback.text.toString();

        tvRate_feedback.text = subtextcolorText4(ctex5);


        val feedback_meal_image: ImageView = findViewById(R.id.feedback_meal_image)
        val meal_tv_title: TextView = findViewById(R.id.tvmeal_feedback)
        val rate_nums: TextView = findViewById(R.id.meal_feed_num_rates)

        var price: TextView = findViewById(R.id.feed_meal_price)


        val i = intent

        meal_id = i.getIntExtra("id", 0)

        meal_image = i.getStringExtra("urlImage")
        rate_nums.text = i.getStringExtra("num_rate")
        price.text = i.getStringExtra("meal_price")
        meal_image = i.getStringExtra("urlImage")

        meal_tv_title.text = i.getStringExtra("title")




        Picasso.get()
            .load(ApiClient.BASE_URL + "image/Meal_images/" + meal_image)
            .placeholder(R.drawable.loading)
            .error(R.drawable.no_image)
            .into(feedback_meal_image);
    }

    fun subtextcolorText3(text: String): SpannableString {
        val mSpannableString = SpannableString(text)
        val g1 = ForegroundColorSpan(ContextCompat.getColor(this, R.color.colorPrimary));
        val g2 = ForegroundColorSpan(ContextCompat.getColor(this, R.color.colorPrimary));
        mSpannableString.setSpan(g1, 0, 1, Spanned.SPAN_INCLUSIVE_EXCLUSIVE)
        mSpannableString.setSpan(g2, 9, 10, Spanned.SPAN_INCLUSIVE_EXCLUSIVE)


        return mSpannableString;

    }

    fun subtextcolorText4(text: String): SpannableString {
        val mSpannableString = SpannableString(text)
        val g1 = ForegroundColorSpan(ContextCompat.getColor(this, R.color.colorPrimary));
        val g2 = ForegroundColorSpan(ContextCompat.getColor(this, R.color.colorPrimary));
        mSpannableString.setSpan(g1, 0, 1, Spanned.SPAN_INCLUSIVE_EXCLUSIVE)
        mSpannableString.setSpan(g2, 5, 6, Spanned.SPAN_INCLUSIVE_EXCLUSIVE)


        return mSpannableString;

    }

    fun gomeal(view: View) {


        finish()
    }




    fun saveFeedback(){


       var save= mFeedback(sharedPref.getInt(USERID_KEY, 0),meal_id,TextFeedback.text.toString(),ratingbar.rating.toInt());

        val call: Call<mFeedback> = ApiClient.getClient.addfeedback(save)
        call.enqueue(object : Callback<mFeedback> {

            override fun onResponse(call: Call<mFeedback>, response: Response<mFeedback>) {
                Log.i("savfeebck",response.body().toString())
                Log.i("savfeebck",response.toString())
                Log.i("savfeebck_comment",save.comment)
                Log.i("savfeebck_meal_id",save.meal_id.toString())
                Log.i("savfeebck_rate",save.rate.toString())
                Log.i("savfeebck_customer_id",save.cutomerId.toString())

                if (response.isSuccessful()) {


                    DynamicToast.makeSuccess(baseContext, " Your Rate Added :)").show();
                    TextFeedback.setText("");
                    ratingbar.setRating(0f);

                    finish();
                    startActivity(getIntent());
                } else {

                    DynamicToast.makeError(baseContext, "can not add this Comment now try letter.")
                        .show();

                }


            }

            override fun onFailure(call: Call<mFeedback>?, t: Throwable?) {
                Log.i("onFailure:", t!!.message.toString())

            }

        })

    }

    fun sendReview(view: View) {
        saveFeedback();
    }
}
