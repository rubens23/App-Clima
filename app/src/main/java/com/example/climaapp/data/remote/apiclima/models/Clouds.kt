package com.example.climaapp.data.remote.apiclima.models

import com.google.gson.annotations.SerializedName

data class Clouds(
    @SerializedName("all")
    val all: Int
)
