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
import androidx.appcompat.app.AppCompatDialog
import androidx.fragment.app.DialogFragment

class SetDateDialog : DialogFragment() {
    var districtIndex=0;
    lateinit var districtButton : Button
    val list = arrayOf("当日","前日")

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {
            val builder = AlertDialog.Builder(it)
            builder.setTitle("地区を選択してください")
                .setItems(list,
                    DialogInterface.OnClickListener { dialog, which ->
                        // The 'which' argument contains the index position
                        // of the selected item
                        (activity as SettingsActivity).setDateComment(list[which])
                        (activity as SettingsActivity).updateList()
                        (activity as SettingsActivity).date = which

                    })
            builder.create()
        } ?: throw IllegalStateException("Activity cannot be null")
    }
}