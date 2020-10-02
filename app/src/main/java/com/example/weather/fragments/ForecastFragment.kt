package com.example.weather.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.navArgs
import com.example.weather.R
import com.example.weather.viewmodel.ForecastViewModel
import kotlinx.android.synthetic.main.fragment_forecast.*
import kotlinx.android.synthetic.main.fragment_forecast.view.*

class ForecastFragment : Fragment() {
    private val mWeatherViewModel: ForecastViewModel by viewModels()

    private val args by navArgs<ForecastFragmentArgs>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_forecast, container, false)

        mWeatherViewModel.refresh(args.city)

        view.swipeRefreshLayout.setOnRefreshListener {
            mWeatherViewModel.refresh(args.city)
        }

        observeViewModel()

        return view
    }

    private fun observeViewModel() {
        mWeatherViewModel.forecast.observe(viewLifecycleOwner, Observer { weather ->
            weather?.let {
                weatherDataView.visibility = View.VISIBLE

                txtCity.text = it.observationData?.get(0)?.city.toString()
                txtDescription.text = it.observationData?.get(0)?.weather?.description.toString()
                txtTemp.text = it.observationData?.get(0)?.temperature.toString().plus(" \u2103")
                txtHumidity.text = it.observationData?.get(0)?.humidity.toString().plus("%")
                txtUv.text = it.observationData?.get(0)?.uv.toString()
                txtWind.text = it.observationData?.get(0)?.windSpeed.toString().plus(" km")
                txtPressure.text = it.observationData?.get(0)?.pressure.toString().plus(" m.Bar")
            }
        })

        mWeatherViewModel.forecastLoadError.observe(viewLifecycleOwner, Observer { isError ->
            isError?.let {
                errorView.visibility = if (it) View.VISIBLE else View.GONE
            }
        })

        mWeatherViewModel.loading.observe(viewLifecycleOwner, Observer { isLoading ->
            isLoading?.let {
                swipeRefreshLayout.isRefreshing = it

                if (it) {
                    errorView.visibility = View.GONE
                    weatherDataView.visibility = View.GONE
                }
            }
        })
    }
}