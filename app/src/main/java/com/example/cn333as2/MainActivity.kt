package com.example.cn333as2

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.InputType
import android.widget.EditText
import android.widget.Toast
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
            val mainFragment = MainFragment.newInstance()
            mainFragment.clickListener = this
            mainFragment.holdClickListener = this
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
            if (viewModel.findList(listTitleEditText.text.toString())){
                Toast.makeText(this,"The note name has been taken", Toast.LENGTH_LONG).show()
            }else {
                val noteList = NameList(listTitleEditText.text.toString(), "")
                viewModel.createList(noteList)
                showListDetail(noteList)
            }
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

    private fun showRemoveDialog(list: NameList) {
        val dialogTitle = "You removing note ${list.name}!"
        val positiveButtonTitle = "Yes"
        val negativeButtonTitle = "No"

        val builder = AlertDialog.Builder(this)

        builder.setTitle(dialogTitle)

        builder.setPositiveButton(positiveButtonTitle) { dialog, _ ->
            dialog.dismiss()
            viewModel.removeList(list)

            viewModel = ViewModelProvider(this,
                MainViewModelFactory(android.preference.PreferenceManager.getDefaultSharedPreferences(this))
            )
                .get(MainViewModel::class.java)
            binding = MainActivityBinding.inflate(layoutInflater)
            setContentView(binding.root)

            val mainFragment = MainFragment.newInstance()
            mainFragment.clickListener = this
            mainFragment.holdClickListener = this
            val fragmentContainerViewId: Int = if (binding.mainFragment == null) {
                R.id.container }
            else {
                R.id.main_fragment
            }

            supportFragmentManager.commit {
                setReorderingAllowed(true)
                add(fragmentContainerViewId, mainFragment)
            }


            binding.addButton.setOnClickListener {
                showCreateListName()
            }

        }
        builder.setNegativeButton(negativeButtonTitle) { dialog, _ ->
            dialog.dismiss()
        }

        builder.create().show()
    }

    companion object {
        const val INTENT_LIST_KEY = "list"
        const val LIST_DETAIL_REQUEST_CODE = 123
    }

    override fun listItemTapped(list: NameList) {
        showListDetail(list)
    }

    override fun listItemHold(list: NameList) {
        showRemoveDialog(list)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == LIST_DETAIL_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            data?.let {
                viewModel.updateList(data.getParcelableExtra(INTENT_LIST_KEY)!!)
            }
        }
    }

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