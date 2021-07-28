package com.example.hachiojicalendar

import android.app.AlertDialog
import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import android.os.Parcel
import android.os.Parcelable
import android.widget.Button
import androidx.appcompat.app.AppCompatDialog
import androidx.fragment.app.DialogFragment

class DistrictDialog : DialogFragment() {
    var districtIndex=0;
    lateinit var districtButton : Button
    val districtList = arrayOf("地区1", "地区2","地区3","地区4","地区5","地区6","地区7","地区8","地区9",
        "地区10","地区11","地区12","地区13","地区14","地区15","地区16","地区17","地区18","地区19")

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {
            val builder = AlertDialog.Builder(it)
            builder.setTitle("地区を選択してください")
                .setItems(districtList,
                    DialogInterface.OnClickListener { dialog, which ->
                        // The 'which' argument contains the index position
                        // of the selected item
                        districtIndex = which
                        (activity as MainActivity).setDistrictIndex(districtIndex)
                        districtButton.text = districtList[which]
                        districtIndex = which
                        (activity as MainActivity).updateMonthView()
                    })
            builder.create()
        } ?: throw IllegalStateException("Activity cannot be null")
    }
}