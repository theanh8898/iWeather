package com.example.i_weather.data.api

import com.example.i_weather.data.model.Result
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("weather?appid=cf87528eb883888befa9732b8f7d7312&units=metric")
    fun getWeather(@Query("lon") lon: Double, @Query("lat") lat: Double): Observable<Result>
}