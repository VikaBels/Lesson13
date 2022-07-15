package com.example.lesson13

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import kotlin.system.exitProcess

class CustomDialogFragment(var title: String?, var message: String, var cancelOrNot: Boolean) :
    DialogFragment() {
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder = AlertDialog.Builder(activity)
        return if (cancelOrNot) {
            builder
                .setMessage(message)
                .setPositiveButton(resources.getString(R.string.btn_OK)) { _, _ ->
                    exitProcess(-1)
                }
                .setNegativeButton(resources.getString(R.string.btn_CANCEL), null)
                .create()
        } else {
            builder
                .setTitle(title)
                .setMessage(message)
                .setPositiveButton(resources.getString(R.string.btn_OK), null)
                .create()
        }
    }
}