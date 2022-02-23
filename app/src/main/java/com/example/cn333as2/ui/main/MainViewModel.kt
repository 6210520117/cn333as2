package com.example.cn333as2.ui.main

import android.content.SharedPreferences
import androidx.lifecycle.ViewModel
import com.example.cn333as2.models.NameList

class MainViewModel(val sharedPreferences: SharedPreferences) : ViewModel() {

    lateinit var onListAdded: (() -> Unit)

    lateinit var list: NameList

    private val share = sharedPreferences.edit()
    private val text: String = list.content

    val lists: MutableList<NameList> by lazy {
        mutableLists()
    }

    private fun mutableLists(): MutableList<NameList> {
        val sharedPreferencesContents = sharedPreferences.all
        val nameLists = ArrayList<NameList>()

        for (nameList in sharedPreferencesContents) {
            val list = NameList(nameList.key, nameList.value as String)
            nameLists.add(list)
        }


        return nameLists
    }

    fun saveList(list: NameList) {
            share.putString(list.name, text).apply()
            lists.add(list)
            onListAdded.invoke()
    }


}