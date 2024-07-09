package com.submission.mypokemon.ui

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.bumptech.glide.Glide
import com.submission.mypokemon.R
import com.submission.mypokemon.data.response.DetailResponse
import com.submission.mypokemon.databinding.ActivityDetailBinding

class DetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailBinding

    private val detailViewModel by viewModels<DetailViewModel>()


    companion object{
        const val ITEM_ID = "item_id"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        enableEdgeToEdge()
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val items = intent.getStringExtra(ITEM_ID)

        detailViewModel.setDetailPokemon(detailPokemonName = items.toString())
        supportActionBar?.hide()

        detailViewModel.isLoading.observe(this) {
            showLoading(it)
        }

        detailViewModel.detailPokemon.observe(this) { detailPokemon ->
            setDetailPokemon(detailPokemon)
        }
    }

    private fun setDetailPokemon(detailResponse: DetailResponse) {

        val abilitty = detailResponse.abilities?.joinToString(", "){
            it?.ability?.name.toString()
        }

        val stat = detailResponse.stats?.joinToString(", "){
            "${it?.stat?.name} = ${it?.baseStat}"
        }


        binding?.apply {
            tvName.text = detailResponse.name
            tvShowStats.text = stat
            tvShowAbilitty.text = abilitty
        }

        Glide.with(binding.root.context)
            .load(detailResponse.sprites?.frontDefault)
            .circleCrop()
            .into(binding.ivPokemon)
    }

    private fun showLoading(state: Boolean) {
        binding.progressBar.visibility = if (state) View.VISIBLE else View.GONE
    }
}
