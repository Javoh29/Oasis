package com.rangedroid.javoh.oasis.ui.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.rangedroid.javoh.oasis.R
import com.rangedroid.javoh.oasis.data.db.entity.future.WeatherList
import kotlin.math.roundToInt

class WeatherAdapter (private var listWeatherModel: List<WeatherList>): RecyclerView.Adapter<WeatherAdapter.WeatherViewHolder>() {

    class WeatherViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var tvHour: TextView = view.findViewById(R.id.tv_list_hour)
        var tvMain: TextView = view.findViewById(R.id.tv_list_main)
        var tvTemp: TextView = view.findViewById(R.id.tv_list_temp)
        var imgMain: ImageView = view.findViewById(R.id.img_smal_icons)
        var mView = view
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WeatherViewHolder {
        return WeatherViewHolder(
                LayoutInflater.from(parent.context).inflate(
                R.layout.item_weather_hours_container,
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return listWeatherModel.size
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: WeatherViewHolder, position: Int) {
        holder.tvHour.text = listWeatherModel[position].dtTxt.substring(11, 16)
        holder.tvTemp.text = "${listWeatherModel[position].main.temp.roundToInt()}˚"

        when(listWeatherModel[position].weather[0].main){
            "Thunderstorm" -> {
                holder.imgMain.setImageResource(R.drawable.ic_saml_storm)
                holder.tvMain.text = holder.mView.context.getString(R.string.text_thunderstorm)
            }
            "Drizzle" -> {
                holder.imgMain.setImageResource(R.drawable.ic_smal_drizzle)
                holder.tvMain.text = holder.mView.context.getString(R.string.text_drizzle)
            }
            "Rain" -> {
                holder.imgMain.setImageResource(R.drawable.ic_smal_rain)
                holder.tvMain.text = holder.mView.context.getString(R.string.text_rain)
            }
            "Clear" -> {
                holder.imgMain.setImageResource(R.drawable.ic_smal_sunny)
                holder.tvMain.text = holder.mView.context.getString(R.string.text_clear)
            }
            "Clouds" -> {
                holder.imgMain.setImageResource(R.drawable.ic_smal_cloudy)
                holder.tvMain.text = holder.mView.context.getString(R.string.text_clouds)
            }
            "Snow" -> {
                holder.imgMain.setImageResource(R.drawable.ic_smal_snow)
                holder.tvMain.text = holder.mView.context.getString(R.string.text_snow)
            }
            "Mist" -> {
                holder.imgMain.setImageResource(R.drawable.ic_smal_mist)
                holder.tvMain.text = holder.mView.context.getString(R.string.text_mist)
            }
            "Smoke" -> {
                holder.imgMain.setImageResource(R.drawable.ic_smal_mist)
                holder.tvMain.text = holder.mView.context.getString(R.string.text_smoke)
            }
            "Dust" -> {
                holder.imgMain.setImageResource(R.drawable.ic_smal_mist)
                holder.tvMain.text = holder.mView.context.getString(R.string.text_dust)
            }
            "Fog" -> {
                holder.imgMain.setImageResource(R.drawable.ic_smal_mist)
                holder.tvMain.text = holder.mView.context.getString(R.string.text_fog)
            }
            "Sand" -> {
                holder.imgMain.setImageResource(R.drawable.ic_smal_mist)
                holder.tvMain.text = holder.mView.context.getString(R.string.text_sand)
            }
            "Ash" -> {
                holder.imgMain.setImageResource(R.drawable.ic_smal_mist)
                holder.tvMain.text = holder.mView.context.getString(R.string.text_ash)
            }
            "Squall" -> {
                holder.imgMain.setImageResource(R.drawable.ic_smal_mist)
                holder.tvMain.text = holder.mView.context.getString(R.string.text_squall)
            }
            "Tornado" -> {
                holder.imgMain.setImageResource(R.drawable.ic_smal_mist)
                holder.tvMain.text = holder.mView.context.getString(R.string.text_tornado)
            }
            else -> {
                holder.imgMain.setImageResource(R.drawable.ic_smal_mist)
                holder.tvMain.text = holder.mView.context.getString(R.string.text_haze)
            }
        }
    }
}