package com.example.cn333as2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.InputType
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import com.example.cn333as2.databinding.MainActivityBinding
import com.example.cn333as2.ui.main.MainFragment

class MainActivity : AppCompatActivity() {
    private  lateinit var  binding:MainActivityBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = MainActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.add_button, MainFragment.newInstance())
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
        }
        builder.create().show()
    }

}