package com.example.hachiojicalendar

import android.os.Bundle
import android.widget.Toolbar
import androidx.appcompat.app.AppCompatActivity

class SettingsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.settings_activity)
        var toolBar = findViewById(R.id.toolbar) as androidx.appcompat.widget.Toolbar
        setSupportActionBar(toolBar)
        val actionBar = supportActionBar
        actionBar!!.title = "通知設定"
        actionBar.setDisplayHomeAsUpEnabled(true)
    }
}