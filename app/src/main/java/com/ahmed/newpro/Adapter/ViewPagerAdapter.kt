package com.ahmed.newpro.Adapter;

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.ahmed.newpro.status_fragment


class ViewPagerAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm) {
        override fun getItem(position: Int): Fragment {
        var fragment: Fragment? = null
        if (position == 0) {
        fragment = status_fragment()
        }
        return fragment!!
        }

        override fun getCount(): Int {
        return 3
        }

        override fun getPageTitle(position: Int): CharSequence? {
        var title: String? = null
        if (position == 0) {
        title = "First"
        } else if (position == 1) {
        title = "Second"
        } else if (position == 2) {
        title = "Third"
        }
        return title
        }
        }