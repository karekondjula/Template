package com.team2.pokemon.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.transform.CircleCropTransformation
import com.team2.pokemon.R
import com.team2.pokemon.model.Pokemon

class PokemonPagingAdapter :
    PagingDataAdapter<Pokemon, PokemonPagingAdapter.PokemonViewHolder>(REPO_COMPARATOR) {

    inner class PokemonViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        private val idTextView: TextView = view.findViewById(R.id.pokemon_id)
        private val imageView: ImageView = view.findViewById(R.id.pokemon_image)
        private val pokemonTextView: TextView = view.findViewById(R.id.pokemon_name)

        fun bindPokemon(pokemon: Pokemon) {
            idTextView.text = pokemon.id.toString()
            pokemonTextView.text = pokemon.name
            imageView.load(pokemon.sprites.image) {
                crossfade(true)
                transformations(CircleCropTransformation())
            }
        }
    }

    override fun onBindViewHolder(holder: PokemonViewHolder, position: Int) {
        getItem(position)?.let { pokemon ->
            holder.bindPokemon(pokemon)
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