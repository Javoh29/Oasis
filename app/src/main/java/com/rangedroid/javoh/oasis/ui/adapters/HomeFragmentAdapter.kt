@file:Suppress("DEPRECATION")

package com.rangedroid.javoh.oasis.ui.adapters

import android.annotation.SuppressLint
import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.widget.AppCompatImageView
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.*
import com.ramotion.foldingcell.FoldingCell
import com.rangedroid.javoh.oasis.R
import com.rangedroid.javoh.oasis.data.db.entity.current.CurrentWeatherModel
import com.rangedroid.javoh.oasis.data.db.unitlocalized.UnitSpecificCitiesInfoModel
import com.rangedroid.javoh.oasis.data.provider.UnitProvider
import com.rangedroid.javoh.oasis.ui.fragments.HomeFragmentDirections
import com.rangedroid.javoh.oasis.utils.MovingImageView
import com.rangedroid.javoh.oasis.utils.UnitPanorama
import com.rangedroid.javoh.oasis.utils.UnitTheme
import com.rangedroid.javoh.oasis.utils.UniversalImageLoader
import com.sackcentury.shinebuttonlib.ShineButton
import com.skydoves.elasticviews.ElasticButton
import java.lang.ref.WeakReference
import java.text.DecimalFormat
import kotlin.math.roundToInt

class HomeFragmentAdapter():
    RecyclerView.Adapter<HomeFragmentAdapter.CitiesViewHolder>() {

    private val cities = "cities"
    private val cityInfo = "city_info_en"
    private val cityLikes = "likes"
    private lateinit var listInfoModel: List<UnitSpecificCitiesInfoModel>
    private lateinit var listCurrentWeather: CurrentWeatherModel
    private val myRef: DatabaseReference = FirebaseDatabase.getInstance().reference
    private val df: DecimalFormat = DecimalFormat("#,###.##")
    private lateinit var activity: WeakReference<Activity>
    private lateinit var unitProvider: UnitProvider

    @SuppressLint("CommitPrefEdits")
    constructor(activity: Activity, listInfoModel: List<UnitSpecificCitiesInfoModel>, listCurrentWeather: CurrentWeatherModel, unitProvider: UnitProvider) : this() {
        this.activity = WeakReference(activity)
        this.listInfoModel = listInfoModel
        this.listCurrentWeather = listCurrentWeather
        this.unitProvider = unitProvider
    }

    class CitiesViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var fc: WeakReference<FoldingCell> = WeakReference(view.findViewById(R.id.folding_cell))
        var btnOpen: ElasticButton = view.findViewById(R.id.btn_open)
        var imageView: WeakReference<MovingImageView> = WeakReference(view.findViewById(R.id.img_view_home))
        var imageViewCell: AppCompatImageView = view.findViewById(R.id.img_view_home_cell)
        var imageViewWeatherFon: AppCompatImageView = view.findViewById(R.id.img_view_weather_cell)
        var imageWeatherIc: ImageView = view.findViewById(R.id.img_weather_ic)
        var likeBtn: ShineButton = view.findViewById(R.id.like_btn)
        var progressBar: ProgressBar = view.findViewById(R.id.progress_bar_cities)
        var tvCityName: TextView = view.findViewById(R.id.tv_city_name)
        var tvCityNameContent: TextView = view.findViewById(R.id.tv_city_name_content)
        var tvAreaQty: TextView = view.findViewById(R.id.tv_area_qty)
        var tvClimateQty: TextView = view.findViewById(R.id.tv_climate_qty)
        var tvHeightQty: TextView = view.findViewById(R.id.tv_hight_qty)
        var tvLikesQty: TextView = view.findViewById(R.id.tv_like_qty)
        var tvPopQty: TextView = view.findViewById(R.id.tv_pop_qty)
        var tvTelCodeQty: TextView = view.findViewById(R.id.tv_tel_code_qty)
        var tvMain: TextView = view.findViewById(R.id.tv_main)
        var tvTemp: TextView = view.findViewById(R.id.tv_temp)
        var tvHumidity: TextView = view.findViewById(R.id.tv_humidity)
        var tvWind: TextView = view.findViewById(R.id.tv_wind)

    }
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CitiesViewHolder {
        return CitiesViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.items_home_fragment, parent, false))
    }

    override fun getItemCount(): Int {
        return listInfoModel.size
    }

    @SuppressLint("SetTextI18n", "ResourceType")
    override fun onBindViewHolder(holder: CitiesViewHolder, position: Int) {
        val likes = IntArray(listInfoModel.size)
        likes[position] = listInfoModel[position].likes

        holder.likeBtn.isChecked = unitProvider.getLikes(listInfoModel[position].dataSnap)

        holder.imageView.get()?.isLoadOnCreate = unitProvider.getUnitPanorama() == UnitPanorama.ON

        if (unitProvider.getUnitTheme() == UnitTheme.DAY){
            UniversalImageLoader.setImage(listInfoModel[position].cityPhoto, holder.imageView.get()!!, holder.progressBar)
            UniversalImageLoader.setImage(listInfoModel[position].cityPhotoNight, holder.imageViewCell, holder.progressBar)
        }else{
            UniversalImageLoader.setImage(listInfoModel[position].cityPhotoParm, holder.imageView.get()!!, holder.progressBar)
            UniversalImageLoader.setImage(listInfoModel[position].cityPhotoParmNight, holder.imageViewCell, holder.progressBar)
        }
        holder.tvCityName.text = listInfoModel[position].name
        holder.tvCityNameContent.text = listInfoModel[position].name
        holder.tvAreaQty.text = listInfoModel[position].area
        holder.tvClimateQty.text = listInfoModel[position].climate
        holder.tvHeightQty.text = listInfoModel[position].height
        holder.tvLikesQty.text = df.format(likes[position])
        holder.tvPopQty.text = listInfoModel[position].pop
        holder.tvTelCodeQty.text = listInfoModel[position].tel_code
        holder.tvTemp.text = listCurrentWeather.climate[position].temp.toFloat().roundToInt().toString() + "˚"
        holder.tvHumidity.text = "${listCurrentWeather.climate[position].humidity} %"
        holder.tvWind.text = "${listCurrentWeather.wind[position].speed} m/s"

        if (unitProvider.getUnitTheme() == UnitTheme.DAY) {
            when (listCurrentWeather.weather[position].main) {
                "Thunderstorm" -> {
                    holder.imageViewWeatherFon.setImageResource(R.drawable.img_storm)
                    holder.imageWeatherIc.setImageResource(R.drawable.ic_storm)
                    holder.tvMain.text = activity.get()?.getString(R.string.text_thunderstorm)
                }
                "Drizzle" -> {
                    holder.imageViewWeatherFon.setImageResource(R.drawable.img_drizzle)
                    holder.imageWeatherIc.setImageResource(R.drawable.ic_drizzle)
                    holder.tvMain.text = activity.get()?.getString(R.string.text_drizzle)
                }
                "Rain" -> {
                    holder.imageViewWeatherFon.setImageResource(R.drawable.img_rain)
                    holder.imageWeatherIc.setImageResource(R.drawable.ic_rain)
                    holder.tvMain.text = activity.get()?.getString(R.string.text_rain)
                }
                "Clear" -> {
                    holder.imageViewWeatherFon.setImageResource(R.drawable.img_clear)
                    holder.imageWeatherIc.setImageResource(R.drawable.ic_sunny)
                    holder.tvMain.text = activity.get()?.getString(R.string.text_clear)
                }
                "Clouds" -> {
                    holder.imageViewWeatherFon.setImageResource(R.drawable.img_clouds)
                    holder.imageWeatherIc.setImageResource(R.drawable.ic_cloudy)
                    holder.tvMain.text = activity.get()?.getString(R.string.text_clouds)
                }
                "Snow" -> {
                    holder.imageViewWeatherFon.setImageResource(R.drawable.img_snow)
                    holder.imageWeatherIc.setImageResource(R.drawable.ic_snow)
                    holder.tvMain.text = activity.get()?.getString(R.string.text_snow)
                }
                "Mist" -> {
                    holder.imageViewWeatherFon.setImageResource(R.drawable.img_mist)
                    holder.imageWeatherIc.setImageResource(R.drawable.ic_mist)
                    holder.tvMain.text = activity.get()?.getString(R.string.text_mist)
                }
                "Smoke" -> {
                    holder.imageViewWeatherFon.setImageResource(R.drawable.img_mist)
                    holder.imageWeatherIc.setImageResource(R.drawable.ic_mist)
                    holder.tvMain.text = activity.get()?.getString(R.string.text_smoke)
                }
                "Dust" -> {
                    holder.imageViewWeatherFon.setImageResource(R.drawable.img_mist)
                    holder.imageWeatherIc.setImageResource(R.drawable.ic_mist)
                    holder.tvMain.text = activity.get()?.getString(R.string.text_dust)
                }
                "Fog" -> {
                    holder.imageViewWeatherFon.setImageResource(R.drawable.img_mist)
                    holder.imageWeatherIc.setImageResource(R.drawable.ic_mist)
                    holder.tvMain.text = activity.get()?.getString(R.string.text_fog)
                }
                "Sand" -> {
                    holder.imageViewWeatherFon.setImageResource(R.drawable.img_mist)
                    holder.imageWeatherIc.setImageResource(R.drawable.ic_mist)
                    holder.tvMain.text = activity.get()?.getString(R.string.text_sand)
                }
                "Ash" -> {
                    holder.imageViewWeatherFon.setImageResource(R.drawable.img_mist)
                    holder.imageWeatherIc.setImageResource(R.drawable.ic_mist)
                    holder.tvMain.text = activity.get()?.getString(R.string.text_ash)
                }
                "Squall" -> {
                    holder.imageViewWeatherFon.setImageResource(R.drawable.img_mist)
                    holder.imageWeatherIc.setImageResource(R.drawable.ic_mist)
                    holder.tvMain.text = activity.get()?.getString(R.string.text_squall)
                }
                "Tornado" -> {
                    holder.imageViewWeatherFon.setImageResource(R.drawable.img_mist)
                    holder.imageWeatherIc.setImageResource(R.drawable.ic_mist)
                    holder.tvMain.text = activity.get()?.getString(R.string.text_tornado)
                }
                else -> {
                    holder.imageViewWeatherFon.setImageResource(R.drawable.img_mist)
                    holder.imageWeatherIc.setImageResource(R.drawable.ic_mist)
                    holder.tvMain.text = activity.get()?.getString(R.string.text_haze)
                }

            }
        }else{
            when (listCurrentWeather.weather[position].main) {
                "Thunderstorm" -> {
                    holder.imageViewWeatherFon.setImageResource(R.drawable.img_storm)
                    holder.imageWeatherIc.setImageResource(R.drawable.ic_storm_night)
                    holder.tvMain.text = activity.get()?.getString(R.string.text_thunderstorm)
                }
                "Drizzle" -> {
                    holder.imageViewWeatherFon.setImageResource(R.drawable.img_drizzle_night)
                    holder.imageWeatherIc.setImageResource(R.drawable.ic_drizzle_night)
                    holder.tvMain.text = activity.get()?.getString(R.string.text_drizzle)
                }
                "Rain" -> {
                    holder.imageViewWeatherFon.setImageResource(R.drawable.img_rain_night)
                    holder.imageWeatherIc.setImageResource(R.drawable.ic_rain_night)
                    holder.tvMain.text = activity.get()?.getString(R.string.text_rain)
                }
                "Clear" -> {
                    holder.imageViewWeatherFon.setImageResource(R.drawable.img_clear_night)
                    holder.imageWeatherIc.setImageResource(R.drawable.ic_moon)
                    holder.tvMain.text = activity.get()?.getString(R.string.text_clear)
                }
                "Clouds" -> {
                    holder.imageViewWeatherFon.setImageResource(R.drawable.img_clouds_night)
                    holder.imageWeatherIc.setImageResource(R.drawable.ic_moon_cloud)
                    holder.tvMain.text = activity.get()?.getString(R.string.text_clouds)
                }
                "Snow" -> {
                    holder.imageViewWeatherFon.setImageResource(R.drawable.img_snow_night)
                    holder.imageWeatherIc.setImageResource(R.drawable.ic_snow_night)
                    holder.tvMain.text = activity.get()?.getString(R.string.text_snow)
                }
                "Mist" -> {
                    holder.imageViewWeatherFon.setImageResource(R.drawable.img_mist_night)
                    holder.imageWeatherIc.setImageResource(R.drawable.ic_mist_night)
                    holder.tvMain.text = activity.get()?.getString(R.string.text_mist)
                }
                "Smoke" -> {
                    holder.imageViewWeatherFon.setImageResource(R.drawable.img_mist_night)
                    holder.imageWeatherIc.setImageResource(R.drawable.ic_mist_night)
                    holder.tvMain.text = activity.get()?.getString(R.string.text_smoke)
                }
                "Dust" -> {
                    holder.imageViewWeatherFon.setImageResource(R.drawable.img_mist_night)
                    holder.imageWeatherIc.setImageResource(R.drawable.ic_mist_night)
                    holder.tvMain.text = activity.get()?.getString(R.string.text_dust)
                }
                "Fog" -> {
                    holder.imageViewWeatherFon.setImageResource(R.drawable.img_mist_night)
                    holder.imageWeatherIc.setImageResource(R.drawable.ic_mist_night)
                    holder.tvMain.text = activity.get()?.getString(R.string.text_fog)
                }
                "Sand" -> {
                    holder.imageViewWeatherFon.setImageResource(R.drawable.img_mist_night)
                    holder.imageWeatherIc.setImageResource(R.drawable.ic_mist_night)
                    holder.tvMain.text = activity.get()?.getString(R.string.text_sand)
                }
                "Ash" -> {
                    holder.imageViewWeatherFon.setImageResource(R.drawable.img_mist_night)
                    holder.imageWeatherIc.setImageResource(R.drawable.ic_mist_night)
                    holder.tvMain.text = activity.get()?.getString(R.string.text_ash)
                }
                "Squall" -> {
                    holder.imageViewWeatherFon.setImageResource(R.drawable.img_mist_night)
                    holder.imageWeatherIc.setImageResource(R.drawable.ic_mist_night)
                    holder.tvMain.text = activity.get()?.getString(R.string.text_squall)
                }
                "Tornado" -> {
                    holder.imageViewWeatherFon.setImageResource(R.drawable.img_mist_night)
                    holder.imageWeatherIc.setImageResource(R.drawable.ic_mist_night)
                    holder.tvMain.text = activity.get()?.getString(R.string.text_tornado)
                }
                else -> {
                    holder.imageViewWeatherFon.setImageResource(R.drawable.img_mist_night)
                    holder.imageWeatherIc.setImageResource(R.drawable.ic_mist_night)
                    holder.tvMain.text = activity.get()?.getString(R.string.text_haze)
                }

            }
        }


        holder.fc.get()?.setOnClickListener {
            holder.fc.get()?.toggle(false)
        }

        holder.likeBtn.setOnCheckStateChangeListener { _, checked ->
            myRef.addListenerForSingleValueEvent(object: ValueEventListener{
                override fun onCancelled(p0: DatabaseError) {
                }

                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    var i = 0
                    if (checked){
                        for (ds in dataSnapshot.child(cities).child(cityInfo).children){
                            if(i == position){
                                dataSnapshot.child(cities).child(cityInfo).child(ds.key.toString()).child(cityLikes).ref.setValue(likes[position] + 1)
                                holder.tvLikesQty.text = df.format(listInfoModel[position].likes + 1)
                                unitProvider.setLikes(ds.key!!, true)
                            }
                            i++
                        }
                    }else {
                        for (ds in dataSnapshot.child(cities).child(cityInfo).children){
                            if(i == position){
                                dataSnapshot.child(cities).child(cityInfo).child(ds.key.toString()).child(cityLikes).ref.setValue(likes[position] - 1)
                                listInfoModel[position].likes.and(listInfoModel[position].likes - 1)
                                holder.tvLikesQty.text = df.format(listInfoModel[position].likes)
                                unitProvider.setLikes(ds.key!!, false)
                            }
                            i++
                        }
                    }
                }

            })
        }

        holder.btnOpen.setOnClickListener {
            Navigation.findNavController(it).navigate(HomeFragmentDirections.actionHomeFragmentToInfoFragment(listInfoModel[position].dataSnap))
        }
    }

}