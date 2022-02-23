package com.example.cn333as2.ui.main

import android.content.SharedPreferences
import androidx.lifecycle.ViewModel
import com.example.cn333as2.models.NameList

class MainViewModel(private val sharedPreferences: SharedPreferences) : ViewModel() {
    lateinit var  onListAdded: (() -> Unit)

    val lists: MutableList<NameList> by lazy {
        mutableLists()
    }

    private fun mutableLists() :MutableList<NameList>{
        val sharedPreferencesContents = sharedPreferences.all
        val nameLists = ArrayList<NameList>()

        for (nameList in sharedPreferencesContents) {
            val itemsHashSet = ArrayList(nameList.value as HashSet<String>)
            val list = NameList(nameList.key, itemsHashSet)
            nameLists.add(list)
        }

        return nameLists
    }

    fun saveList(list: NameList) {
        sharedPreferences.edit().putStringSet(list.name, list.tasks.toHashSet()).apply()
        lists.add(list)
        onListAdded.invoke()
    }
}