package com.example.i_weather.ui.home

import androidx.lifecycle.MutableLiveData
import com.example.i_weather.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor() : BaseViewModel() {
    val lon = MutableLiveData<String>()
    val lat = MutableLiveData<String>()
}