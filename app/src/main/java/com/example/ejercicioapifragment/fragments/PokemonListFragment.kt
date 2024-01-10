package com.example.ejercicioapifragment.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.ejercicioapifragment.R
import com.example.ejercicioapifragment.adapters.PokemonListAdapter
import com.example.ejercicioapifragment.models.PokemonListResponse
import com.example.ejercicioapifragment.networks.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PokemonListFragment : Fragment(), PokemonListAdapter.OnItemClickListener {

    private lateinit var recyclerView: RecyclerView
    private val pokemonListAdapter = PokemonListAdapter()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_pokemon_list, container, false)

        recyclerView = view.findViewById(R.id.recyclerView)

        val spanCount = 3 // Puedes ajustar el número de columnas según tus necesidades
        val layoutManager = GridLayoutManager(requireContext(), spanCount)

        recyclerView.layoutManager = layoutManager
        recyclerView.adapter = pokemonListAdapter

        pokemonListAdapter.onItemClickListener = this

        return view
    }

    private fun fetchPokemonList(){
        val pokeApiService = RetrofitClient.instance
        val call = pokeApiService.getPokemonList(151) // Obtener la lista de los primeros 151 Pokémon

        call.enqueue(object : Callback<PokemonListResponse> {
            override fun onResponse(call: Call<PokemonListResponse>, response: Response<PokemonListResponse>){
                if(response.isSuccessful){
                    val pokemonList = response.body()?.results
                    pokemonListAdapter.setData(pokemonList)
                }
            }

            override fun onFailure(call: Call<PokemonListResponse>, t: Throwable) {
                //Manejar el fallo de la llamada
            }
        })
    }

    // Manejar el clic en el elemento de la lista, por ejemplo, abrir los detalles del Pokémon
    // Realizar alguna acción con el Pokémon seleccionado, como abrir un nuevo fragmento
    // o iniciar una actividad con los detalles del Pokémon
    override fun onItemClick(position: Int, name: String) {
        val bundle = Bundle()
        val selectPokemon = pokemonListAdapter.getItemAtPosition(position)
        val pokemonId: Int? = selectPokemon?.number

        if(pokemonId != null){
            bundle.putInt("pokemonId", pokemonId)
            bundle.putString("name", name)
        }

        val navController = findNavController()
        // Navegar al destino deseado
        navController.navigate(R.id.action_pokemonListFragment_to_pokemonDetailsFragment, bundle)
    }
}