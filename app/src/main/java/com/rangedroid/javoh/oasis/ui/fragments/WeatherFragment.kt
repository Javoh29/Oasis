@file:Suppress("DEPRECATION")

package com.rangedroid.javoh.oasis.ui.fragments

import android.annotation.SuppressLint
import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.github.ybq.android.spinkit.SpinKitView
import com.rangedroid.javoh.oasis.R
import com.rangedroid.javoh.oasis.data.network.response.FutureWeatherResponse
import com.rangedroid.javoh.oasis.ui.adapters.WeatherAdapter
import com.rangedroid.javoh.oasis.ui.base.ScopedFragment
import com.rangedroid.javoh.oasis.utils.UnitTheme
import kotlinx.android.synthetic.main.weather_fragment.*
import kotlinx.coroutines.launch
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.closestKodein
import org.kodein.di.generic.instance
import kotlin.math.roundToInt

class WeatherFragment : ScopedFragment(R.layout.weather_fragment), KodeinAware {

    override val kodein by closestKodein()
    private val viewModelFactory: WeatherViewModelFactory by instance<WeatherViewModelFactory>()
    private lateinit var viewModel: WeatherViewModel

    private lateinit var imgWeatherFon: ImageView
    private lateinit var imgWeatherMain: ImageView
    private lateinit var tvPlaceText: TextView
    private lateinit var tvTemp: TextView
    private lateinit var tvMain: TextView
    private lateinit var tvFeels: TextView
    private lateinit var tvHumidity: TextView
    private lateinit var tvWind: TextView
    private lateinit var tvPressure: TextView
    private lateinit var recyclerView: RecyclerView
    private lateinit var spinKit: SpinKitView

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        imgWeatherFon = view.findViewById(R.id.img_fon_weather)
        imgWeatherMain = view.findViewById(R.id.ic_weather_main_nav)
        tvPlaceText = view.findViewById(R.id.tv_place_name)
        tvTemp = view.findViewById(R.id.tv_temp_nav)
        tvMain= view.findViewById(R.id.tv_main_nav)
        tvFeels = view.findViewById(R.id.tv_feels_nav)
        tvHumidity = view.findViewById(R.id.tv_humidity_nav)
        tvWind = view.findViewById(R.id.tv_wind_nav)
        tvPressure = view.findViewById(R.id.tv_pressure_nav)
        recyclerView = view.findViewById(R.id.recycler_weather)
        spinKit = view.findViewById(R.id.spin_kit_weather)

        recyclerView.layoutManager = LinearLayoutManager(context)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(WeatherViewModel::class.java)
        loadData()
    }

    private fun loadData() = launch {
        val lat: String = viewModel.mUnitProvider.getLocation().split("/")[0]
        val lon: String = viewModel.mUnitProvider.getLocation().split("/")[1]
        viewModel.model(lat, lon).value.await().downloadedFutureWeather.observeForever {
            if (it == null) return@observeForever
            bindUI(it)
        }
    }

    @SuppressLint("SetTextI18n")
    private fun bindUI(model: FutureWeatherResponse){
        tvTemp.text = "${model.list[0].main.temp.roundToInt()}˚"
        tvFeels.text = "${getString(R.string.text_feels)} ${model.list[0].main.feelsLike.roundToInt()}˚"
        tvPressure.text = "${getString(R.string.text_pressure)} " + model.list[0].main.pressure
        tvHumidity.text = "${getString(R.string.text_humidity)} " + model.list[0].main.humidity
        tvWind.text = "${getString(R.string.text_wind)} " + model.list[0].wind.speed + "m/s"
        tvPlaceText.text = model.city.name

        if (viewModel.mUnitProvider.getUnitTheme() == UnitTheme.DAY){
            when (model.list[0].weather[0].main) {
                "Thunderstorm" -> {
                    imgWeatherFon.setImageResource(R.drawable.img_storm_nav)
                    imgWeatherMain.setImageResource(R.drawable.ic_storm)
                    tvMain.text = getString(R.string.text_thunderstorm)
                }
                "Drizzle" -> {
                    imgWeatherFon.setImageResource(R.drawable.img_drizzle_nav)
                    imgWeatherMain.setImageResource(R.drawable.ic_drizzle)
                    tvMain.text = getString(R.string.text_drizzle)
                }
                "Rain" -> {
                    imgWeatherFon.setImageResource(R.drawable.img_rain_nav)
                    imgWeatherMain.setImageResource(R.drawable.ic_rain)
                    tvMain.text = getString(R.string.text_rain)
                }
                "Clear" -> {
                    imgWeatherFon.setImageResource(R.drawable.img_clear_nav)
                    imgWeatherMain.setImageResource(R.drawable.ic_sunny)
                    tvMain.text = getString(R.string.text_clear)
                }
                "Clouds" -> {
                    imgWeatherFon.setImageResource(R.drawable.img_clouds_nav)
                    imgWeatherMain.setImageResource(R.drawable.ic_cloudy)
                    tvMain.text = getString(R.string.text_clouds)
                }
                "Snow" -> {
                    imgWeatherFon.setImageResource(R.drawable.img_snow_nav)
                    imgWeatherMain.setImageResource(R.drawable.ic_snow)
                    tvMain.text = getString(R.string.text_snow)
                }
                "Mist" -> {
                    imgWeatherFon.setImageResource(R.drawable.img_mist_nav)
                    imgWeatherMain.setImageResource(R.drawable.ic_mist)
                    tvMain.text = getString(R.string.text_mist)
                }
                "Smoke" -> {
                    imgWeatherFon.setImageResource(R.drawable.img_mist_nav)
                    imgWeatherMain.setImageResource(R.drawable.ic_mist)
                    tvMain.text = getString(R.string.text_smoke)
                }
                "Dust" -> {
                    imgWeatherFon.setImageResource(R.drawable.img_mist_nav)
                    imgWeatherMain.setImageResource(R.drawable.ic_mist)
                    tvMain.text = getString(R.string.text_dust)
                }
                "Fog" -> {
                    imgWeatherFon.setImageResource(R.drawable.img_mist_nav)
                    imgWeatherMain.setImageResource(R.drawable.ic_mist)
                    tvMain.text = getString(R.string.text_fog)
                }
                "Sand" -> {
                    imgWeatherFon.setImageResource(R.drawable.img_mist_nav)
                    imgWeatherMain.setImageResource(R.drawable.ic_mist)
                    tvMain.text = getString(R.string.text_sand)
                }
                "Ash" -> {
                    imgWeatherFon.setImageResource(R.drawable.img_mist_nav)
                    imgWeatherMain.setImageResource(R.drawable.ic_mist)
                    tvMain.text = getString(R.string.text_ash)
                }
                "Squall" -> {
                    imgWeatherFon.setImageResource(R.drawable.img_mist_nav)
                    imgWeatherMain.setImageResource(R.drawable.ic_mist)
                    tvMain.text = getString(R.string.text_squall)
                }
                "Tornado" -> {
                    imgWeatherFon.setImageResource(R.drawable.img_mist_nav)
                    imgWeatherMain.setImageResource(R.drawable.ic_mist)
                    tvMain.text = getString(R.string.text_tornado)
                }
                else -> {
                    imgWeatherFon.setImageResource(R.drawable.img_mist_nav)
                    imgWeatherMain.setImageResource(R.drawable.ic_mist)
                    tvMain.text = getString(R.string.text_haze)
                }
            }
        }else{
            when (model.list[0].weather[0].main) {
                "Thunderstorm" -> {
                    imgWeatherFon.setImageResource(R.drawable.img_storm_nav)
                    imgWeatherMain.setImageResource(R.drawable.ic_storm_night)
                    tvMain.text = getString(R.string.text_thunderstorm)
                }
                "Drizzle" -> {
                    imgWeatherFon.setImageResource(R.drawable.img_drizzle_nav_night)
                    imgWeatherMain.setImageResource(R.drawable.ic_drizzle_night)
                    tvMain.text = getString(R.string.text_drizzle)
                }
                "Rain" -> {
                    imgWeatherFon.setImageResource(R.drawable.img_rain_nav_night)
                    imgWeatherMain.setImageResource(R.drawable.ic_rain_night)
                    tvMain.text = getString(R.string.text_rain)
                }
                "Clear" -> {
                    imgWeatherFon.setImageResource(R.drawable.img_clear_nav_night)
                    imgWeatherMain.setImageResource(R.drawable.ic_moon)
                    tvMain.text = getString(R.string.text_clear)
                }
                "Clouds" -> {
                    imgWeatherFon.setImageResource(R.drawable.img_clouds_nav_night)
                    imgWeatherMain.setImageResource(R.drawable.ic_moon_cloud)
                    tvMain.text = getString(R.string.text_clouds)
                }
                "Snow" -> {
                    imgWeatherFon.setImageResource(R.drawable.img_snow_nav_night)
                    imgWeatherMain.setImageResource(R.drawable.ic_snow_night)
                    tvMain.text = getString(R.string.text_snow)
                }
                "Mist" -> {
                    imgWeatherFon.setImageResource(R.drawable.img_mist_nav_night)
                    imgWeatherMain.setImageResource(R.drawable.ic_mist_night)
                    tvMain.text = getString(R.string.text_mist)
                }
                "Smoke" -> {
                    imgWeatherFon.setImageResource(R.drawable.img_mist_nav_night)
                    imgWeatherMain.setImageResource(R.drawable.ic_mist_night)
                    tvMain.text = getString(R.string.text_smoke)
                }
                "Dust" -> {
                    imgWeatherFon.setImageResource(R.drawable.img_mist_nav_night)
                    imgWeatherMain.setImageResource(R.drawable.ic_mist_night)
                    tvMain.text = getString(R.string.text_dust)
                }
                "Fog" -> {
                    imgWeatherFon.setImageResource(R.drawable.img_mist_nav_night)
                    imgWeatherMain.setImageResource(R.drawable.ic_mist_night)
                    tvMain.text = getString(R.string.text_fog)
                }
                "Sand" -> {
                    imgWeatherFon.setImageResource(R.drawable.img_mist_nav_night)
                    imgWeatherMain.setImageResource(R.drawable.ic_mist_night)
                    tvMain.text = getString(R.string.text_sand)
                }
                "Ash" -> {
                    imgWeatherFon.setImageResource(R.drawable.img_mist_nav_night)
                    imgWeatherMain.setImageResource(R.drawable.ic_mist_night)
                    tvMain.text = getString(R.string.text_ash)
                }
                "Squall" -> {
                    imgWeatherFon.setImageResource(R.drawable.img_mist_nav_night)
                    imgWeatherMain.setImageResource(R.drawable.ic_mist_night)
                    tvMain.text = getString(R.string.text_squall)
                }
                "Tornado" -> {
                    imgWeatherFon.setImageResource(R.drawable.img_mist_nav_night)
                    imgWeatherMain.setImageResource(R.drawable.ic_mist_night)
                    tvMain.text = getString(R.string.text_tornado)
                }
                else -> {
                    imgWeatherFon.setImageResource(R.drawable.img_mist_nav_night)
                    imgWeatherMain.setImageResource(R.drawable.ic_mist_night)
                    tvMain.text = getString(R.string.text_haze)
                }
            }
        }
        recyclerView.adapter = WeatherAdapter(model.list)
        frame_weather.visibility = View.VISIBLE
        spinKit.visibility = View.GONE
    }
}