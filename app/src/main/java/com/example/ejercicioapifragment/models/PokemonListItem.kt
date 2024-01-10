package com.example.ejercicioapifragment.models

data class PokemonListItem(
    var name: String? = null,
    var url: String? = null
){
    val number: Int
        get(){
            val urlParts = url?.split("/") ?: emptyList()
            val partNumber = urlParts.getOrNull(urlParts.size - 2) ?: "0"

            return if(partNumber.isNotEmpty()){
                Integer.parseInt(partNumber)
            }else{
                0
            }
        }
}
