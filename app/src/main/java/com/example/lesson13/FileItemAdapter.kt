package com.example.lesson13

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.lesson13.databinding.ListItemBinding

class FileItemAdapter(
    private val context: Context,
    private val fileItemList: List<CurrentFile>,
    private val listenerForFragment: OnFragmentOpenFileListener?
) : RecyclerView.Adapter<FileItemAdapter.FileItemViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FileItemViewHolder {
        val binding = ListItemBinding.inflate(LayoutInflater.from(context), parent, false)
        return FileItemViewHolder(binding, listenerForFragment)
    }

    override fun onBindViewHolder(holder: FileItemViewHolder, position: Int) {
        val fileItem = fileItemList[position]
        holder.bind(fileItem)
    }

    override fun getItemCount(): Int {
        return fileItemList.size
    }

    class FileItemViewHolder(
        private val binding: ListItemBinding,
        private val listenerForFragment: OnFragmentOpenFileListener?
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(fileItem: CurrentFile) {
            binding.name.text = fileItem.name
            binding.data.text = fileItem.data
            binding.oneItem.setOnClickListener {
                listenerForFragment?.openFileByName(fileItem.name)
            }
        }
    }
}