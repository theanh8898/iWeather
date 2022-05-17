package com.example.i_weather.data.respository

import com.example.i_weather.data.api.ApiHelper
import com.example.i_weather.data.model.Result
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class ApiRepository @Inject constructor(private val apiHelper: ApiHelper) {
    fun getWeather(lon: Double, lat: Double): Observable<Result> =
        apiHelper.getWeather(lon, lat).subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
}