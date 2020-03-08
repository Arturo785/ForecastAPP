package com.example.forecastapp.weather.current

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.forecastapp.R
import com.example.forecastapp.internal.glide.GlideApp
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
        val weatherLocation = viewModelWeather.weatherLocation.await()

        weatherLocation.observe(this@CurrentWeatherFragment, Observer { location ->
            if(location == null) return@Observer

            updateLocation(location.name)
        })


        currentWeather.observe(this@CurrentWeatherFragment, Observer {
            if(it == null) return@Observer

            group_loading.visibility = View.GONE
            updateDateToToday()
            updateTemperaure(it.temperature,it.feelslike)
            updateCondition(it.weatherDescriptions[0])
            updatePrecipitation(it.precip)
            updateWind(it.windDir,it.windSpeed)
            updateVisibility(it.visibility)

            //This simple with the addition of appGlideModule in Internal package
            GlideApp.with(this@CurrentWeatherFragment)
                .load(it.weatherIcons[0])
                .into(imageView_condition_icon)


        })
    }

    private fun bindUI2(){
        CoroutineScope(Dispatchers.Main).launch {
            val test = async {
                viewModelWeather.weather
            }.await()

            test.await().observe(this@CurrentWeatherFragment, Observer {
                if(it == null) return@Observer

                //textViewCurrentWeather.text = it.toString()
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

                //textViewCurrentWeather.text = it.toString()
            })

        }
    }

    private fun chooseLocalizedUnitAbbreviaton(metric : String, imperial : String) : String{
        return if(viewModelWeather.isMetric) metric else imperial
    }

    private fun updateLocation(location : String){
        (activity as? AppCompatActivity)?.supportActionBar?.title = location
    }

    private fun updateDateToToday(){
        (activity as? AppCompatActivity)?.supportActionBar?.subtitle = "Today"
    }

    private fun updateTemperaure(temperature : Int, feelsLike: Int){
        val unit = chooseLocalizedUnitAbbreviaton("ºC" , "ºF")
        textView_temperature.text = "$temperature$unit"
        textView_feels_like_temperature.text = "Feels like $feelsLike$unit"
    }

    private fun updateCondition(condition : String){
        textView_condition.text = condition
    }

    private fun updatePrecipitation(precipitationVolume: Double){
        val unit = chooseLocalizedUnitAbbreviaton("mm" , "in")
        textView_precipitation.text = "Precipitation $precipitationVolume $unit"

    }

    private fun updateWind(windDirection: String, windSpeed: Int) {
        val unitAbbreviation = chooseLocalizedUnitAbbreviaton("kph", "mph")
        textView_wind.text = "Wind: $windDirection, $windSpeed $unitAbbreviation"
    }

    private fun updateVisibility(visibilityDistance: Int) {
        val unitAbbreviation = chooseLocalizedUnitAbbreviaton("km", "mi.")
        textView_visibility.text = "Visibility: $visibilityDistance $unitAbbreviation"
    }

}
