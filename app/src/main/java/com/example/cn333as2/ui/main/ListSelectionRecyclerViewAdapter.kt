package com.example.cn333as2.ui.main

import android.content.ContentValues
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.cn333as2.databinding.ListSelectionViewHolderBinding
import com.example.cn333as2.models.NameList

class ListSelectionRecyclerViewAdapter(private val lists: MutableList<NameList>,
                                       private val clickListener: ListSelectionRecyclerViewClickListener,
                                       private val holdClickListener: ListSelectionRecyclerViewClickListener
                                       ) : RecyclerView.Adapter<ListSelectionViewHolder>() {


    interface ListSelectionRecyclerViewClickListener {
        fun listItemClicked(list: NameList)
        fun listItemHold(list: NameList)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListSelectionViewHolder {
        val binding = ListSelectionViewHolderBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ListSelectionViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ListSelectionViewHolder, position: Int) {
        holder.binding.itemName.text = lists[position].name
        holder.itemView.setOnClickListener {
            clickListener.listItemClicked(lists[position])
        }
        holder.itemView.setOnLongClickListener {
            holdClickListener.listItemHold(lists[position])
            true
        }
    }

    override fun getItemCount() = lists.size

    fun listsUpdated() {
        notifyItemInserted(lists.size - 1)
    }

    fun listsRemove(posRemove: Int) {
        notifyItemRemoved(posRemove)
    }
}
