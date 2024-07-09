package com.submission.mypokemon.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.submission.mypokemon.data.response.ResultsItem
import com.submission.mypokemon.databinding.ItemPokemonBinding
import com.submission.mypokemon.ui.DetailActivity

class PokemonAdapter : ListAdapter<ResultsItem, PokemonAdapter.ListViewHolder>(DIFF_CALLBACK) {
    private var onItemClickCallback: OnItemClickCallback? = null

    fun setOnItemClickCallback (onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val binding = ItemPokemonBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val user = getItem(position)
        holder.bind(user)
    }

    class ListViewHolder(private val binding: ItemPokemonBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(pokemon: ResultsItem){
            binding.root.setOnClickListener {
                val intent = Intent(itemView.context, DetailActivity::class.java)
                intent.putExtra(DetailActivity.ITEM_ID, pokemon.name)
                itemView.context.startActivity(intent)
            }
            binding.tvName.text = pokemon.name
        }
    }

    companion object{
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<ResultsItem>(){
            override fun areItemsTheSame(oldItem: ResultsItem, newItem: ResultsItem): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: ResultsItem, newItem: ResultsItem): Boolean {
                return oldItem == newItem
            }
        }
    }

    interface OnItemClickCallback{
        fun onItemClicked(data: ResultsItem)
    }
}