package com.ahmed.newpro


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import android.widget.Toast
import androidx.constraintlayout.solver.widgets.ConstraintWidget.VISIBLE
import com.ahmed.newpro.Model.order_data




/**
 * A simple [Fragment] subclass.
 */
class status_fragment : BaseFragment() {

    lateinit var ly_order_not_canceled: RelativeLayout
    lateinit var ly_order_canceled: RelativeLayout



    lateinit var img_placed: ImageView
    lateinit var img_prepare: ImageView
    lateinit var img_Dispatched: ImageView
    lateinit var img_Delivered: ImageView

    lateinit var img_placed_gray: ImageView
    lateinit var img_prepare_gray: ImageView
    lateinit var img_Dispatched_gray: ImageView
    lateinit var img_Delivered_gray: ImageView
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val incomingText = ACTIVITY.status
        println("Incoming text: " + incomingText)

        when (incomingText) {
            "New_Order" -> setOrderPlaced()
            "Preparing" -> setPreparing()
            "Dispatched" -> setDispatched()
            "Delivered" -> setDelivered()
            "Cancel" -> setCancel()

        }

        Toast.makeText(activity, "status:" + incomingText, Toast.LENGTH_SHORT).show()

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view: View = inflater.inflate(R.layout.fragment_status_fragment, container, false)


        // You can also set the value of this variable to be read from
        // another fragment later

        ly_order_not_canceled = view.findViewById(R.id.ly_order_not_canceled) as RelativeLayout
        ly_order_canceled = view.findViewById(R.id.ly_order_canceled) as RelativeLayout




        img_placed = view.findViewById(R.id.img_placed) as ImageView
        img_prepare = view.findViewById(R.id.img_prepare) as ImageView
        img_Dispatched = view.findViewById(R.id.img_Dispatched) as ImageView
        img_Delivered = view.findViewById(R.id.img_Delivered) as ImageView

        img_placed_gray = view.findViewById(R.id.img_placed_gray) as ImageView
        img_prepare_gray = view.findViewById(R.id.img_prepare_gray) as ImageView
        img_Dispatched_gray = view.findViewById(R.id.img_Dispatched_gray) as ImageView
        img_Delivered_gray = view.findViewById(R.id.img_Delivered_gray) as ImageView



        return view
    }

    fun setOrderPlaced() {


       img_placed.visibility = View.VISIBLE
       img_placed_gray.visibility = View.INVISIBLE

    }

    fun setPreparing() {
        img_placed.visibility = View.VISIBLE
        img_prepare.visibility = View.VISIBLE

        img_placed_gray.visibility = View.INVISIBLE
        img_prepare_gray.visibility = View.INVISIBLE

    }

    fun setDispatched() {

        img_placed.visibility = View.VISIBLE
        img_prepare.visibility = View.VISIBLE
        img_Dispatched.visibility = View.VISIBLE

        img_placed_gray.visibility = View.INVISIBLE
        img_prepare_gray.visibility = View.INVISIBLE
        img_Dispatched_gray.visibility = View.INVISIBLE
    }

    fun setDelivered() {
        img_placed.visibility = View.VISIBLE
        img_prepare.visibility = View.VISIBLE
        img_Dispatched.visibility = View.VISIBLE
        img_Delivered.visibility = View.VISIBLE

        img_placed_gray.visibility = View.INVISIBLE
        img_prepare_gray.visibility = View.INVISIBLE
        img_Dispatched_gray.visibility = View.INVISIBLE
        img_Delivered_gray.visibility = View.INVISIBLE

    }

    fun setCancel() {
        ly_order_canceled.visibility = View.VISIBLE
        ly_order_not_canceled.visibility = View.INVISIBLE


    }


}