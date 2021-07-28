package com.example.hachiojicalendar

import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.ListView
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

        val data = ArrayList<Any>()
        data.add("通知日")
        data.add("通知時間")
        val adapter: ArrayAdapter<*> = ArrayAdapter(this, android.R.layout.simple_list_item_1, data)
        val listView: ListView = findViewById<View>(R.id.listview) as ListView
        listView.setAdapter(adapter)
    }
}