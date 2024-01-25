package com.example.ejercicioapifragment.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.ejercicioapifragment.R
import com.example.ejercicioapifragment.models.PokemonListItem

class PokemonListAdapter: RecyclerView.Adapter<PokemonListAdapter.PokemonViewHolder>() {

    private var pokemonList: List<PokemonListItem>? = null
    var onItemClickListener: OnItemClickListener? = null

    fun setData(newPokemonlist: List<PokemonListItem>) {
        pokemonList = newPokemonlist
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): PokemonListAdapter.PokemonViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_pokemon, parent, false)
        return PokemonViewHolder(view)
    }

    override fun onBindViewHolder(holder: PokemonListAdapter.PokemonViewHolder, position: Int) {
        val pokemon = pokemonList?.get(position)
        pokemon?.let{
            holder.bind(it)
        }

        // Configurar el listener para manejar clics en los elementos
        holder.itemView.setOnClickListener {
            if(pokemon != null){
                pokemon.name?.let { it1 -> onItemClickListener?.onItemClick(position, it1) }
            }
        }
    }

    override fun getItemCount(): Int {
        return pokemonList?.size ?: 0
    }

    class PokemonViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        private val textPokemonName: TextView = itemView.findViewById(R.id.textPokemonName)
        private val imgPokemon: ImageView = itemView.findViewById(R.id.imgPokemon)

        fun bind(pokemon: PokemonListItem){
            textPokemonName.text = pokemon.name
            // Cargar la imagen utilizando Glide
            Glide.with(itemView.context)
                .load("https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/" + pokemon.number + ".png")
                .centerCrop()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(imgPokemon)
        }
    }

    interface OnItemClickListener {
        fun onItemClick(position: Int, name: String)
    }

    fun getItemAtPosition(position: Int): PokemonListItem?{
        return pokemonList?.get(position)
    }
}