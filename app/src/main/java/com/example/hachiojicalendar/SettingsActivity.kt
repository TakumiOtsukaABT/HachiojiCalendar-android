package com.example.hachiojicalendar

import android.os.Bundle
import android.view.View
import android.widget.AdapterView.OnItemClickListener
import android.widget.ListView
import android.widget.SimpleAdapter
import androidx.appcompat.app.AppCompatActivity


class SettingsActivity : AppCompatActivity(){
    private val subjects = arrayOf("通知日", "通知時間")
    private var comments = arrayOf("","")

    var date = 0
    var time = 0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val data = getPreferences(MODE_PRIVATE)
        date = data.getInt("DateDigit", 0)
        time = data.getInt("TimeDigit",0)
        comments[0] = data.getString("Date","").toString()
        comments[1] = data.getString("Time","").toString()


        setContentView(R.layout.settings_activity)
        var toolBar = findViewById(R.id.toolbar) as androidx.appcompat.widget.Toolbar
        setSupportActionBar(toolBar)
        val actionBar = supportActionBar
        actionBar!!.title = "通知設定"
        actionBar.setDisplayHomeAsUpEnabled(true)

        // リスト項目のもととなる値を準備する
        updateList(subjects, comments)
    }

    public fun updateList(
        subjects: Array<String> = this.subjects,
        comments: Array<String> = this.comments
    ) {
        val data: MutableList<Map<String, String?>> = ArrayList()
        for (i in subjects.indices) {
            val item: MutableMap<String, String?> = HashMap()
            item["Subject"] = subjects[i]
            item["Comment"] = comments[i]
            data.add(item)
        }
        val adapter = SimpleAdapter(
            this,
            data,
            android.R.layout.simple_list_item_2,
            arrayOf("Subject", "Comment"),
            intArrayOf(android.R.id.text1, android.R.id.text2)
        )
        val listView = findViewById<View>(R.id.listview) as ListView
        listView.adapter = adapter
        listView.setOnItemClickListener(OnItemClickListener { parent, view, position, id ->
            when(position){
                0->{
                    val setDateDialog = SetDateDialog()
                    setDateDialog.show(supportFragmentManager, "setDate_tag")
                }
                1->{
                    val setTimeDialog = SetTimeDialog()
                    setTimeDialog.show(supportFragmentManager, "setTime_tag")
                }
            }
        })
    }

    fun setDateComment(text:String,index:Int) {
        comments[0] = text
        val data = getPreferences(MODE_PRIVATE)
        val editor = data.edit()
        editor.putString("Date", text)
        editor.putInt("DateDigit",index)
        editor.apply()
    }

    fun setTimeComment(text:String,index:Int) {
        comments[1] = text
        val data = getPreferences(MODE_PRIVATE)
        val editor = data.edit()
        editor.putString("Time", text)
        editor.putInt("TimeDigit", index)
        editor.apply()
    }
}
