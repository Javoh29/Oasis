package com.rangedroid.javoh.oasis.data.provider

import com.rangedroid.javoh.oasis.utils.UnitLoadFirebase
import com.rangedroid.javoh.oasis.utils.UnitPanorama
import com.rangedroid.javoh.oasis.utils.UnitTheme

interface UnitProvider {

    fun getUnitTheme(): UnitTheme

    fun setUnitTheme(theme: Boolean)

    fun getUnitPanorama(): UnitPanorama

    fun setUnitPanorama(isPanorama: Boolean)

    fun getUnitLoadFirebase(): UnitLoadFirebase

    fun setUnitLoadFirebase(isLoad: Boolean)

    fun initImageLoader()

    fun isOnline(): Boolean

    fun isLocale(): Boolean

    fun getLikes(name: String): Boolean

    fun setLikes(name: String, isLiked: Boolean)

    fun getLocation(): String

    fun setLocation(location: String)

}