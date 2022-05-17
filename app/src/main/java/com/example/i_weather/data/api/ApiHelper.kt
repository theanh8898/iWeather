package com.example.i_weather.data.api

import com.example.i_weather.data.model.Result
import io.reactivex.Observable

interface ApiHelper {
    fun getWeather(lon: Double, lat: Double): Observable<Result>
}