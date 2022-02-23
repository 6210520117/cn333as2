package com.example.cn333as2.ui.detail

import android.content.ContentValues
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import com.example.cn333as2.MainActivity
import com.example.cn333as2.R
import com.example.cn333as2.models.NameList
import com.example.cn333as2.ui.main.MainViewModel



class ListDetailFragment : Fragment() {

    companion object {
        fun newInstance() = ListDetailFragment()
    }

    private lateinit var viewModel: MainViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.main_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(requireActivity()).get(MainViewModel::class.java)
        val list: NameList? = arguments?.getParcelable(MainActivity.INTENT_LIST_KEY)

        list?.let{
            viewModel.list = list
            requireActivity().title = list.name
            val text:EditText = requireActivity().findViewById(R.id.edittext)
            val sharedPreferences = viewModel.sharedPreferences
            val contented = sharedPreferences.getString(viewModel.list.name,"Not found")
            text.setText(contented)
        }

    }


}