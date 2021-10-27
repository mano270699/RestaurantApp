package com.ahmed.newpro

import android.content.*
import android.graphics.Color
import android.os.Bundle
import android.text.SpannableString
import android.text.Spanned
import android.text.TextUtils
import android.text.style.ForegroundColorSpan
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ahmed.newpro.API.ApiClient
import com.ahmed.newpro.Adapter.SliderAdapterExample
import com.ahmed.newpro.Adapter.feedBackAdapter
import com.ahmed.newpro.Adapter.menuAdapter
import com.ahmed.newpro.Model.*
import com.pranavpandey.android.dynamic.toasts.DynamicToast
import com.smarteist.autoimageslider.IndicatorAnimations
import com.smarteist.autoimageslider.SliderAnimations
import com.smarteist.autoimageslider.SliderView
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_meal_details.*
import kotlinx.android.synthetic.main.activity_start.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class meal_details : AppCompatActivity() {


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


    var meal_id: Int = 0
    var meal_image: String = ""
    lateinit var textTool: TextView;
    lateinit var textTitle: TextView;
    lateinit var textDesc: TextView;
    lateinit var textPrice: TextView;

    lateinit var meal_imageSlider: ImageView;

    //

    lateinit var recyclerView: RecyclerView;
    lateinit var adapter: feedBackAdapter;
    val feeds = ArrayList<mFeedback>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_meal_details)


        val intentFilter = IntentFilter()
        intentFilter.addAction("com.package.ACTION_LOGOUT")
        registerReceiver(object : BroadcastReceiver() {
            override fun onReceive(context: Context?, intent: Intent?) {
                Log.d("onReceive", "Logout in progress")
                //At this point you should start the login activity and finish this one
                finish()
            }
        }, intentFilter)
        //text color

        //SET SHARDpRES
        sharedPref = getSharedPreferences(PREF_NAME, PRIVATE_MODE)

        editor = sharedPref.edit()
        editor.apply()

        textTool = findViewById(R.id.meal_title)
        textTitle = findViewById(R.id.text_title)
        textDesc = findViewById(R.id.meal_desc)
        textPrice = findViewById(R.id.meal_details_meal_price)
        meal_imageSlider = findViewById(R.id.meal_imageSlider)

        //
        val i = intent
        textTool.text = i.getStringExtra("title")
        textTitle.text = i.getStringExtra("title")
        textDesc.text = i.getStringExtra("desc")
        textPrice.text = i.getStringExtra("price")
        meal_id = i.getIntExtra("id", 0)
        meal_image = i.getStringExtra("urlImage")


        Log.i("price:", i.getStringExtra("price"))


        //text color
        val ctex4 = text_title.text.toString();

//        text_title.text = subtextcolorText3(ctex4);
        text_title.text = ctex4;

        Picasso.get()
            .load(ApiClient.BASE_URL + "image/Meal_images/" + meal_image)
            .placeholder(R.drawable.loading)
            .error(R.drawable.no_image)
            .into(meal_imageSlider);

        val ctex3 = tvDesc.text.toString();
        tvDesc.text = subtextcolorText4(ctex3);

        val ctex5 = tvFeedBack.text.toString();
        tvFeedBack.text = subtextcolorText5(ctex5);


        //getting recyclerview from xml
        recyclerView = findViewById(R.id.feed_recycleView) as RecyclerView

        //adding a layoutmanager
        recyclerView.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)

        getDataFeedBack()

    }

    private fun getDataFeedBack() {

        val call: Call<List<mFeedback>> = ApiClient.getClient.getMealsFeed(meal_id)
        call.enqueue(object : Callback<List<mFeedback>> {

            override fun onResponse(
                call: Call<List<mFeedback>>?,
                response: Response<List<mFeedback>>?
            ) {
                if(response!!.body()==null){

                }else {
                    feeds.addAll(response!!.body()!!)
                    //creating our adapter
                    adapter = feedBackAdapter(feeds, baseContext);

                    //now adding the adapter to recyclerview
                    recyclerView.adapter = adapter

                    number_rate.text = """(${adapter.itemCount})"""
                    number_rate2.text = """(${adapter.itemCount})"""
                }

            }

            override fun onFailure(call: Call<List<mFeedback>>?, t: Throwable?) {
                Log.i("onFailure:", t!!.message.toString())
            }

        })
    }

    fun subtextcolorText3(text: String): SpannableString {
        val mSpannableString = SpannableString(text)
        val g1 = ForegroundColorSpan(ContextCompat.getColor(this, R.color.colorPrimary));
        val g2 = ForegroundColorSpan(ContextCompat.getColor(this, R.color.colorPrimary));
        mSpannableString.setSpan(g1, 0, 1, Spanned.SPAN_INCLUSIVE_EXCLUSIVE)
        mSpannableString.setSpan(g2, 6, 7, Spanned.SPAN_INCLUSIVE_EXCLUSIVE)


        return mSpannableString;

    }

    fun subtextcolorText4(text: String): SpannableString {
        val mSpannableString = SpannableString(text)
        val g1 = ForegroundColorSpan(ContextCompat.getColor(this, R.color.colorPrimary));

        mSpannableString.setSpan(g1, 0, 1, Spanned.SPAN_INCLUSIVE_EXCLUSIVE)



        return mSpannableString;

    }

    fun subtextcolorText5(text: String): SpannableString {
        val mSpannableString = SpannableString(text)
        val g1 = ForegroundColorSpan(ContextCompat.getColor(this, R.color.colorPrimary));
        val g2 = ForegroundColorSpan(ContextCompat.getColor(this, R.color.colorPrimary));
        mSpannableString.setSpan(g1, 0, 1, Spanned.SPAN_INCLUSIVE_EXCLUSIVE)
        mSpannableString.setSpan(g2, 9, 10, Spanned.SPAN_INCLUSIVE_EXCLUSIVE)




        return mSpannableString;

    }

    fun goFeedBack(view: View) {


        val intent: Intent
        intent = Intent(this, Meal_Feedback::class.java)
        intent.putExtra("id", meal_id)
        intent.putExtra("title", textTool.text)
        intent.putExtra("urlImage", meal_image)
        intent.putExtra("num_rate", number_rate.text)
        intent.putExtra("meal_price", textPrice.text)
        startActivity(intent)

    }

    fun gomnue(view: View) {

        finish()


    }

    fun addNewMealToCart(view: View) {

        if (sharedPref.getInt(USERORDERID_KEY, 0) == 0)
            addNewOrdertoCart()
        else
            addNewMeal()


    }


    private fun addNewMeal() {


        val newMeal = mOrder_details(sharedPref.getInt(USERORDERID_KEY, 0), meal_id)

        val call: Call<mOrder_details> = ApiClient.getClient.addNewMealOrder(newMeal)
        call.enqueue(object : Callback<mOrder_details> {

            override fun onResponse(
                call: Call<mOrder_details>?,
                response: Response<mOrder_details>?
            ) {
                if (response!!.isSuccessful()) {
                    var myMeal: mOrder_details = response.body()!!


                    if (myMeal.error == true)
                        DynamicToast.makeWarning(baseContext, "" + myMeal.msg)
                            .show();
                    else {
                        myMeal.error == false
                        DynamicToast.makeSuccess(baseContext, "" + myMeal.msg)
                            .show();
                    }


                } else {

                    DynamicToast.makeError(baseContext, "can not add this meal now try letter.")
                        .show();

                }

            }

            override fun onFailure(call: Call<mOrder_details>?, t: Throwable?) {
                Log.i("onFailure:", t!!.message.toString())

            }

        })

    }

    private fun addNewOrdertoCart() {


        val user = mUser(sharedPref.getInt(USERID_KEY, 0))
        Log.i("USERID_INADD_NEW_ORER", user.customerId.toString())
        val call: Call<mUser> = ApiClient.getClient.addNewOrder(user)
        call.enqueue(object : Callback<mUser> {

            override fun onResponse(
                call: Call<mUser>?,
                response: Response<mUser>?
            ) {
                if (response!!.isSuccessful()) {
                    var myOrder: mUser = response.body()!!

                    editor.putInt(USERORDERID_KEY, myOrder.order_id!!)
                    editor.apply()

                    addNewMeal()

                } else {

                    DynamicToast.makeError(
                            baseContext,
                            "can not add this meal to order now try letter."
                        )
                        .show();

                }

            }

            override fun onFailure(call: Call<mUser>?, t: Throwable?) {
                Log.i("onFailure:", t!!.message.toString())

            }

        })

    }


}
