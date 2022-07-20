package com.example.lesson13

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import com.example.lesson13.databinding.FragmentDetailBinding
import java.io.File
import java.io.FileInputStream
import java.io.FileNotFoundException
import java.io.FileOutputStream
import kotlin.math.max

class DetailFragment : Fragment() {
    private var nameFile: String? = DEFAULT_NAME_FILE

    private var bindingDetail: FragmentDetailBinding? = null

    private var fragmentRenameTitleListener: OnFragmentRenameTitleListener? = null
    private var fragmentNavigationListener: OnFragmentNavigationListener? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        fragmentRenameTitleListener = context as? OnFragmentRenameTitleListener
            ?: error("$context${resources.getString(R.string.exceptionInterface)}")

        fragmentNavigationListener = context as? OnFragmentNavigationListener
            ?: error("$context${resources.getString(R.string.exceptionInterface)}")
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): ConstraintLayout? {
        bindingDetail = FragmentDetailBinding.inflate(layoutInflater)

        openText()

        return bindingDetail?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        bindingDetail?.btnSave?.setOnClickListener {
            saveText()

            fragmentNavigationListener?.finishDetailFragment()
        }
    }

    override fun onStart() {
        super.onStart()
        fragmentRenameTitleListener?.renameFragmentTitle(resources.getString(R.string.text_edit_editing))
    }

    override fun onDestroyView() {
        super.onDestroyView()
        bindingDetail = null
    }

    override fun onDetach() {
        super.onDetach()
        fragmentRenameTitleListener = null
        fragmentNavigationListener = null
    }

    private fun checkingNameForUniqueness(currentFileName: String): String {
        val directory = File("${requireContext().filesDir}$KEY_FOLDER_NAME")
        val files: Array<File> = directory.listFiles() as Array<File>

        var secondPathFirstLine: String
        var arrForPattern: Array<String>

        var newNameFile = currentFileName
        var maxNumber = 0

        for (i in files.indices) {
            val file = files[i]
            if (file.name.contains(currentFileName)) {
                secondPathFirstLine =
                    file.name.substring(currentFileName.length, file.name.length)

                if (secondPathFirstLine.contains(Regex("\\(\\d+\\)$"))) {
                    arrForPattern = secondPathFirstLine.split(Regex("[()]")).toTypedArray()
                    arrForPattern[1].toIntOrNull()?.let { maxNumber = max(maxNumber, it) }
                } else if (secondPathFirstLine.trim().isEmpty() && maxNumber == 0) {
                    newNameFile = "$currentFileName (1)"
                }
            }
        }

        if (maxNumber != 0) {
            newNameFile = currentFileName + " (${maxNumber + 1})"
        }

        return newNameFile
    }

    private fun saveText() {
        val currentFile = File("${requireContext().filesDir}$KEY_FOLDER_NAME/$nameFile")
        var renameFile = currentFile
        val newNameFile: String

        val inputText = bindingDetail?.editTextFile?.text.toString()
        var outputText: String? = null
        var allFileText = bindingDetail?.editTextFile?.text.toString()
        var firstLine = allFileText.split(SLASH_N)[0]

        if (firstLine == TXT_EMPTY) {
            firstLine = DEFAULT_NAME_FILE
            outputText = firstLine + inputText
        }

        if (nameFile != firstLine || nameFile == DEFAULT_NAME_FILE) {
            newNameFile = checkingNameForUniqueness(firstLine)
            renameFile = File("${requireContext().filesDir}$KEY_FOLDER_NAME/$newNameFile")
            try {
                allFileText =
                    newNameFile + SLASH_N + inputText.split(SLASH_N)[1]
            } catch (e: Exception) {
                allFileText = newNameFile
            }
        } else {
            allFileText = inputText
        }

        bindingDetail?.editTextFile?.setText(outputText)

        try {
            FileOutputStream(currentFile).use { oStream ->
                oStream.write(allFileText.toByteArray())

                currentFile.renameTo(renameFile)
            }
        } catch (fnfe: FileNotFoundException) {
            fnfe.printStackTrace()
        }
    }

    private fun openText() {
        nameFile = arguments?.getString(KEY_TRANSFER_NAME, TXT_EMPTY)

        val file = File("${requireContext().filesDir}$KEY_FOLDER_NAME/$nameFile")
        try {
            FileInputStream(file).use { iStream ->
                val byte = ByteArray(iStream.available())
                iStream.read(byte)
                val t = String(byte)
                bindingDetail?.editTextFile?.setText(t, TextView.BufferType.EDITABLE)
                println(t)
            }
        } catch (fnfe: FileNotFoundException) {
            fnfe.printStackTrace()
        }
    }

}