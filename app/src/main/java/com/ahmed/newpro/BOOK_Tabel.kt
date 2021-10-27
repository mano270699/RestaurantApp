package com.ahmed.newpro

import android.app.Activity
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.*
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.View.VISIBLE
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.ahmed.newpro.API.ApiClient
import com.ahmed.newpro.Model.mTabelOrder
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.SimpleDateFormat
import java.util.*


class BOOK_Tabel : AppCompatActivity() {
lateinit var context:Context
    lateinit var btn_daypicker: Button
    lateinit var btn_time: Button
    lateinit var btn_table: Button
    lateinit var iv_plus: ImageView
    lateinit var iv_min: ImageView
    lateinit var tv_time: TextView
    lateinit var tv_DAY: TextView
    lateinit var tvnum: TextView
    lateinit var num_ppl: TextView
    lateinit var note: EditText
    lateinit var tv_table: TextView
    var tv_table_id: Int = 0


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

    var USER_num_people_mytabel_KEY = "numpeoplemytabel"
    var USER_tabel_id_mytabel_KEY = "tabel_id_mytabel"
    var USER_time_mytabel_KEY = "timemytabel"
    var USERB_date_mytabel_KEY = "date_mytabel"
    var USER_note_mytabel_KEY = "note_mytabel"

    private var IDTable = 0
    private lateinit var adapter: ArrayAdapter<Int>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_book__tabel)

        val intentFilter = IntentFilter()
        intentFilter.addAction("com.package.ACTION_LOGOUT")
        registerReceiver(object : BroadcastReceiver() {
            override fun onReceive(context: Context?, intent: Intent?) {
                Log.d("onReceive", "Logout in progress")
                //At this point you should start the login activity and finish this one
                finish()
            }
        }, intentFilter)



        btn_daypicker = findViewById(R.id.btn_daypicker) as Button
        iv_plus = findViewById(R.id.iv_plus) as ImageView
        iv_min = findViewById(R.id.iv_min) as ImageView
        btn_time = findViewById(R.id.btn_time) as Button
        btn_table = findViewById(R.id.btn_table) as Button
        context=this

        tv_time = findViewById(R.id.tv_time) as TextView
        tv_DAY = findViewById(R.id.tv_DAY) as TextView
        tvnum = findViewById(R.id.tvnum) as TextView
        num_ppl = findViewById(R.id.num_ppl) as TextView
        note = findViewById(R.id.note) as EditText

        //SET SHARDpRES
        sharedPref = getSharedPreferences(PREF_NAME, PRIVATE_MODE)

        editor = sharedPref.edit()
        editor.apply()


        tv_table = findViewById(R.id.tv_table)



        iv_plus.setOnClickListener(View.OnClickListener {


            val istr1 = tvnum.text.toString()
            var count_pepole: Int? = istr1.toInt()
            count_pepole = count_pepole!! + 1
            tvnum.text = (count_pepole).toString()
            num_ppl.text = (count_pepole).toString() + " peoples"

        })


        iv_min.setOnClickListener(View.OnClickListener {


            val istr1 = tvnum.text.toString()
            var count_pepole: Int? = istr1.toInt()

            if (count_pepole == 1) Toast.makeText(
                it.context,
                "can not less count people than 1",
                Toast.LENGTH_LONG
            ).show()
            else {
                count_pepole = count_pepole!! - 1
                tvnum.text = (count_pepole).toString()
                if (count_pepole == 1)
                    num_ppl.text = (count_pepole).toString() + " people"
                else
                    num_ppl.text = (count_pepole).toString() + " peoples"
            }
        })










        btn_time.setOnClickListener {
            val cal = Calendar.getInstance()
            val timeSetListener = TimePickerDialog.OnTimeSetListener { timePicker, hour, minute ->
                cal.set(Calendar.HOUR_OF_DAY, hour)
                cal.set(Calendar.MINUTE, minute)
                tv_time.text = SimpleDateFormat("HH:mm").format(cal.time)
            }
            TimePickerDialog(
                this,
                timeSetListener,
                cal.get(Calendar.HOUR_OF_DAY),
                cal.get(Calendar.MINUTE),
                true
            ).show()
        }

        val c = Calendar.getInstance()
        val year = c.get(Calendar.YEAR)
        val month = c.get(Calendar.MONTH)
        val day = c.get(Calendar.DAY_OF_MONTH)

        btn_daypicker.setOnClickListener {

            val dpd = DatePickerDialog(
                this,
                DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
                    // Display Selected date in TextView
                    tv_DAY.setText("" + year + "-" + (month + 1) + "-" + dayOfMonth)
                },
                year,
                month,
                day
            )
            dpd.show()

        }


        adapter = ArrayAdapter(this, android.R.layout.select_dialog_singlechoice)


        btn_table.setOnClickListener(View.OnClickListener {
            adapter.clear();
            adapter.notifyDataSetChanged();

            Toast.makeText(this, "btn clicked", Toast.LENGTH_SHORT).show()
            getTableAvailable(tv_DAY.text.toString() + " " + tv_time.text.toString());

        })

    }


    fun booktabel(view: View) {

        if ((isInputEmpty(tv_time) or isInputEmpty(tv_DAY) or isInputEmpty(tv_table))) {

            Toast.makeText(this, "please fill all filed", Toast.LENGTH_SHORT).show()
            return

        }


        val num_people_mytabel: Int = tvnum.text.toString().toInt()
        val tabel_id_mytabel: Int = tv_table_id.toString().toInt()
        val time_mytabel: String = tv_time.text.toString()
        val date_mytabel: String = tv_DAY.text.toString()
        val note_mytabel: String = note.getText().toString()




        editor.putInt(USER_num_people_mytabel_KEY, num_people_mytabel)
        editor.putInt(USER_tabel_id_mytabel_KEY, tabel_id_mytabel)
        editor.putString(USER_time_mytabel_KEY, time_mytabel)
        editor.putString(USERB_date_mytabel_KEY, date_mytabel)
        editor.putString(USER_note_mytabel_KEY, note_mytabel)

        editor.apply()


        val returnIntent = Intent()
        returnIntent.putExtra("num_people", num_people_mytabel)
        returnIntent.putExtra("time", time_mytabel)
        returnIntent.putExtra("date", date_mytabel)
        returnIntent.putExtra("tabel_id", tabel_id_mytabel)
        returnIntent.putExtra("note", note_mytabel)
        setResult(Activity.RESULT_OK, returnIntent)
        finish()

    }

    fun gotocart(view: View) {


        val intent: Intent = Intent(this, Checkout::class.java)
        startActivity(intent)

    }

    fun time_picker(view: View) {


    }


    fun isInputEmpty(txt: TextView): Boolean {

        if (txt.text == "") return true;
        return false

    }


//    override fun onPositiveButtonClicked(list: Array<out String>?, position: Int) {
//
//        tv_table.setText("Table Number :" + list!![position]);
//        tv_table_id = list!![position].toInt()
//
//    }
//
//    override fun onNegativeButtonClicked() {
//        tv_table.setText("");
//    }


     fun makeDialogTable(t: ArrayAdapter<Int>) {

       // Toast.makeText(context, "makeDialog t.getCount():"+t.getCount(), Toast.LENGTH_LONG).show();
        Log.i("dapter_size_makeDi:", "makeDialog t.getCount():"+t.getCount())
        val builder: AlertDialog.Builder = AlertDialog.Builder(context)
        builder.setTitle("Choose Table:")
        if (t.count == 0) {
            tv_table.setText("")
            tv_table.setVisibility(View.INVISIBLE)
            builder.setMessage("No available table in this time.\nPlease select another time.")
        } else {
            builder.setAdapter(t, DialogInterface.OnClickListener { dialog, which ->
                IDTable = t.getItem(which)!!.toInt()
                tv_table.setText("Tabel ID:" + IDTable)
                tv_table.setVisibility(VISIBLE)
                tv_table_id=IDTable
            })
        }


        // add OK and Cancel buttons
        builder.setPositiveButton("OK", null)
        builder.create()
        builder.show()


//////////////////////////////////////////////////
//        // setup the alert builder
//        val builder = AlertDialog.Builder(context)
//        builder.setTitle("Choose an animal")
//
//// add a radio button list
//        val animals = arrayOf("horse", "cow", "camel", "sheep", "goat")
//        val checkedItem = 1 // cow
//        builder.setSingleChoiceItems(animals, checkedItem) { dialog, which ->
//            // user checked an item
//
//            Log.i("which:",which.toString())
//        }
//
//
//// add OK and Cancel buttons
//        builder.setPositiveButton("OK") { dialog, which ->
//            // user clicked OK
//            Log.i("which:","OK");
//        }
//        builder.setNegativeButton("Cancel", null)
//
//// create and show the alert dialog
//        val dialog = builder.create()
//        dialog.show()
    }


     fun getTableAvailable(date: String) {

        Log.i("date",date)


        Log.i("Response_before","in Response_before")
        val call: Call<List<mTabelOrder>> = ApiClient.getClient.getAvalibleTable(date)
        call.enqueue(object : Callback<List<mTabelOrder>> {

            override fun onResponse(
                call: Call<List<mTabelOrder>>?,
                response: Response<List<mTabelOrder>>?
            ) {
                Log.i("Response","in Response")
                Log.i("Response",response.toString())
                Log.i("ResponseBody",response!!.body().toString())
                val tables: List<mTabelOrder>? = response!!.body()

                // Toast.makeText(getBaseContext(), "tables:"+tables, Toast.LENGTH_LONG).show();
                if (tables != null) for (t in tables){

                    adapter!!.add(t.table_id)
                    Log.i("table_id", t.table_id.toString())

                }
                Log.i("adapter.size:", adapter.count.toString())
                makeDialogTable(adapter)


            }

            override fun onFailure(call: Call<List<mTabelOrder>>?, t: Throwable?) {
                Toast.makeText(this@BOOK_Tabel, "onFailure", Toast.LENGTH_SHORT).show()

                Log.i("onFailure:", t!!.message.toString())
            }

        })

    }
}
