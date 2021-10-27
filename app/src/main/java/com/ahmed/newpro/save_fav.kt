package com.ahmed.newpro

import android.content.*
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ahmed.newpro.API.ApiClient
import com.ahmed.newpro.Adapter.catAdapter
import com.ahmed.newpro.Adapter.menuAdapter
import com.ahmed.newpro.Adapter.saveAdapter
import com.ahmed.newpro.Model.mCat
import com.ahmed.newpro.Model.mMenu
import com.ahmed.newpro.Model.mSaved
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class save_fav : AppCompatActivity() {
    lateinit var recyclerView: RecyclerView;
    lateinit var adapter: saveAdapter;
    val saveds = ArrayList<mSaved>()
    //store login data in shard
    private var PRIVATE_MODE = 0
    private val PREF_NAME = "userInfo"
    lateinit var sharedPref: SharedPreferences
    lateinit var editor: SharedPreferences.Editor;
    var USERID_KEY = "UserID"
    var USEREmail_KEY = "UserEmail"
    var USERName_KEY = "UserName"
    var USERImage_KEY = "UserImage"
    var USERPhone_KEY = "UserPhone"
    var USERAddress_KEY = "UserAddress"
    var USERBDate_KEY = "UserBDate"




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_save_fav)
        val intentFilter = IntentFilter()
        intentFilter.addAction("com.package.ACTION_LOGOUT")
        registerReceiver(object : BroadcastReceiver() {
            override fun onReceive(context: Context?, intent: Intent?) {
                Log.d("onReceive", "Logout in progress")
                //At this point you should start the login activity and finish this one
                finish()
            }
        }, intentFilter)

        recyclerView = findViewById(R.id.save_recycleView) as RecyclerView

        //adding a layoutmanager
        recyclerView.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)

        //SET SHARDpRES
        sharedPref = getSharedPreferences(PREF_NAME, PRIVATE_MODE)

        editor = sharedPref.edit()
        editor.apply()
        getData()

    }

    private fun getData() {

        val call: Call<List<mSaved>> = ApiClient.getClient.getSaved(sharedPref.getInt(USERID_KEY, 0))
        call.enqueue(object : Callback<List<mSaved>> {

            override fun onResponse(call: Call<List<mSaved>>?, response: Response<List<mSaved>>?) {

                saveds.addAll(response!!.body()!!)
                //creating our adapter
                adapter = saveAdapter(saveds, baseContext);

                //now adding the adapter to recyclerview
                recyclerView.adapter = adapter



            }

            override fun onFailure(call: Call<List<mSaved>>?, t: Throwable?) {
                Log.i("onFailure:", t!!.message.toString())
            }

        })
    }

    fun gomainMeanufromsave(view: View) {
        finish()
    }
}
