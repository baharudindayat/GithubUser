package com.baharudindayat.githubuser.ui

import android.app.SearchManager
import android.content.Context
import android.os.Bundle
import android.view.Menu
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.baharudindayat.githubuser.R
import com.baharudindayat.githubuser.adapter.MainAdapter
import com.baharudindayat.githubuser.apiservice.ItemsItem
import com.baharudindayat.githubuser.databinding.ActivityMainBinding
import com.baharudindayat.githubuser.viewmodel.MainViewModel
import com.google.android.material.snackbar.Snackbar

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val mainViewModel by viewModels<MainViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        val layoutManager = LinearLayoutManager(this)
        binding.rvItem.layoutManager = layoutManager
        val itemDecoration = DividerItemDecoration(this, layoutManager.orientation)
        binding.rvItem.addItemDecoration(itemDecoration)


        //mainviewmodel
        mainViewModel.items.observe(this){ Items ->
            setItemData(Items)
        }

        mainViewModel.isLoading.observe(this){
            showLoading(it)
        }

        mainViewModel.snackbarText.observe(this) {
            it.getContentIfNotHandled()?.let { snackBarText ->
                Snackbar.make(
                    window.decorView.rootView,
                    snackBarText,
                    Snackbar.LENGTH_SHORT
                ).show()
            }
        }

    }

    private fun setItemData(Items: List<ItemsItem>){
        val adapterMain = MainAdapter(Items)
        binding.rvItem.adapter = adapterMain

    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    //searchbar
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.option_menu, menu)

        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        val searchView = menu.findItem(R.id.search).actionView as SearchView

        searchView.setSearchableInfo(searchManager.getSearchableInfo(componentName))
        searchView.queryHint = resources.getString(R.string.search_hint)
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {

            override fun onQueryTextSubmit(query: String): Boolean {
                Toast.makeText(this@MainActivity, query, Toast.LENGTH_SHORT).show()
                mainViewModel.findUser(query)
                return true
            }


            override fun onQueryTextChange(newText: String): Boolean {
                return false
            }
        })
        return true
    }


}