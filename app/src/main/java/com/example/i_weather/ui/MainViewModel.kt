package com.example.i_weather.ui

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.i_weather.base.BaseViewModel
import com.example.i_weather.data.model.Coord
import com.example.i_weather.data.model.Result
import com.example.i_weather.data.respository.ApiRepository
import com.example.i_weather.util.NetworkHelper
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.observers.DisposableObserver
import javax.inject.Inject
import kotlin.math.log

@HiltViewModel
class MainViewModel @Inject constructor() : BaseViewModel() {
    @Inject
    lateinit var networkHelper: NetworkHelper

    @Inject
    lateinit var apiRepository: ApiRepository

    val result = MutableLiveData<Result>()
    val currentCoord = MutableLiveData<Coord>()

    fun getWeather(lon: Double, lat: Double, success: () -> Unit, fail: (message: String) -> Unit) {
        if (networkHelper.isNetworkConnected()) {
            addCompositeDisposable(
                apiRepository.getWeather(lon, lat)
                    .subscribeWith(object : DisposableObserver<Result>() {
                        override fun onNext(value: Result?) {
                            success()
                            result.value = value!!
                            Log.d("getWeather", "onNext: $value")
                        }

                        override fun onError(e: Throwable?) {
                            e?.message?.let { fail(it) }
                            e?.printStackTrace()
                        }

                        override fun onComplete() {
                        }
                    })
            )
        } else fail("Check your network connection")
    }
}