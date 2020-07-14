package com.rangedroid.javoh.oasis.data.provider

import com.rangedroid.javoh.oasis.utils.UnitPanorama
import com.rangedroid.javoh.oasis.utils.UnitTheme

interface UnitProvider {

    fun getUnitTheme(): UnitTheme

    fun setUnitTheme(theme: Boolean)

    fun getUnitPanorama(): UnitPanorama

    fun setUnitPanorama(isPanorama: Boolean)

    fun getUnitLoadFirebase(): String

    fun setUnitLoadFirebase(updateVer: String)

    fun initImageLoader()

    fun isOnline(): Boolean

    fun isLocale(): Boolean

    fun getLikes(name: String): Boolean

    fun setLikes(name: String, isLiked: Boolean)

    fun getLocation(): String

    fun setLocation(location: String)

    fun getIsLiked(ds: String): Boolean

    fun setCurrencyLoaded(isLoaded: Boolean)

    fun getCurrencyLoaded(): Boolean

    fun setWeather(isLoaded: Boolean)

    fun getWeather(): Boolean


}