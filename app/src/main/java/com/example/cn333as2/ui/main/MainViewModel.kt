package com.example.cn333as2.ui.main

import android.content.ContentValues
import android.content.SharedPreferences
import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.cn333as2.models.NameList

class MainViewModel(val sharedPreferences: SharedPreferences) : ViewModel() {

    lateinit var onListAdded: (() -> Unit)
    lateinit var list: NameList
    private val share = sharedPreferences.edit()
    lateinit var onListRemoved: () -> Unit
    private val text: String = list.content
    var whereRemoved: Int = -1

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

    fun updateList(list: NameList) {
        share.putString(list.name, text).apply()
        lists.add(list)
        refreshLists()
    }


    fun saveList(list: NameList) {
        share.putString(list.name, text).apply()
        lists.add(list)
        onListAdded.invoke()
    }

    fun createList(list: NameList) {
        share.putString(list.name, text).apply()
        lists.add(list)
        onListAdded.invoke()
    }

    fun findList(key: String): Boolean {
        return sharedPreferences.contains(key)
    }

    fun removeList(list: NameList){
        val index = lists.indexOf(list)
        whereRemoved = index
        lists.remove(list)
        onListRemoved.invoke()
        sharedPreferences.edit().remove(list.name).apply()

    }

    private fun refreshLists() {
        lists.clear()
        lists.addAll(mutableLists())
    }
}