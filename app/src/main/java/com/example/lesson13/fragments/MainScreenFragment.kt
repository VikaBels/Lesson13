package com.example.lesson13.fragments

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.lesson13.R
import com.example.lesson13.TXT_EMPTY
import com.example.lesson13.databinding.FragmentMainScreenBinding
import com.example.lesson13.listeners.CustomDialogConfigurationListener
import com.example.lesson13.listeners.OnFragmentRenameTitleListener

class MainScreenFragment : Fragment() {
    private var bindingMainScreen: FragmentMainScreenBinding? = null

    private var fragmentRenameTitleListener: OnFragmentRenameTitleListener? = null
    private var fragmentSendInfoDialogListener: CustomDialogConfigurationListener? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        fragmentRenameTitleListener = context as? OnFragmentRenameTitleListener
            ?: error("$context${resources.getString(R.string.exceptionInterface)}")

        fragmentSendInfoDialogListener = context as? CustomDialogConfigurationListener
            ?: error("$context${resources.getString(R.string.exceptionInterface)}")
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        bindingMainScreen = FragmentMainScreenBinding.inflate(layoutInflater)

        return bindingMainScreen?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setOnClickListenerButtons()
    }

    override fun onStart() {
        super.onStart()
        fragmentRenameTitleListener?.renameFragmentTitle(resources.getString(R.string.app_name))
    }

    override fun onDestroyView() {
        super.onDestroyView()
        bindingMainScreen = null
    }

    override fun onDetach() {
        super.onDetach()
        fragmentRenameTitleListener = null
        fragmentSendInfoDialogListener = null
    }

    private fun setOnClickListenerButtons() {
        bindingMainScreen?.btnAboutProgram?.setOnClickListener {
            sendInfoOnDialog(
                resources.getString(R.string.about_program),
                resources.getString(R.string.message_about_program),
                false
            )
        }
        bindingMainScreen?.btnAboutAuthor?.setOnClickListener {
            sendInfoOnDialog(
                resources.getString(R.string.about_author),
                resources.getString(R.string.message_about_author),
                false
            )
        }
        bindingMainScreen?.btnExit?.setOnClickListener {
            sendInfoOnDialog(TXT_EMPTY, resources.getString(R.string.message_exit), true)
        }
    }

    private fun sendInfoOnDialog(title: String, message: String, cancel: Boolean) {
        fragmentSendInfoDialogListener?.showDialog(title, message, cancel)
    }
}