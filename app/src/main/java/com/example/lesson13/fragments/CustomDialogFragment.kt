package com.example.lesson13.fragments

import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import com.example.lesson13.KEY_INFO_DIALOG
import com.example.lesson13.R
import com.example.lesson13.TXT_EMPTY
import com.example.lesson13.listeners.OnFragmentFinishListener

class CustomDialogFragment : DialogFragment() {
    private var title: String = ""
    private var message: String = ""
    private var canCancel: Boolean = false

    private var fragmentFinishListener: OnFragmentFinishListener? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        fragmentFinishListener = context as? OnFragmentFinishListener
            ?: error("$context${resources.getString(R.string.exceptionInterface)}")
    }

    private fun setInfoAboutDialog() {
        val arrayInfoDialog = arguments?.getStringArrayList(KEY_INFO_DIALOG)
        title = arrayInfoDialog?.get(0) ?: TXT_EMPTY
        message = arrayInfoDialog?.get(1) ?: TXT_EMPTY
        canCancel = arrayInfoDialog?.get(2).toBoolean()
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        setInfoAboutDialog()

        val builder = AlertDialog.Builder(activity)
        return if (canCancel) {
            builder
                .setMessage(message)
                .setPositiveButton(resources.getString(R.string.btn_OK)) { _, _ ->
                    fragmentFinishListener?.finishProgram()
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

    override fun onDetach() {
        super.onDetach()
        fragmentFinishListener = null
    }
}