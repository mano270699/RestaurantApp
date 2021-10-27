package com.ahmed.newpro


import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import android.widget.TextView
import androidx.cardview.widget.CardView

/**
 * A simple [Fragment] subclass.
 */
class fr_payment : Fragment() {

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


    var USERB_txtSuptotal_KEY = "txtSuptotal"
    var USERB_txtTotal_KEY = "txtTotal"
    var USERB_Payment_Method_KEY = "Payment_Method"


    lateinit var txtSuptotal: TextView
    lateinit var txtTotal: TextView
    lateinit var rb_cash: RadioButton
    lateinit var rb_cards: RadioButton
    lateinit var rb_paypal: RadioButton
    lateinit var rb_cashu: RadioButton
    lateinit var card_cash: CardView
    lateinit var card_cards: CardView
    lateinit var card_paypal: CardView
    lateinit var card_cashu: CardView
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_fr_payment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        sharedPref = requireContext().getSharedPreferences(PREF_NAME, PRIVATE_MODE)

        editor = sharedPref.edit()
        editor.apply()

        rb_cash = getView()!!.findViewById(R.id.rb_cash) as RadioButton
        rb_cards = getView()!!.findViewById(R.id.rb_cards) as RadioButton
        rb_paypal = getView()!!.findViewById(R.id.rb_paypal) as RadioButton
        rb_cashu = getView()!!.findViewById(R.id.rb_cashu) as RadioButton

        card_cash = getView()!!.findViewById(R.id.card_cash) as CardView
        card_cards = getView()!!.findViewById(R.id.card_cards) as CardView
        card_paypal = getView()!!.findViewById(R.id.card_paypal) as CardView
        card_cashu = getView()!!.findViewById(R.id.card_cashu) as CardView

        txtSuptotal = getView()!!.findViewById(R.id.txtSuptotal) as TextView
        txtTotal = getView()!!.findViewById(R.id.txtTotal) as TextView

        txtSuptotal.text =sharedPref.getString(USERB_txtSuptotal_KEY,"Nan")
        txtTotal.text= sharedPref.getString(USERB_txtTotal_KEY,"Nan")


        editor.putString(USERB_Payment_Method_KEY,"COD")
        editor.apply()

        rb_cash.setOnClickListener(View.OnClickListener {
            setRb_cash()
            editor.putString(USERB_Payment_Method_KEY,"COD")
            editor.apply()

        })

        rb_cards.setOnClickListener(View.OnClickListener {
            setRb_cards()
            editor.putString(USERB_Payment_Method_KEY,"cards")
            editor.apply()



        })
        rb_paypal.setOnClickListener(View.OnClickListener {
            setRb_paypal()
            editor.putString(USERB_Payment_Method_KEY,"paypal")
            editor.apply()


        })
        rb_cashu.setOnClickListener(View.OnClickListener {
            setRb_cashul()
            editor.putString(USERB_Payment_Method_KEY,"cashu")
            editor.apply()


        })



        card_cash.setOnClickListener(View.OnClickListener {

            setRb_cash()
            editor.putString(USERB_Payment_Method_KEY,"COD")
            editor.apply()

        })
        card_cards.setOnClickListener(View.OnClickListener {

            setRb_cards()
            editor.putString(USERB_Payment_Method_KEY,"cards")
            editor.apply()


        })
        card_paypal.setOnClickListener(View.OnClickListener {

            setRb_paypal()
            editor.putString(USERB_Payment_Method_KEY,"paypal")
            editor.apply()

        })

        card_cashu.setOnClickListener(View.OnClickListener {

            setRb_cashul()
            editor.putString(USERB_Payment_Method_KEY,"cashu")
            editor.apply()


        })
    }

    fun setRb_cash() {
        rb_cash.isChecked = true;
        rb_cards.isChecked = false;
        rb_paypal.isChecked = false;
        rb_cashu.isChecked = false;

    }
    fun setRb_cards() {
        rb_cash.isChecked = false;
        rb_cards.isChecked = true;
        rb_paypal.isChecked = false;
        rb_cashu.isChecked = false;
    }
    fun setRb_paypal() {
        rb_cash.isChecked = false;
        rb_cards.isChecked = false;
        rb_paypal.isChecked = true;
        rb_cashu.isChecked = false;

    }

    fun setRb_cashul() {
        rb_cash.isChecked = false;
        rb_cards.isChecked = false;
        rb_paypal.isChecked = false;
        rb_cashu.isChecked = true;

    }



}
