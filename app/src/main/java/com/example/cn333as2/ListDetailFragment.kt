package com.example.cn333as2
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.cn333as2.databinding.ListDetailFragmentBinding

class ListDetailFragment : Fragment() {
    private lateinit var binding: ListDetailFragmentBinding
    private lateinit var viewModel: ListDetailViewModel

    companion object {
        fun newInstance() = ListDetailFragment()
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = ListDetailFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(requireActivity()).get(ListDetailViewModel::class.java)
    }

}