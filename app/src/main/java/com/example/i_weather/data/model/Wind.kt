package com.example.i_weather.data.model

import com.google.gson.annotations.SerializedName

data class Wind(
    @SerializedName("speed") var speed: Double? = null,
    @SerializedName("deg") var deg: Int? = null
)