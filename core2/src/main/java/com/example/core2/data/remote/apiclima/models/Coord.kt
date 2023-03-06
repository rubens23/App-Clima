package com.example.core2.data.remote.apiclima.models

import com.google.gson.annotations.SerializedName

data class Coord(
    @SerializedName("lat")
    val latitude: Double,
    @SerializedName("lon")
    val longitude: Double
)
