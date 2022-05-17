package com.example.i_weather.ui.home

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.viewModels
import com.example.i_weather.BR
import com.example.i_weather.R
import com.example.i_weather.base.BaseFragment
import com.example.i_weather.base.BaseFragmentWithoutViewModel
import com.example.i_weather.data.model.Coord
import com.example.i_weather.databinding.FragmentHomeBinding
import dagger.hilt.android.AndroidEntryPoint
import java.util.*
import java.util.concurrent.TimeUnit

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding, HomeViewModel>() {
    companion object {
        fun newInstance(): HomeFragment {
            val args = Bundle()
            val fragment = HomeFragment()
            fragment.arguments = args
            return fragment
        }
    }

    override val bindingVariable: Int
        get() = BR.viewModel

    override val layoutId: Int
        get() = R.layout.fragment_home

    override val viewModel: HomeViewModel by viewModels()

    private var lon: Double = -500.0
    private var lat: Double = -500.0

    override fun initView() {
        val cHaNoi = Coord(105.804817, 21.028511)
        val cHCM = Coord(106.660172, 10.762622)
        val cHue = Coord(107.590866, 16.463713)
        val cHaiPhong = Coord(106.688087, 20.844912)
        activityViewModel.currentCoord.value = cHaNoi
        getWeather(activityViewModel.currentCoord.value!!)
        viewBinding.layoutRefresh.setOnRefreshListener {
            getWeather(activityViewModel.currentCoord.value!!)
        }
        with(viewBinding) {
            btnHaNoi.setOnClickListener { activityViewModel.currentCoord.value = cHaNoi }
            btnHCM.setOnClickListener { activityViewModel.currentCoord.value = cHCM }
            btnHue.setOnClickListener { activityViewModel.currentCoord.value = cHue }
            btnHaiPhong.setOnClickListener { activityViewModel.currentCoord.value = cHaiPhong }
            btnSubmit.setOnClickListener { activityViewModel.currentCoord.value = Coord(lon, lat) }
        }
    }

    private fun getWeather(coord: Coord) {
        viewBinding.loadingView.visibility = View.VISIBLE
        setButtonState(false)
        activityViewModel.getWeather(coord.lon!!, coord.lat!!, {
            viewBinding.loadingView.visibility = View.GONE
            viewBinding.layoutRefresh.isRefreshing = false
            setButtonState(true)
        }, {
            viewBinding.loadingView.visibility = View.GONE
            Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
            setButtonState(true)
        })
    }

    private fun setButtonState(enabled: Boolean) {
        with(viewBinding) {
            btnHaNoi.isEnabled = enabled
            btnHaiPhong.isEnabled = enabled
            btnHCM.isEnabled = enabled
            btnHue.isEnabled = enabled
        }
    }

    @SuppressLint("SetTextI18n")
    override fun setupObserver() {
        activityViewModel.result.observe(viewLifecycleOwner) {
            with(viewBinding) {
                tvLocation.text = it.name
                tvCoordinate.text = "${it.coord!!.lon} | ${it.coord!!.lat}"
                tvWeather.text = "${it.weather[0].main} (${it.weather[0].description})"
                tvTemperature.text = "${it.main!!.temp!!} °C"
                tvWind.text = "${it.wind!!.speed} km/h"
            }
        }
        activityViewModel.currentCoord.observe(viewLifecycleOwner) {
            getWeather(activityViewModel.currentCoord.value!!)
        }
        viewModel.lon.observe(viewLifecycleOwner) {
            lon = (-500).toDouble()
            if (it.isNotBlank()) lon = it.toDouble()
            if (lon >= -180 && lon <= 180) {
                viewBinding.btnSubmit.isEnabled =
                    !viewModel.lat.value.isNullOrBlank() && viewModel.lat.value!!.toDouble() >= -90 && viewModel.lat.value!!.toDouble() <= 90
            } else viewBinding.btnSubmit.isEnabled = false
        }
        viewModel.lat.observe(viewLifecycleOwner) {
            lat = (-500).toDouble()
            if (it.isNotBlank()) lat = it.toDouble()
            if (lat >= -90 && lat <= 90) {
                viewBinding.btnSubmit.isEnabled =
                    !viewModel.lon.value.isNullOrBlank() && viewModel.lon.value!!.toDouble() >= -180 && viewModel.lon.value!!.toDouble() <= 180
            } else viewBinding.btnSubmit.isEnabled = false
        }
    }
}