package com.ahmed.newpro

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TabHost
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.fragment.app.FragmentTransaction
import androidx.viewpager.widget.ViewPager
import com.ahmed.newpro.Adapter.ViewPagerAdapter
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayout.OnTabSelectedListener

import androidx.core.app.ComponentActivity.ExtraData
import androidx.core.content.ContextCompat.getSystemService
import android.icu.lang.UCharacter.GraphemeClusterBreak.T
import android.util.Log
import com.ahmed.newpro.Model.order_data
import kotlinx.android.synthetic.main.activity_order_path_details.*


class order_path_details : AppCompatActivity() {


    private var tabLayout: TabLayout? = null
    var viewPager: ViewPager? = null
    var tv_date: TextView? = null
    var tv_ordernum: TextView? = null
    var tv_total_price: TextView? = null

    var order_id: Int = 0
    var date: String = ""
    var price: Float = 0f
    var time: String = ""
    var status: String = ""
    var cash_methods: String = ""
    var Delivery_methods: String = ""
    var num_item:Int=0


    override fun onCreate(savedInstanceState: Bundle?) {


        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_order_path_details)
        val intentFilter = IntentFilter()
        intentFilter.addAction("com.package.ACTION_LOGOUT")
        registerReceiver(object : BroadcastReceiver() {
            override fun onReceive(context: Context?, intent: Intent?) {
                Log.d("onReceive", "Logout in progress")
                //At this point you should start the login activity and finish this one
                finish()
            }
        }, intentFilter)

        viewPager = findViewById(R.id.main_pager) as ViewPager
        tv_date = findViewById(R.id.tv_date) as TextView
        tv_ordernum = findViewById(R.id.tv_ordernum) as TextView
        tv_total_price = findViewById(R.id.tv_total_price) as TextView
        setupViewPager(viewPager!!)

        tabLayout = findViewById(R.id.tab_ly) as TabLayout
        tabLayout!!.setupWithViewPager(viewPager)


//get data from orders
        val i = intent

        order_id = i.getIntExtra("order_id", 0)
        num_item = i.getIntExtra("num_item", 0)

        date = i.getStringExtra("date")
        price = i.getFloatExtra("price",0f)
        time = i.getStringExtra("time")
        status = i.getStringExtra("status")
        cash_methods = i.getStringExtra("cash_methods")
        Delivery_methods = i.getStringExtra("Delivery_methods")
        tv_date!!.text = date
        tv_ordernum!!.text = order_id.toString()
        tv_total_price!!.text = price.toString()





        // Creating the new Fragment with the name passed in.
//        tabLayout=findViewById(R.id.tab_ly)
//        viewPager=findViewById(R.id.main_pager)
//        tabLayout.setupWithViewPager(viewPager)
//
//        tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
//            override fun onTabReselected(p0: TabLayout.Tab?) {
//            }
//
//            override fun onTabUnselected(p0: TabLayout.Tab?) {
//            }
//
//            override fun onTabSelected(p0: TabLayout.Tab?) {
//              val fm:  FragmentManager =supportFragmentManager;
//                val ft:FragmentTransaction=fm.beginTransaction();
//                val fb = status_fragment()
//                ft.replace(R.id.fragment, fb)
//                ft.addToBackStack(null)
//                ft.commit()
//
//            }
//
//
//        })
//
//        viewPager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
//            override fun equals(other: Any?): Boolean {
//                return super.equals(other)
//            }
//
//            override fun onPageScrollStateChanged(state: Int) {
//            }
//
//            override fun onPageScrolled(
//                position: Int,
//                positionOffset: Float,
//                positionOffsetPixels: Int
//            ) {
//
//            }
//
//            override fun onPageSelected(position: Int) {
//
//            }
//        })


    }


    private fun setupViewPager(viewPager: ViewPager) {
        val adapter = ViewPagerAdapter(supportFragmentManager)
        adapter.addFragment(status_fragment(), "Order Status")
        adapter.addFragment(order_details(), "Ordered items")

        viewPager.adapter = adapter
    }

    internal inner class ViewPagerAdapter(manager: FragmentManager) :
        FragmentPagerAdapter(manager) {
        private val mFragmentList = ArrayList<Fragment>()
        private val mFragmentTitleList = ArrayList<String>()

        override fun getItem(position: Int): Fragment {
            return mFragmentList[position]
        }

        override fun getCount(): Int {
            return mFragmentList.size
        }

        fun addFragment(fragment: Fragment, title: String) {
            mFragmentList.add(fragment)
            mFragmentTitleList.add(title)
        }

        override fun getPageTitle(position: Int): CharSequence {
            return mFragmentTitleList[position]
        }
    }


    fun gotomain(view: View) {

        val intent = Intent(this, orders::class.java)
        this.startActivity(intent)
    }

}

