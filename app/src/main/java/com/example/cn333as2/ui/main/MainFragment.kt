package com.example.cn333as2.ui.main

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.preference.PreferenceManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.cn333as2.R
import com.example.cn333as2.databinding.MainFragmentBinding
import com.example.cn333as2.models.NameList

class MainFragment() : Fragment(), ListSelectionRecyclerViewAdapter.ListSelectionRecyclerViewClickListener{
    private lateinit var binding:MainFragmentBinding
    var clickListener: MainFragmentInteractionListener? = null
    var holdClickListener: MainFragmentInteractionListener? = null

    interface MainFragmentInteractionListener {
        fun listItemTapped(list: NameList)
        fun listItemHold(list: NameList)
    }

    companion object {
        fun newInstance() = MainFragment()
    }

    private lateinit var viewModel: MainViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = MainFragmentBinding.inflate(inflater, container, false)
        binding.listName.layoutManager = LinearLayoutManager(requireContext())
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(requireActivity(),
            MainViewModelFactory(PreferenceManager.getDefaultSharedPreferences(requireActivity())))
            .get(MainViewModel::class.java)

        val recyclerViewAdapter = ListSelectionRecyclerViewAdapter(viewModel.lists, this,this)
        binding.listName.adapter = recyclerViewAdapter
        viewModel.onListAdded = {
            recyclerViewAdapter.listsUpdated()
        }
        viewModel.onListRemoved ={
            recyclerViewAdapter.listsRemove(viewModel.whereRemoved)

        }
    }

    override fun listItemClicked(list: NameList) {
        clickListener?.listItemTapped(list)
    }

    override fun listItemHold(list: NameList) {
        holdClickListener?.listItemHold(list)
    }

}