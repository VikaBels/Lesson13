package com.example.lesson13

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.lesson13.databinding.NameItemMenuBinding

class MenuItemAdapter(
    private val context: Context,
    private val menuItemList: List<ItemMenu>,
    private val listenerForFragment: OnFragmentNavigationListener?,
) : RecyclerView.Adapter<MenuItemAdapter.MenuItemViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MenuItemViewHolder {
        val binding = NameItemMenuBinding.inflate(LayoutInflater.from(context), parent, false)
        return MenuItemViewHolder(binding, listenerForFragment)
    }

    override fun onBindViewHolder(holder: MenuItemViewHolder, position: Int) {
        val fileItem = menuItemList[position]
        holder.bind(fileItem)
    }

    override fun getItemCount(): Int {
        return menuItemList.size
    }

    class MenuItemViewHolder(
        private val binding: NameItemMenuBinding,
        private val listenerForFragment: OnFragmentNavigationListener?
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(menuItem: ItemMenu) {
            binding.name.text = menuItem.name
            binding.nameItemMenu.setOnClickListener {
                listenerForFragment?.openFragmentByTag(menuItem.tag)
            }
        }
    }
}