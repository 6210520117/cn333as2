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
import com.example.cn333as2.databinding.ListDetailFragmentBinding
import com.example.cn333as2.ui.main.MainViewModel
import org.w3c.dom.NameList



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
        val cont: NameList? = arguments?.getParcelable(MainActivity.INTENT_LIST_KEY)

        cont?.let{
            viewModel.list = cont
            requireActivity().title = cont.name
            val text:EditText = requireActivity().findViewById(R.id.editText)
            val sharedPreferences = viewModel.sharedPreferences
            val contented = sharedPreferences.getString(viewModel.list.name,"Not found")
            Log.d(ContentValues.TAG, cont.content)
            text.setText(contented)
        }

    }


}