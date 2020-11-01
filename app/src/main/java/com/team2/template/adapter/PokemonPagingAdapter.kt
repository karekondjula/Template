package com.team2.template.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.team2.template.R
import com.team2.template.model.Pokemon

class PokemonPagingAdapter :
    PagingDataAdapter<Pokemon, PokemonPagingAdapter.PokemonViewHolder>(REPO_COMPARATOR) {

    inner class PokemonViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        private val idTextView: TextView = view.findViewById(R.id.pokemon_id)
        private val pokemonTextView: TextView = view.findViewById(R.id.pokemon_name)

        fun bindPokemon(position: Int, pokemon: Pokemon) {
            idTextView.text = position.toString()
            pokemonTextView.text = pokemon.name
        }
    }

    override fun onBindViewHolder(holder: PokemonViewHolder, position: Int) {
        getItem(position)?.let { pokemon ->
            holder.bindPokemon(position, pokemon)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PokemonViewHolder {
        return PokemonViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_pokemon,
                parent,
                false
            )
        )
    }

    companion object {
        private val REPO_COMPARATOR = object : DiffUtil.ItemCallback<Pokemon>() {
            override fun areItemsTheSame(oldItem: Pokemon, newItem: Pokemon): Boolean =
                oldItem.name == newItem.name

            override fun areContentsTheSame(oldItem: Pokemon, newItem: Pokemon): Boolean =
                oldItem == newItem
        }
    }
}