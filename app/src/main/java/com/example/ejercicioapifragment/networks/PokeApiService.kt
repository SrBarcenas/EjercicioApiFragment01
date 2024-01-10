package com.example.ejercicioapifragment.networks

import com.example.ejercicioapifragment.models.PokemonDetails
import com.example.ejercicioapifragment.models.PokemonListResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface PokeApiService {
    @GET("pokemon/")
    fun getPokemonList(@Query("limit") limit: Int): Call<PokemonListResponse>

    @GET("pokemon-species/{id}")
    fun getPokemonDetails(@Path("id") id: Int): Call<PokemonDetails>
}