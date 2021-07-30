package com.example.hachiojicalendar

import android.app.AlarmManager
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.AdapterView.OnItemClickListener
import android.widget.ListView
import android.widget.SimpleAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat
import java.time.LocalDate
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashMap
import kotlin.text.CharDirectionality.Companion.valueOf


class SettingsActivity : AppCompatActivity() {
    private val subjects = arrayOf("通知日", "通知時間")
    private var comments = arrayOf("", "")

    var date = 0
    var time = 0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val data = getPreferences(MODE_PRIVATE)
        date = data.getInt("DateDigit", 0)
        time = data.getInt("TimeDigit", 0)
        comments[0] = data.getString("Date", "").toString()
        comments[1] = data.getString("Time", "").toString()


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
            when (position) {
                0 -> {
                    val setDateDialog = SetDateDialog()
                    setDateDialog.show(supportFragmentManager, "setDate_tag")
                }
                1 -> {
                    val setTimeDialog = SetTimeDialog()
                    setTimeDialog.show(supportFragmentManager, "setTime_tag")
                }
            }
        })
    }

    fun setDateComment(text: String, index: Int) {
        comments[0] = text
        val data = getPreferences(MODE_PRIVATE)
        val editor = data.edit()
        editor.putString("Date", text)
        editor.putInt("DateDigit", index)
        editor.apply()
    }

    fun setTimeComment(text: String, index: Int) {
        comments[1] = text
        val data = getPreferences(MODE_PRIVATE)
        val editor = data.edit()
        editor.putString("Time", text)
        editor.putInt("TimeDigit", index)
        editor.apply()
    }

    fun setNotification(view: View) {
        val i = Intent(
            applicationContext,
            ReceivedActivity::class.java
        ) // ReceivedActivityを呼び出すインテントを作成
        for ((index, elem) in CalendarHelper.sched.withIndex()) {
            val calendar = Calendar.getInstance()

            print("dddd")
            val todayCalendar = Calendar.getInstance()

            var localDate = elem.dateComponent // Calendar取得
            localDate = localDate.minusDays(date.toLong())
            calendar.set(Calendar.MONTH,localDate.monthValue-1)
            calendar.set(Calendar.DATE,localDate.dayOfMonth)
            calendar.set(Calendar.HOUR_OF_DAY,time)
            calendar.set(Calendar.MINUTE,0)


            if (todayCalendar.before(calendar)) {

                val array = elem.garbage.toIntArray()
                i.putExtra("GarbageType",array)

                val sender =
                    PendingIntent.getBroadcast(this, index, i, 0) // ブロードキャストを投げるPendingIntentの作成

                val am = getSystemService(ALARM_SERVICE) as AlarmManager // AlramManager取得

                am[AlarmManager.RTC_WAKEUP, calendar.timeInMillis] =
                    sender // AlramManagerにPendingIntentを登録
            }
        }
        Toast.makeText(applicationContext,"表示している月の通知を設定しました",Toast.LENGTH_LONG).show()
    }
}

class ReceivedActivity : BroadcastReceiver() {

    companion object {
        const val TAG = "AlarmReceiver"
        const val NOTIFICATION_ID = 0
        const val PRIMARY_CHANNEL_ID = "primary_notification_channel"
    }

    lateinit var notificationManager: NotificationManager

    lateinit var array: IntArray

    override fun onReceive(context: Context, intent: Intent) {
        notificationManager = context.getSystemService(
            Context.NOTIFICATION_SERVICE) as NotificationManager

        array = intent.getIntArrayExtra("GarbageType")!!
        println("llll")
        print(array)
        createNotificationChannel()
        deliverNotification(context)
    }

    private fun deliverNotification(context: Context) {
        val contentIntent = Intent(context, MainActivity::class.java)
        val contentPendingIntent = PendingIntent.getActivity(
            context,
            NOTIFICATION_ID,
            contentIntent,
            PendingIntent.FLAG_UPDATE_CURRENT
        )
        var notificationComment:String = ""
        for ((index,elem) in array.withIndex()) {
            val j = GarbageType.valueOf(elem)
            notificationComment += CalendarHelper().getGarbageTypeJapanese(j)
            if (array.size!=index+1) {
                notificationComment += "と"
            }
        }
        notificationComment += "の日です。"

        val builder =
            NotificationCompat.Builder(context, PRIMARY_CHANNEL_ID)
                .setSmallIcon(R.drawable.icon)
                .setContentTitle("ゴミ出しの時間です")
                .setContentText(notificationComment)
                .setContentIntent(contentPendingIntent)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setAutoCancel(true)
                .setDefaults(NotificationCompat.DEFAULT_ALL)

        notificationManager.notify(NOTIFICATION_ID, builder.build())
    }

    fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val notificationChannel = NotificationChannel(
                PRIMARY_CHANNEL_ID,
                "Stand up notification",
                NotificationManager.IMPORTANCE_HIGH
            )
            notificationChannel.enableLights(true)
            notificationChannel.lightColor = Color.RED
            notificationChannel.enableVibration(true)
            notificationChannel.description = "AlarmManager Tests"
            notificationManager.createNotificationChannel(
                notificationChannel)
        }
    }
}