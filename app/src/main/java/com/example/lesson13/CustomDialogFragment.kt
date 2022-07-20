package com.example.lesson13

import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.os.Bundle
import androidx.fragment.app.DialogFragment

class CustomDialogFragment() : DialogFragment() {
    private var title: String = ""
    private var message: String = ""
    private var cancelOrNot: Boolean = false

    private var fragmentNavigationListener: OnFragmentNavigationListener? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        fragmentNavigationListener = context as? OnFragmentNavigationListener
            ?: error("$context${resources.getString(R.string.exceptionInterface)}")
    }

    private fun setInfoAboutDialog() {
        val g = arguments?.getStringArrayList(KEY_INFO_DIALOG)
        title = g?.get(0) ?: TXT_EMPTY
        message = g?.get(1) ?: TXT_EMPTY
        cancelOrNot = g?.get(2).toBoolean()
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        setInfoAboutDialog()

        val builder = AlertDialog.Builder(activity)
        return if (cancelOrNot) {
            builder
                .setMessage(message)
                .setPositiveButton(resources.getString(R.string.btn_OK)) { _, _ ->
                    fragmentNavigationListener?.finishAllFragmentsAndExit()
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
        fragmentNavigationListener = null
    }
}