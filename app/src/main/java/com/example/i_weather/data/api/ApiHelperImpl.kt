package com.example.i_weather.data.api

import com.example.i_weather.data.model.Result
import io.reactivex.Observable
import javax.inject.Inject

class ApiHelperImpl @Inject constructor(private val apiService: ApiService) : ApiHelper {
    override fun getWeather(lon: Double, lat: Double): Observable<Result> {
        return apiService.getWeather(lon, lat)
    }
}