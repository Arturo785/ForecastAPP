package com.example.forecastapp.weather.current

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.forecastapp.R
import com.example.forecastapp.ui.base.ScopedFragment
import kotlinx.android.synthetic.main.current_weather_fragment.*
import kotlinx.coroutines.*
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.closestKodein
import org.kodein.di.generic.instance


class CurrentWeatherFragment : ScopedFragment(), KodeinAware {

    override val kodein by closestKodein()

        //like that because of the dependency injection
    private val viewModelFactory : CurrentWeatherViewModelFactory by instance()


    private lateinit var viewModelWeather: CurrentWeatherViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.current_weather_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        // second parameter is the factory
        viewModelWeather = ViewModelProviders.of(this, viewModelFactory)
            .get(CurrentWeatherViewModel::class.java)

        bindUI()
    }

    private fun bindUI() = launch{
        val currentWeather = viewModelWeather.weather.await()
        currentWeather.observe(this@CurrentWeatherFragment, Observer {
            if(it == null) return@Observer

            textViewCurrentWeather.text = it.toString()
        })
    }

    private fun bindUI2(){
        CoroutineScope(Dispatchers.Main).launch {
            val test = async {
                viewModelWeather.weather
            }.await()

            test.await().observe(this@CurrentWeatherFragment, Observer {
                if(it == null) return@Observer

                textViewCurrentWeather.text = it.toString()
            })

        }
    }
        // bindUI 2 and 3 do the same but with different methods
    private fun bindUI3(){
        CoroutineScope(Dispatchers.Main).launch {

            val test = withContext(Dispatchers.Main) {
                viewModelWeather.weather
            }

            test.await().observe(this@CurrentWeatherFragment, Observer {
                if(it == null) return@Observer

                textViewCurrentWeather.text = it.toString()
            })

        }
    }

}
