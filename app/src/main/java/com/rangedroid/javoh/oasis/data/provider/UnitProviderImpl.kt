package com.rangedroid.javoh.oasis.data.provider

import android.annotation.SuppressLint
import android.content.Context
import android.net.ConnectivityManager
import android.os.Build
import androidx.annotation.RequiresApi
import com.nostra13.universalimageloader.core.ImageLoader
import com.rangedroid.javoh.oasis.utils.*
import java.io.IOException
import java.net.HttpURLConnection
import java.net.URL

const val UNIT_THEME = "UNIT_THEME"
const val UNIT_PANORAMA = "UNIT_PANORAMA"
const val UNIT_LOAD_FIREBASE = "UNIT_LOAD_FIREBASE"
const val UNIT_LOCATION = "UNIT_LOCATION"

@Suppress("DEPRECATION")
class UnitProviderImpl(context: Context) : PreferenceProvider(context), UnitProvider {

    private val mContext: Context = context
    private var actionHomeFun: ActionHomeFun? = null

    override fun getUnitTheme(): UnitTheme {
        return UnitTheme.valueOf(preferences.getString(UNIT_THEME, UnitTheme.DAY.name)!!)
    }

    @SuppressLint("CommitPrefEdits")
    override fun setUnitTheme(theme: Boolean) {
        if (theme){
            preferences.edit().putString(UNIT_THEME, UnitTheme.DAY.name).apply()
        }else{
            preferences.edit().putString(UNIT_THEME, UnitTheme.NIGHT.name).apply()
        }
    }

    override fun getUnitPanorama(): UnitPanorama {
        return UnitPanorama.valueOf(preferences.getString(UNIT_PANORAMA, UnitPanorama.ON.name)!!)
    }

    override fun setUnitPanorama(isPanorama: Boolean) {
        if (isPanorama){
            preferences.edit().putString(UNIT_PANORAMA, UnitPanorama.ON.name).apply()
        }else{
            preferences.edit().putString(UNIT_PANORAMA, UnitPanorama.OFF.name).apply()
        }
    }

    override fun getUnitLoadFirebase(): UnitLoadFirebase {
        return UnitLoadFirebase.valueOf(preferences.getString(UNIT_LOAD_FIREBASE, UnitLoadFirebase.NOT.name)!!)
    }

    override fun setUnitLoadFirebase(isLoad: Boolean) {
        if (isLoad){
            preferences.edit().putString(UNIT_LOAD_FIREBASE, UnitLoadFirebase.LOADED.name).apply()
        }else{
            preferences.edit().putString(UNIT_LOAD_FIREBASE, UnitLoadFirebase.NOT.name).apply()
        }
    }

    override fun initImageLoader(){
        val universalImageLoader = UniversalImageLoader(mContext)
        ImageLoader.getInstance().init(universalImageLoader.getConfig())
    }

    override fun isOnline(): Boolean {
        val connectivityManager = mContext.getSystemService(Context.CONNECTIVITY_SERVICE)
                as ConnectivityManager
        val networkInfo = connectivityManager.activeNetworkInfo
        return if (networkInfo != null && networkInfo.isConnected) {
            try {
                URL("https://www.google.com/").openConnection() as HttpURLConnection
                true
            } catch (e: IOException) {
                false
            }
        }else false
    }

    @RequiresApi(Build.VERSION_CODES.N)
    override fun isLocale(): Boolean {
        return !(mContext.resources.configuration.locales.toString() == "[ru_RU]" || mContext.resources.configuration.locales.toString() == "ru_RU")
    }

    override fun getLikes(name: String): Boolean {
        return preferences.getBoolean(name, false)
    }

    override fun setLikes(name: String, isLiked: Boolean) {
        preferences.edit().putBoolean(name, isLiked).apply()
    }

    override fun getLocation(): String {
        return preferences.getString(UNIT_LOCATION, "null").toString()
    }

    override fun setLocation(location: String) {
        preferences.edit().putString(UNIT_LOCATION, location).apply()
    }
}