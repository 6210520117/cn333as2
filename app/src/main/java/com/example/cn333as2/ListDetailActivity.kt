package com.example.cn333as2

import android.content.ContentValues.TAG
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import android.widget.EditText
import com.example.cn333as2.models.NameList
import java.lang.ClassCastException

class ListDetailActivity : AppCompatActivity() {
    lateinit var list : NameList
    lateinit var sharedPreferences: SharedPreferences
    lateinit var textEdit : EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.list_detail_activity)
        list = intent.getParcelableExtra(MainActivity.INTENT_LIST_KEY)!!
        title = list.name
    }

    public override fun onPostCreate(savedInstanceState: Bundle?) {
        sharedPreferences = getSharedPreferences("",MODE_PRIVATE)
        textEdit = findViewById(R.id.editText)
        var text = sharedPreferences.getString(list.name, "")
        textEdit.setText(text)
        super.onPostCreate(savedInstanceState)
    }

    override fun onBackPressed() {
        sharedPreferences = getSharedPreferences("", MODE_PRIVATE)
        textEdit = findViewById(R.id.editText)
        var edited = textEdit.text.toString()
        textEdit.setText(edited)
        sharedPreferences.edit().putString(list.name, edited).apply()
        super.onBackPressed()
    }



}
