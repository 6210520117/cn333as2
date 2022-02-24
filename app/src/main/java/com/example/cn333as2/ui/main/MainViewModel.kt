package com.example.cn333as2.ui.main

import android.content.ContentValues
import android.content.SharedPreferences
import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.cn333as2.models.NameList

class MainViewModel(val sharedPreferences: SharedPreferences) : ViewModel() {
    lateinit var onListAdded: (() -> Unit)
    lateinit var list: NameList
    val lists: MutableList<NameList> by lazy {
        mutableLists()
    }

    private fun mutableLists(): MutableList<NameList> {
        val sharedPreferencesContents = sharedPreferences.all
        val nameLists = ArrayList<NameList>()

        for (nameList in sharedPreferencesContents) {
            try {
            val list = NameList(nameList.key, nameList.value as String)
            nameLists.add(list) } catch (e: ClassCastException) {
            }
        }

        return nameLists
    }

    fun saveList(list: NameList) {
        sharedPreferences.edit().putString(list.name, list.content).apply()
    }

    fun updateNoted(list: NameList) {

        sharedPreferences.edit().putString(list.name, list.content).apply()
        lists.clear()
        lists.addAll(mutableLists())
    }

    fun findList(key: String): Boolean{
        return sharedPreferences.contains(key)
    }

    fun createList(list: NameList) {
        sharedPreferences.edit().putString(list.name, list.content).apply()
        lists.add(list)
        onListAdded.invoke()

    }

}