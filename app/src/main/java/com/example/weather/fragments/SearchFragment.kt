package com.example.weather.fragments

import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.weather.R
import kotlinx.android.synthetic.main.fragment_search.*
import kotlinx.android.synthetic.main.fragment_search.view.*

class SearchFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_search, container, false)

        view.btnSearch.setOnClickListener {
            handleSearchCity()
        }

        return view
    }

    private fun handleSearchCity() {
        val city = edtCity.text.toString()

        if (TextUtils.isEmpty(city)) {
            Toast.makeText(
                requireContext(),
                getString(R.string.city_empty_error),
                Toast.LENGTH_SHORT
            ).show()
        } else {
            val action = SearchFragmentDirections.actionSearchFragmentToWeatherFragment(city)
            findNavController().navigate(action)
        }
    }
}
