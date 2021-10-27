package com.ahmed.newpro


import android.annotation.SuppressLint
import android.content.*
import android.content.res.Configuration
import android.content.res.Resources
import android.graphics.Color
import android.os.Bundle
import android.text.SpannableString
import android.text.Spanned
import android.text.style.ForegroundColorSpan
import android.util.DisplayMetrics
import android.util.Log
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat.recreate
import androidx.core.content.ContextCompat
import androidx.core.view.GravityCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ahmed.newpro.API.ApiClient
import com.ahmed.newpro.Adapter.SliderAdapterExample
import com.ahmed.newpro.Adapter.SliderAdapterExample2
import com.ahmed.newpro.Adapter.catAdapter
import com.ahmed.newpro.Model.mCat
import com.ahmed.newpro.Model.mMenu
import com.ahmed.newpro.Model.mSlider
import com.google.android.material.navigation.NavigationView
import com.smarteist.autoimageslider.IndicatorAnimations
import com.smarteist.autoimageslider.SliderAnimations
import com.smarteist.autoimageslider.SliderView
import com.squareup.picasso.Picasso
import de.hdodenhof.circleimageview.CircleImageView
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*
import kotlin.collections.ArrayList


class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {
    lateinit var textnav_header: TextView
    lateinit var nav_profile_image: CircleImageView

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

    //

    val cats = ArrayList<mCat>()

    val MostMels = ArrayList<mMenu>()
    val Sliderv = ArrayList<mSlider>()


    lateinit var recyclerView: RecyclerView
    lateinit var adapter: catAdapter
    lateinit var sliderView: SliderView;
    lateinit var sliderView2: SliderView;
    lateinit var adapter2: SliderAdapterExample2;
    lateinit var adapter3: SliderAdapterExample;
    lateinit var navigationView: NavigationView;

    @SuppressLint("WrongConstant")
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        //SET SHARDpRES
        sharedPref = getSharedPreferences(PREF_NAME, PRIVATE_MODE)

        editor = sharedPref.edit()
        editor.apply()

        setLanguage(sharedPref.getString(USERlanguage_KEY, "en"))
        setContentView(R.layout.activity_main);

        setSupportActionBar(toolbar)
        float_action();
        val toggle = ActionBarDrawerToggle(
            this, Drawerlayout, toolbar,
            0, 0
        );



        Drawerlayout.addDrawerListener(toggle);
        toggle.syncState();
        navView.setNavigationItemSelectedListener(this)


        val intentFilter = IntentFilter()
        intentFilter.addAction("com.package.ACTION_LOGOUT")
        registerReceiver(object : BroadcastReceiver() {
            override fun onReceive(context: Context?, intent: Intent?) {
                Log.d("onReceive", "Logout in progress")
                //At this point you should start the login activity and finish this one
                finish()
            }
        }, intentFilter)

        val ctex = textview1.text.toString();
        val ctex2 = textview2.text.toString();
        val ctex3 = textview3.text.toString();
        textview1.text = subtextcolorText1(ctex);
        textview2.text = subtextcolorText2(ctex2);
        textview3.text = subtextcolorText3(ctex3);


        //getting recyclerview from xml





        recyclerView = findViewById(R.id.cat_recycleView) as RecyclerView

        //adding a layoutmanager
        recyclerView.layoutManager = LinearLayoutManager(this, LinearLayout.HORIZONTAL, false)


        //crating an arraylist to store users using the data class user


        getData()
        getmostMealOrdered()
        getSliderview()



Log.i(USERName_KEY, sharedPref.getString(USERName_KEY, "Nan").toString())
Log.i(USERImage_KEY, sharedPref.getString(USERImage_KEY, "Nan").toString())

//        textnav_header.text = sharedPref.getString(USERName_KEY, "Nan").toString()
//
//        Picasso.get()
//            .load(
//                ApiClient.BASE_URL + "image/customer/" + sharedPref.getString(
//                    USERImage_KEY,
//                    "avatar.png"
//                ).toString()
//            )
//            .placeholder(R.drawable.loading)
//            .error(R.drawable.no_image)
//            .into(nav_profile_image);





        val navigationView = findViewById(R.id.navView) as NavigationView
        val headerView = navigationView.getHeaderView(0)
        val navUsername = headerView.findViewById<View>(R.id.textnav_header) as TextView
        val navUserImage =
            headerView.findViewById<View>(R.id.nav_profile_image) as CircleImageView

        navUsername.text = sharedPref.getString(USERName_KEY, "Nan").toString()
        Picasso.get()
            .load(R.drawable.u)
            .placeholder(R.drawable.loading)
            .error(R.drawable.u)
            .into(navUserImage);





    }


    private fun getData() {

        val call: Call<List<mCat>> = ApiClient.getClient.getCategorys()
        call.enqueue(object : Callback<List<mCat>> {

            override fun onResponse(call: Call<List<mCat>>?, response: Response<List<mCat>>?) {

                cats.addAll(response!!.body()!!)
                //creating our adapter
                adapter = catAdapter(cats, baseContext);

                //now adding the adapter to recyclerview
                recyclerView.adapter = adapter


            }

            override fun onFailure(call: Call<List<mCat>>?, t: Throwable?) {
                Log.i("onFailure:", t!!.message.toString())
            }

        })
    }

    private fun setSliderImage1() {

        val orange: Int = Color.rgb(250, 100, 15)
        val LightOrange: Int = Color.rgb(252, 160, 108)


        sliderView = findViewById(R.id.Main_imageSlider1)

        adapter2 = SliderAdapterExample2(Sliderv, this)

        sliderView.setSliderAdapter(adapter2)

        sliderView.setIndicatorAnimation(IndicatorAnimations.WORM) //set indicator animation by using SliderLayout.IndicatorAnimations. :WORM or THIN_WORM or COLOR or DROP or FILL or NONE or SCALE or SCALE_DOWN or SLIDE and SWAP!!

        sliderView.setSliderTransformAnimation(SliderAnimations.SIMPLETRANSFORMATION)
        sliderView.setAutoCycleDirection(SliderView.AUTO_CYCLE_DIRECTION_BACK_AND_FORTH)
        sliderView.setIndicatorSelectedColor(orange)
        sliderView.setIndicatorUnselectedColor(LightOrange)
        sliderView.setScrollTimeInSec(5) //set scroll delay in seconds :

        sliderView.startAutoCycle()

    }
    private fun setSliderImage2(){

        val orange: Int = Color.rgb(250, 100, 15)
        val LightOrange: Int = Color.rgb(252, 160, 108)
        //Slider 2


        sliderView2 = findViewById(R.id.Main_imageSlider2)

        adapter3 = SliderAdapterExample(MostMels, this)

        sliderView2.setSliderAdapter(adapter3)

        sliderView2.setIndicatorAnimation(IndicatorAnimations.WORM) //set indicator animation by using SliderLayout.IndicatorAnimations. :WORM or THIN_WORM or COLOR or DROP or FILL or NONE or SCALE or SCALE_DOWN or SLIDE and SWAP!!

        sliderView2.setSliderTransformAnimation(SliderAnimations.SIMPLETRANSFORMATION)
        sliderView2.setAutoCycleDirection(SliderView.AUTO_CYCLE_DIRECTION_RIGHT)
        sliderView2.setIndicatorSelectedColor(orange)
        sliderView2.setIndicatorUnselectedColor(LightOrange)
        sliderView2.setScrollTimeInSec(5) //set scroll delay in seconds :

        sliderView2.startAutoCycle()
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.Save -> {
                val intent = Intent(this, com.ahmed.newpro.save_fav::class.java)
                this.startActivity(intent)

            }
            R.id.home -> {
                Toast.makeText(this, "Home", Toast.LENGTH_LONG).show()
            }
            R.id.myorder -> {
                val intent = Intent(this, com.ahmed.newpro.orders::class.java)
                this.startActivity(intent)


            }
            R.id.offer -> {
                val intent = Intent(this, com.ahmed.newpro.Offer::class.java)
                this.startActivity(intent)
            }

            R.id.logout -> {
                logout()
            }
            R.id.language -> {
                // DynamicToast.makeSuccess(baseContext, "name: Ahmed Mamdouh\n and I develop this app \nphone :01069103550l\n GDG\n ",2000)
                showDialog();


            }

            else -> return super.onOptionsItemSelected(item)
        }


        Drawerlayout.closeDrawer(GravityCompat.START)
        return true
    }

   /* private fun showDialogaboutus() {
        val mbulder = AlertDialog.Builder(this)
        val myview: View = LayoutInflater.from(this).inflate(R.layout.about_us_dialog, null);

        mbulder.setView(myview)

        mbulder.show().window?.setBackgroundDrawableResource(R.drawable.rec_dilog_ly)
    }*/

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {

        val inflater = menuInflater;
        inflater.inflate(R.menu.cart, menu);
        return super.onCreateOptionsMenu(menu)
    }

    fun float_action() {
        val fad: View = findViewById(R.id.fad);
        fad.setOnClickListener {
            val intent = Intent(this, shaping_cart::class.java)
            this.startActivity(intent)
            // Snackbar.make(it,"Fad is now clicked ",Snackbar.LENGTH_LONG).setAction("Action",null).show();
        }


    }


    fun subtextcolorText1(text: String): SpannableString {
        val mSpannableString = SpannableString(text)
        val g1 = ForegroundColorSpan(ContextCompat.getColor(this, R.color.colorPrimary));
        val g2 = ForegroundColorSpan(ContextCompat.getColor(this, R.color.colorPrimary));
        mSpannableString.setSpan(g1, 0, 1, Spanned.SPAN_INCLUSIVE_EXCLUSIVE)
        mSpannableString.setSpan(g2, 8, 9, Spanned.SPAN_INCLUSIVE_EXCLUSIVE)


        return mSpannableString;

    }

    fun subtextcolorText2(text: String): SpannableString {
        val mSpannableString = SpannableString(text)
        val g1 = ForegroundColorSpan(ContextCompat.getColor(this, R.color.colorPrimary));
        val g2 = ForegroundColorSpan(ContextCompat.getColor(this, R.color.colorPrimary));
        mSpannableString.setSpan(g1, 0, 1, Spanned.SPAN_INCLUSIVE_EXCLUSIVE)
        mSpannableString.setSpan(g2, 5, 6, Spanned.SPAN_INCLUSIVE_EXCLUSIVE)


        return mSpannableString;

    }

    fun subtextcolorText3(text: String): SpannableString {
        val mSpannableString = SpannableString(text)
        val g1 = ForegroundColorSpan(ContextCompat.getColor(this, R.color.colorPrimary));
        val g2 = ForegroundColorSpan(ContextCompat.getColor(this, R.color.colorPrimary));
        mSpannableString.setSpan(g1, 0, 1, Spanned.SPAN_INCLUSIVE_EXCLUSIVE)
        mSpannableString.setSpan(g2, 5, 6, Spanned.SPAN_INCLUSIVE_EXCLUSIVE)


        return mSpannableString;

    }

    fun gotoSetting(view: View) {

        val intent = Intent(this, com.ahmed.newpro.acount_setting::class.java)
        this.startActivity(intent)

    }


    private fun logout() {



        editor.remove(USERID_KEY);
        editor.remove(USERName_KEY);
        editor.remove(USEREmail_KEY);
        editor.remove(USERAddress_KEY);
        editor.remove(USERImage_KEY);
        editor.remove(USERPhone_KEY);
        editor.remove(USERBDate_KEY);
        editor.remove(USERlanguage_KEY);
        editor.commit();
        val broadcastIntent = Intent()
        broadcastIntent.action = "com.package.ACTION_LOGOUT"
        sendBroadcast(broadcastIntent)

        val intent = Intent(this, com.ahmed.newpro.Start::class.java)
        intent.addCategory(Intent.CATEGORY_HOME);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);//***Change Here***
        this.startActivity(intent)
        finish()



    }

    private fun getmostMealOrdered() {

        val call: Call<List<mMenu>> = ApiClient.getClient.getMostPopularMeal()
        call.enqueue(object : Callback<List<mMenu>> {

            override fun onResponse(call: Call<List<mMenu>>?, response: Response<List<mMenu>>?) {

                MostMels.addAll(response!!.body()!!)

                setSliderImage2();

            }

            override fun onFailure(call: Call<List<mMenu>>?, t: Throwable?) {
                Log.i("onFailure:", t!!.message.toString())
            }

        })
    }

    private fun getSliderview() {

        val call: Call<List<mSlider>> = ApiClient.getClient.getSliderimage()
        call.enqueue(object : Callback<List<mSlider>> {

            override fun onResponse(
                call: Call<List<mSlider>>?,
                response: Response<List<mSlider>>?
            ) {

                Sliderv.addAll(response!!.body()!!)

                setSliderImage1();

            }

            override fun onFailure(call: Call<List<mSlider>>?, t: Throwable?) {
                Log.i("onFailure:", t!!.message.toString())
            }

        })
    }
    fun showDialog(){
        val items = arrayOf("English", "عربي")
        val builder = AlertDialog.Builder(this)
        with(builder)
        {
            setTitle("List of Items")
            setItems(items) { dialog, which ->
                var langid=items[which].get(0);
               if(langid=='E'){
                   setLanguage("en");
                   finish();
                   startActivity(getIntent());
                   overridePendingTransition(0, 0);
               }else{
                   setLanguage("ar");
                   finish();
                   startActivity(getIntent());
                   overridePendingTransition(0, 0);
               }
            }

           // setPositiveButton("OK", positiveButtonClick)
            show()
        }
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



}
