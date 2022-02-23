package com.example.cn333as2

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.InputType
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.ViewModelProvider
import androidx.preference.PreferenceManager
import com.example.cn333as2.databinding.MainActivityBinding
import com.example.cn333as2.models.NameList
import com.example.cn333as2.ui.main.MainFragment
import com.example.cn333as2.ui.main.MainViewModel
import com.example.cn333as2.ui.main.MainViewModelFactory

class MainActivity : AppCompatActivity(), MainFragment.MainFragmentInteractionListener {
    private  lateinit var  binding: MainActivityBinding
    private  lateinit var  viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this,
            MainViewModelFactory(PreferenceManager.getDefaultSharedPreferences(this))
        )
            .get(MainViewModel::class.java)

        binding = MainActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.add_button, MainFragment.newInstance(this))
                .commitNow()
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
        builder.setPositiveButton(getString(R.string.create_list)) {
            dialog, _->
            dialog.dismiss()
            viewModel.saveList(NameList(listTitleEditText.text.toString()))
            showListDetail(NameList(listTitleEditText.text.toString()))
        }
        builder.create().show()
    }

    private fun showListDetail (list:NameList) {
        val listDetailIntent = Intent(this, ListDetailActivity::class.java)
        listDetailIntent.putExtra(INTENT_LIST_KEY, list)
        startActivity(listDetailIntent)
    }

    companion object {
        const val  INTENT_LIST_KEY = "string"
    }

    override fun listItemTapped(list: NameList) {
        showListDetail(list)
    }
}