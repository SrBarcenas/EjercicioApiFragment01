package com.example.ejercicioapifragment.models

data class FlavorTextEntries(
    val flavor_text: String,
    val language: Language
){
    val description: String
        get(){
            if(language.name == "es"){
                return flavor_text
            }else{
                return ""
            }
        }
}
