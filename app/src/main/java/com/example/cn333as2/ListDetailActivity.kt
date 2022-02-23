package com.example.cn333as2

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import androidx.fragment.app.commit
import androidx.lifecycle.ViewModelProvider
import androidx.preference.PreferenceManager
import com.example.cn333as2.databinding.ListDetailActivityBinding
import com.example.cn333as2.databinding.ListDetailFragmentBinding
import com.example.cn333as2.databinding.MainActivityBinding
import com.example.cn333as2.models.NameList
import com.example.cn333as2.ui.detail.ListDetailFragment
import com.example.cn333as2.ui.main.MainFragment
import com.example.cn333as2.ui.main.MainViewModel
import com.example.cn333as2.ui.main.MainViewModelFactory

class ListDetailActivity : AppCompatActivity() {
    private lateinit var binding: ListDetailFragmentBinding
    private lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel = ViewModelProvider(
            this,
            MainViewModelFactory(PreferenceManager.getDefaultSharedPreferences(this))
        )
            .get(MainViewModel::class.java)

        binding = ListDetailFragmentBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel.list = intent.getParcelableExtra(MainActivity.INTENT_LIST_KEY)!!
        title = viewModel.list.name



        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, ListDetailFragment.newInstance())
                .commitNow()
        }
    }

    override fun onBackPressed() {
        val editNoteText: EditText = findViewById(R.id.editText)

        viewModel.saveList(NameList(viewModel.list.name, editNoteText.text.toString()))

//        Log.d(TAG, viewModel.list.content)
        val bundle = Bundle()
        bundle.putParcelable(MainActivity.INTENT_LIST_KEY, viewModel.list)
        val intent = Intent()
        intent.putExtras(bundle)
        setResult(Activity.RESULT_OK, intent)

        super.onBackPressed()
    }
}