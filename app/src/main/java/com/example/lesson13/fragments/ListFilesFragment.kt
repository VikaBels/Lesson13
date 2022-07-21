package com.example.lesson13.fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.lesson13.DEFAULT_NAME_FILE
import com.example.lesson13.adapters.FileItemAdapter
import com.example.lesson13.KEY_FOLDER_NAME
import com.example.lesson13.R
import com.example.lesson13.databinding.FragmentListBinding
import com.example.lesson13.interfaces.OnFragmentOpenFileListener
import com.example.lesson13.interfaces.OnFragmentRenameTitleListener
import com.example.lesson13.models.CurrentFile
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*

class ListFilesFragment : Fragment() {
    companion object {
        private const val DATA_PATTERN = "yyyy-MM-dd HH:mm:ss"
    }

    private var listFiles: MutableList<CurrentFile> = mutableListOf()

    private var bindingList: FragmentListBinding? = null

    private var fragmentOpenFileListener: OnFragmentOpenFileListener? = null
    private var fragmentRenameTitleListener: OnFragmentRenameTitleListener? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        fragmentOpenFileListener = context as? OnFragmentOpenFileListener
            ?: error("$context${resources.getString(R.string.exceptionInterface)}")

        fragmentRenameTitleListener = context as? OnFragmentRenameTitleListener
            ?: error("$context${resources.getString(R.string.exceptionInterface)}")
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): ConstraintLayout? {
        bindingList = FragmentListBinding.inflate(layoutInflater)

        findAllFiles()

        setUpAdapter()

        return bindingList?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        bindingList?.btnAdd?.setOnClickListener {
            createFile()
        }
    }

    override fun onStart() {
        super.onStart()
        fragmentRenameTitleListener?.renameFragmentTitle(resources.getString(R.string.text_edit))
    }

    override fun onDestroyView() {
        super.onDestroyView()
        bindingList = null
    }

    override fun onDetach() {
        super.onDetach()
        fragmentOpenFileListener = null
        fragmentRenameTitleListener = null
    }

    private fun createFile() {
        val newFile = File("${requireContext().filesDir}$KEY_FOLDER_NAME/$DEFAULT_NAME_FILE")
        try {
            newFile.createNewFile()
        } catch (ex: IOException) {
            println(ex)
        }
        fragmentOpenFileListener?.openFileByName(null)
    }

    private fun setUpAdapter() {
        val adapter = FileItemAdapter(requireContext(), listFiles, fragmentOpenFileListener)

        bindingList?.filesList?.adapter = adapter
        bindingList?.filesList?.layoutManager = LinearLayoutManager(requireContext())
    }

    private fun findAllFiles() {
        val dir = File("${requireContext().filesDir}$KEY_FOLDER_NAME")
        val path = dir.toString()
        if (!dir.mkdir()) {
            val directory = File(path)
            val files: Array<File> = directory.listFiles()
            if (files.isEmpty()) {
                Toast.makeText(
                    requireContext(),
                    resources.getString(R.string.noFileInFolder),
                    Toast.LENGTH_SHORT
                ).show()
            } else {
                for (i in files.indices) {
                    listFiles.add(
                        CurrentFile(
                            files[i].name,
                            SimpleDateFormat(
                                DATA_PATTERN,
                                Locale.CANADA
                            ).format(Date(files[i].lastModified()))
                        )
                    )
                }
            }
        }
    }
}