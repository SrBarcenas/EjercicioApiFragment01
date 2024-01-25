package com.example.ejercicioapifragment.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.ejercicioapifragment.R
import com.example.ejercicioapifragment.models.PokemonDetails
import com.example.ejercicioapifragment.networks.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class PokemonDetailsFragment : Fragment() {

    private lateinit var imgDetailPokemon: ImageView
    private lateinit var textName: TextView
    private lateinit var textDescription: TextView
    private var pokemonId = 0
    private var pokemonName = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_pokemon_details, container, false)

        imgDetailPokemon = view.findViewById(R.id.imgDetailPokemon)
        textName = view.findViewById(R.id.textName)
        textDescription = view.findViewById(R.id.textDescription)

        pokemonId = arguments?.getInt("pokemonId") ?: 1
        pokemonName = arguments?.getString("pokemonName").toString()

        fetchPokemonDetails(pokemonId)
        return view
    }

    private fun fetchPokemonDetails(pokemonId: Int){
        val pokeApiService = RetrofitClient.instance
        val call = pokeApiService.getPokemonDetails(pokemonId)

        call.enqueue(object : Callback<PokemonDetails>{
            override fun onResponse(
                call: Call<PokemonDetails>,
                response: Response<PokemonDetails>,
            ) {
                if(response.isSuccessful) {
                    val pokemon = response.body()
                    pokemon?.let{
                        displayPokemonDetails(pokemon)
                    }
                }
            }

            override fun onFailure(call: Call<PokemonDetails>, t: Throwable) {
                // Manejar el fallo de la llamada
            }

        })
    }

    private fun displayPokemonDetails(pokemonDetails: PokemonDetails) {
        var resultString = "Descripción:\n\n"

        for(pokemon in pokemonDetails.flavor_text_entries) {
            if (pokemon.description != ""){
                resultString += "- ${pokemon.description}\n"
            }
        }

        textName.text = pokemonName
        textDescription.text = resultString

        this.context?.let{
            Glide.with(it)
                .load("https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/" + pokemonId + ".png")
                .centerCrop()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(imgDetailPokemon)
        }
    }

}