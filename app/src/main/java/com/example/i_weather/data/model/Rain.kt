package com.example.i_weather.data.model

import com.google.gson.annotations.SerializedName

data class Rain(
    @SerializedName("1h") var rainProb: Double? = null
)