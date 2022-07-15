package com.example.lesson13

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.lesson13.databinding.FragmentMainScreenBinding

class MainScreenFragment : Fragment(), OnFragmentSendDataListener {
    companion object {
        const val KEY_TAG_DIALOG = "custom"
    }

    private var bindingMainScreen: FragmentMainScreenBinding? = null

    private var fragmentSendDataListener: OnFragmentSendDataListener? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        fragmentSendDataListener = context as? OnFragmentSendDataListener
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
        fragmentSendDataListener?.renameFragmentTitle(resources.getString(R.string.app_name))
    }

    override fun onDestroyView() {
        super.onDestroyView()
        bindingMainScreen = null
    }

    override fun onDetach() {
        super.onDetach()
        fragmentSendDataListener = null
    }

    private fun setOnClickListenerButtons() {
        bindingMainScreen?.btnAboutProgram?.setOnClickListener {
            showDialog(
                resources.getString(R.string.about_program),
                resources.getString(R.string.message_about_program),
                false
            )
        }
        bindingMainScreen?.btnAboutAuthor?.setOnClickListener {
            showDialog(
                resources.getString(R.string.about_author),
                resources.getString(R.string.message_about_author),
                false
            )
        }
        bindingMainScreen?.btnExit?.setOnClickListener {
            showDialog(TXT_EMPTY, resources.getString(R.string.message_exit), true)
        }
    }

    private fun showDialog(title: String, message: String, cancel: Boolean) {
        val dialog = CustomDialogFragment(title, message, cancel)
        dialog.show(parentFragmentManager, KEY_TAG_DIALOG)
    }

    override fun onSendData(data: String?) {}
    override fun onFinishDetailFragment() {}
    override fun renameFragmentTitle(title: String) {}
}