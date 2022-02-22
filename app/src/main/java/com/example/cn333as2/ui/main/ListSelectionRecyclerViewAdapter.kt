package com.example.cn333as2.ui.main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.cn333as2.databinding.ListSelectionViewHolderBinding
import com.example.cn333as2.models.NameList

class ListSelectionRecyclerViewAdapter(private val lists: MutableList<NameList>,
                                       val clickListener: ListSelectionRecyclerViewClickListener
                                       ) : RecyclerView.Adapter<ListSelectionViewHolder>() {



    interface  ListSelectionRecyclerViewClickListener {
        fun listItemClicked(list: NameList)
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListSelectionViewHolder {
        val binding = ListSelectionViewHolderBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ListSelectionViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ListSelectionViewHolder, position: Int) {
        holder.binding.itemName.text = lists[position].name
        holder.itemView.setOnClickListener {
            clickListener.listItemClicked(lists[position])
        }
    }

    override fun getItemCount() = lists.size

    fun listsUpdated() {
        notifyItemInserted(lists.size - 1)
    }
}
