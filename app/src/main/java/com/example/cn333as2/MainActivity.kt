package com.example.cn333as2

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.InputType
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.core.os.bundleOf
import androidx.fragment.app.commit
import androidx.lifecycle.ViewModelProvider
import androidx.preference.PreferenceManager
import com.example.cn333as2.databinding.MainActivityBinding
import com.example.cn333as2.models.NameList
import com.example.cn333as2.ui.detail.ListDetailFragment
import com.example.cn333as2.ui.main.MainFragment
import com.example.cn333as2.ui.main.MainViewModel
import com.example.cn333as2.ui.main.MainViewModelFactory

class MainActivity : AppCompatActivity(), MainFragment.MainFragmentInteractionListener {
    private lateinit var binding: MainActivityBinding
    private lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(
            this,
            MainViewModelFactory(PreferenceManager.getDefaultSharedPreferences(this))
        )
            .get(MainViewModel::class.java)

        binding = MainActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (savedInstanceState == null) {
            val mainFragment = MainFragment.newInstance(this)

            val fragmentContainerViewId: Int = if (binding.mainFragment == null) {
                R.id.container
            } else {
                R.id.main_fragment
            }

            supportFragmentManager.commit {
                setReorderingAllowed(true)
                add(fragmentContainerViewId, mainFragment)
            }
        }

        binding.addButton.setOnClickListener {
            showCreateListName()
        }
    }

    private fun showCreateListName() {
        val builder = AlertDialog.Builder(this)
        val listTitleEditText = EditText(this)
        listTitleEditText.inputType = InputType.TYPE_CLASS_TEXT

        builder.setTitle(getString(R.string.name_of))
        builder.setView(listTitleEditText)
        builder.setPositiveButton(getString(R.string.create_list)) { dialog, _ ->
            dialog.dismiss()
//            viewModel.saveList(NameList(listTitleEditText.text.toString()))
//            showListDetail(NameList(listTitleEditText.text.toString()))
        }
        builder.create().show()
    }

    private fun showListDetail(list: NameList) {
        if (binding.mainFragment == null) {
            val listDetailIntent = Intent(this, ListDetailActivity::class.java)
            listDetailIntent.putExtra(INTENT_LIST_KEY, list)
            startActivity(listDetailIntent)
        } else {
            val bundle = bundleOf(INTENT_LIST_KEY to list)
            supportFragmentManager.commit {
                setReorderingAllowed(true)
                replace(R.id.list_detail_fragment, ListDetailFragment::class.java, bundle, null)
            }
        }
    }

    companion object {
        const val INTENT_LIST_KEY = "list"
    }

    override fun listItemTapped(list: NameList) {
        showListDetail(list)
    }

    @SuppressLint("WrongViewCast")
    override fun onBackPressed() {
        val listNoteFragment = supportFragmentManager.findFragmentById(R.id.list_detail_fragment)
        if (listNoteFragment == null) {
            super.onBackPressed()
        } else {
            title = resources.getString(R.string.app_name)
            val editT: EditText = findViewById(R.id.edittext)
            viewModel.saveList(NameList(viewModel.list.name, editT.text.toString()))
            editT.setText("")
            supportFragmentManager.commit {
                setReorderingAllowed(true)
                remove(listNoteFragment)
            }
            binding.addButton.setOnClickListener {
                showCreateListName()
            }
        }
    }
}