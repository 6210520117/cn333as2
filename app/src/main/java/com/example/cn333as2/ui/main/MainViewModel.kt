package com.example.cn333as2.ui.main

import android.content.SharedPreferences
import androidx.lifecycle.ViewModel
import com.example.cn333as2.models.NameList

class MainViewModel(private val sharedPreferences: SharedPreferences) : ViewModel() {
    lateinit var onListAdded: (() -> Unit)

    lateinit var list: NameList
    val share = sharedPreferences.edit()
    val text: String = list.content

    val lists: MutableList<NameList> by lazy {
        mutableLists()
    }

    private fun mutableLists(): MutableList<NameList> {
        val sharedPreferencesContents = sharedPreferences.all
        val nameLists = ArrayList<NameList>()

        for (nameList in sharedPreferencesContents) {
            val cont = NameList(nameList.key, nameList.value as String)
            nameLists.add(cont)
        }


        return nameLists
    }

    fun createList(list: NameList) {
        share.putString(list.name, text).apply()
        lists.add(list)
        onListAdded.invoke()


        fun saveList(list: NameList) {
            share.putString(list.name, text).apply()
            lists.add(list)
            onListAdded.invoke()
        }
    }
}