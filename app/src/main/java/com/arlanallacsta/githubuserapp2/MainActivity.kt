package com.arlanallacsta.githubuserapp2


import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import android.view.KeyEvent
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.arlanallacsta.githubuserapp2.ConstValue.EXTRA_USERNAME
import com.arlanallacsta.githubuserapp2.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: UserViewModel
    private lateinit var adapter: UsersAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        adapter = UsersAdapter()
        adapter.notifyDataSetChanged()

        adapter.setOnItemClickCallback(object : UsersAdapter.OnItemClickCallback {
            override fun onItemClicked(data: ItemsItem) {
                val intent = Intent(this@MainActivity, DetailUserActivity::class.java)
                intent.putExtra(EXTRA_USERNAME, data.login)
                startActivity(intent)
            }

        })
        viewModel = ViewModelProvider(
            this,
            ViewModelProvider.NewInstanceFactory()
        ).get(UserViewModel::class.java)


        val layoutManager = LinearLayoutManager(this@MainActivity)

        binding.rvUsers.layoutManager = layoutManager
        binding.rvUsers.setHasFixedSize(true)
        binding.rvUsers.adapter = adapter


        binding.searchLogo.setOnClickListener {
            searchUsers()
        }

        binding.inputText.setOnKeyListener { v, keyCode, event ->
            if (event.action == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_ENTER) {
                searchUsers()
                return@setOnKeyListener true
            }
            return@setOnKeyListener false

        }

        viewModel.getSearchUsers().observe(this, {
            if (it != null) {
                adapter.setList(it)
                showLoading(false)
            }
        })


    }

    private fun searchUsers() {
        val queryInput = binding.inputText.text.toString()
        if (queryInput.isEmpty()) return showLoading(true)
        viewModel.setSerachUsers(queryInput)
    }


    private fun showLoading(b: Boolean) {
        if (b) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.option_menu, menu)
        return true
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_change_language -> {
                val changeLanguage = Intent(Settings.ACTION_LOCALE_SETTINGS)
                startActivity(changeLanguage)
                return true
            }
            else -> {
                return true
            }
        }
    }
}