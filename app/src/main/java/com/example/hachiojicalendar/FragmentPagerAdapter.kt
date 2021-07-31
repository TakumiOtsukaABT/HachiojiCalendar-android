package com.example.hachiojicalendar

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import androidx.viewpager2.adapter.FragmentStateAdapter


class FragmentPagerAdapter(fm: FragmentActivity) :
    FragmentStateAdapter(fm) {


    override fun getItemCount(): Int {
        return 2
    }

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> OnBoarding1Fragment()
            else -> OnBoarding2Fragment()
        }
    }
}

