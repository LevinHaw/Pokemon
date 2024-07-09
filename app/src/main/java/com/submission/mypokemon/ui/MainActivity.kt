package com.submission.mypokemon.ui

import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.submission.mypokemon.R
import com.submission.mypokemon.adapter.PokemonAdapter
import com.submission.mypokemon.data.response.ResultsItem
import com.submission.mypokemon.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var mainViewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        enableEdgeToEdge()

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        mainViewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory())[MainViewModel::class.java]

        val layoutManager = LinearLayoutManager(this)
        binding.rvPokemon.layoutManager = layoutManager

        mainViewModel.getIsLoading.observe(this) {
            showLoading(it)
        }

        mainViewModel.getListPokemon.observe(this) { items ->
            setListPokemon(items)
        }
    }

    private fun setListPokemon(items: List<ResultsItem>){
        val adapter = PokemonAdapter()
        adapter.submitList(items)
        binding.rvPokemon.adapter = adapter
    }


    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }
}