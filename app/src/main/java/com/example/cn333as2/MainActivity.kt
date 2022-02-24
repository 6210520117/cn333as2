package com.example.cn333as2

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
            val mainFragment = MainFragment.newInstance()
            mainFragment.clickListener = this
            val fragmentContainer: Int = if (binding.mainFragmentContainer == null) {
                R.id.container
            } else { R.id.main_fragment_container }

            supportFragmentManager.commit {
                setReorderingAllowed(true)
                add(fragmentContainer, mainFragment)
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

        builder.setPositiveButton(getString(R.string.create_list)) {
            dialog, _->
            dialog.dismiss()
            if (viewModel.findList(listTitleEditText.text.toString())){
                Toast.makeText(this,"The note name has been taken", Toast.LENGTH_LONG).show()
            }else {
                val list = NameList(listTitleEditText.text.toString(), "")
                viewModel.createList(list)
                showListDetail(list)
            }
        }
        builder.create().show()
    }

    private fun showListDetail (list:NameList) {
        if (binding.mainFragmentContainer == null){
        val listDetailIntent = Intent(this, ListDetailActivity::class.java)
        listDetailIntent.putExtra(INTENT_LIST_KEY, list)
        startActivity(listDetailIntent)
        }else {
            val bundle = bundleOf(INTENT_LIST_KEY to  list)
            supportFragmentManager.commit {
                setReorderingAllowed(true)
                replace(R.id.list_detail_fragment_container, ListDetailFragment::class.java,bundle,null)
            }
        }
    }

    companion object {
        const val  INTENT_LIST_KEY = "string"
        const val LIST_DETAIL_REQUEST_CODE = 123
    }

    override fun listItemTapped(list: NameList) {
        showListDetail(list)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == LIST_DETAIL_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            data?.let {
                viewModel.updateNoted(data.getParcelableExtra(INTENT_LIST_KEY)!!)
            }
        }
    }

    override fun onBackPressed() {
        val listDetailFragment = supportFragmentManager.findFragmentById(R.id.list_detail_fragment_container)
        if (listDetailFragment == null) {
            super.onBackPressed()
        } else {
            title = resources.getString(R.string.app_name)
            val textEdit: EditText = findViewById(R.id.editText)
            viewModel.saveList(NameList(viewModel.list.name,textEdit.text.toString()))
            textEdit.setText("")
            supportFragmentManager.commit {
                setReorderingAllowed(true)
                remove(listDetailFragment)
            }

            binding.addButton.setOnClickListener {
                showCreateListName()
            }
        }
    }


}