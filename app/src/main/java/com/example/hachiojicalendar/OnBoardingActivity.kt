package com.example.hachiojicalendar

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2


class OnBoardingActivity : AppCompatActivity() {
    lateinit var viewPager: ViewPager2

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_on_boarding)
        viewPager = findViewById(R.id.pager)
        viewPager.adapter = FragmentPagerAdapter(this)

        var toolBar = findViewById(R.id.toolbar2) as androidx.appcompat.widget.Toolbar
        setSupportActionBar(toolBar)
        val actionBar = supportActionBar
        actionBar!!.title = ""
        actionBar.setDisplayHomeAsUpEnabled(false)
    }
}