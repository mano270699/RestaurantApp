package com.ahmed.newpro

import android.content.Context
import androidx.fragment.app.Fragment

abstract class BaseFragment2 : Fragment() {

    lateinit var ACTIVITY: BOOK_Tabel

    override fun onAttach(context: Context) {
        super.onAttach(context)
        ACTIVITY = context as BOOK_Tabel
    }
}