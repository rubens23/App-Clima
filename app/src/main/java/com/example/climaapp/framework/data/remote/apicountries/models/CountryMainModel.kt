package com.example.climaapp.framework.data.remote.apicountries.models

data class CountryMainModel(
    val name: Name,
    val capital: List<String>,
    val altSpellings: List<String>,
    val region: String,
    val subregion: String,
    val translations: Translations,
    val flag: String,
    val flags: Flags,

    )
