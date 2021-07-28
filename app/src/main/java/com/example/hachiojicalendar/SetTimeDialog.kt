package com.example.hachiojicalendar

import android.app.AlertDialog
import android.app.Dialog
import android.content.DialogInterface
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.Parcel
import android.os.Parcelable
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDialog
import androidx.fragment.app.DialogFragment

class SetTimeDialog : DialogFragment() {
    var districtIndex=0
    lateinit var districtButton : Button
    val list = arrayOf("0時", "1時", "2時", "3時", "4時", "5時", "6時", "7時", "8時", "9時",
        "10時", "11時", "12時", "13時", "14時", "15時", "16時", "17時", "18時", "19時", "20時", "21時", "22時", "23時")

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {
            val builder = AlertDialog.Builder(it)
            builder.setTitle("地区を選択してください")
                .setItems(list,
                    DialogInterface.OnClickListener { dialog, which ->
                        // The 'which' argument contains the index position
                        // of the selected item
                        (activity as SettingsActivity).setTimeComment(list[which],which)
                        (activity as SettingsActivity).updateList()
                        (activity as SettingsActivity).time = which
//                        val data = (activity as SettingsActivity).getPreferences(AppCompatActivity.MODE_PRIVATE)
//                        val editor = data.edit()
//                        editor.putInt("Time", which)
//                        editor.putString("Time", list[which])
//                        editor.apply()
                    })
            builder.create()
        } ?: throw IllegalStateException("Activity cannot be null")
    }
}