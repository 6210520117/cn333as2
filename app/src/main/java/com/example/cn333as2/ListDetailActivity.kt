package com.example.cn333as2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.cn333as2.models.NameList
import com.example.cn333as2.ui.detail.ListDetailFragment

class ListDetailActivity : AppCompatActivity() {
    lateinit var  list : NameList

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.list_detail_activity)
        list = intent.getParcelableExtra(MainActivity.INTENT_LIST_KEY)!!
        title = list.name

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, ListDetailFragment.newInstance())
                .commitNow()
        }
    }
}