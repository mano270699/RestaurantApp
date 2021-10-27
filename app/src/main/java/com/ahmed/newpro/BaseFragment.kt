package com.ahmed.newpro

import android.content.Context
import androidx.fragment.app.Fragment

abstract class BaseFragment : Fragment() {

    lateinit var ACTIVITY: order_path_details

    override fun onAttach(context: Context) {
        super.onAttach(context)
        ACTIVITY = context as order_path_details
    }
}