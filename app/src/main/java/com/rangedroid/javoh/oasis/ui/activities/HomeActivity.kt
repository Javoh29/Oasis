package com.rangedroid.javoh.oasis.ui.activities

import android.Manifest
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.View
import android.view.WindowManager
import android.view.animation.AnimationUtils
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.view.GravityCompat
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.fragment.FragmentNavigator
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupWithNavController
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationResult
import com.rangedroid.javoh.oasis.OasisApplication.Companion.isStartActivity
import com.rangedroid.javoh.oasis.R
import com.rangedroid.javoh.oasis.data.provider.UnitProvider
import com.rangedroid.javoh.oasis.ui.adapters.HomeFragmentAdapter
import com.rangedroid.javoh.oasis.utils.ActionHomeFun
import com.rangedroid.javoh.oasis.utils.LifecycleBoundLocationManager
import com.rangedroid.javoh.oasis.utils.UnitPanorama
import com.rangedroid.javoh.oasis.utils.UnitTheme
import kotlinx.android.synthetic.main.activity_home.*
import kotlinx.android.synthetic.main.error_connect.*
import kotlinx.android.synthetic.main.snipped_app_bar_home.*
import kotlinx.android.synthetic.main.splash_screen.*
import org.kodein.di.generic.instance
import org.kodein.di.KodeinAware
import org.kodein.di.android.kodein

class HomeActivity: AppCompatActivity(), KodeinAware {

    override val kodein by kodein()
    val unitProvider: UnitProvider by instance<UnitProvider>()
    private val fusedLocationProviderClient: FusedLocationProviderClient by instance<FusedLocationProviderClient>()
    private val isTheme: Boolean
        get() =  unitProvider.getUnitTheme() == UnitTheme.DAY
    lateinit var navController: NavController
    private val locationCallback = object : LocationCallback() {
        override fun onLocationResult(location: LocationResult?) {
            super.onLocationResult(location)
            unitProvider.setLocation("${location!!.lastLocation.latitude}/${location.lastLocation.longitude}")
        }
    }
    private var actionHomeFun: ActionHomeFun? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (isTheme){
            setTheme(R.style.ThemeDay)
        }else{
            setTheme(R.style.ThemeNight)
        }
        setContentView(R.layout.activity_home)

        navController = Navigation.findNavController(this, R.id.nav_host_fragment)
        navigation_menu.setupWithNavController(navController)

        if (isStartActivity){
            startAnimation()
        }else{
            anim_container.visibility = View.GONE
        }

        onActionBarListener()
        unitProvider.initImageLoader()
        requestLocationPermission()
        bindLocationManager()
    }

    private fun onActionBarListener(){
        item_menu.setOnClickListener {
            if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
                drawer_layout.closeDrawer(GravityCompat.START)
            }else{
                drawer_layout.openDrawer(GravityCompat.START)
            }
        }

        item_mood.setOnClickListener {
            unitProvider.setUnitTheme(!isTheme)
            this.recreate()
        }

        if (unitProvider.getUnitPanorama() == UnitPanorama.ON){
            if (isTheme) {
                item_panorama.setImageResource(R.drawable.ic_panorama_img_on)
            }else {
                item_panorama.setImageResource(R.drawable.ic_panorama_img_on_night)
            }
        }else {
            if (isTheme) {
                item_panorama.setImageResource(R.drawable.ic_panorama_img_off)
            }else {
                item_panorama.setImageResource(R.drawable.ic_panorama_img_off_night)
            }
        }

        item_panorama.setOnClickListener {
            if (unitProvider.getUnitPanorama() == UnitPanorama.ON){
                if (isTheme){
                    item_panorama.setImageResource(R.drawable.ic_panorama_img_off)
                }else{
                    item_panorama.setImageResource(R.drawable.ic_panorama_img_off_night)
                }
                unitProvider.setUnitPanorama(false)
            }else{
                if (isTheme){
                    item_panorama.setImageResource(R.drawable.ic_panorama_img_on)
                }else{
                    item_panorama.setImageResource(R.drawable.ic_panorama_img_on_night)
                }
                unitProvider.setUnitPanorama(true)
            }

            actionHomeFun?.restartAdapterHome()
        }

        btn_try_age.setOnClickListener {
            if (unitProvider.isOnline()){
                Log.e("BAG", "connect")
                line_no_connection.visibility = View.GONE
                progress_bar_connect.visibility = View.VISIBLE
                Handler().postDelayed({
                    kotlin.run {
                        if (isTheme){
                            setStatusBarColor(R.color.colorPrimaryDark)
                        }else {
                            setStatusBarColor(R.color.colorPrimaryNight)
                        }
                        frame_connect.visibility = View.GONE
                        line_no_connection.visibility = View.VISIBLE
                        progress_bar_connect.visibility = View.GONE
                        isStartActivity = true
                        actionHomeFun?.loadBaseHome()
                    }
                }, 3000)
            }else{
                Log.e("BAG", "not connect")
                line_no_connection.visibility = View.GONE
                progress_bar_connect.visibility = View.VISIBLE
                Handler().postDelayed({
                    kotlin.run {
                        frame_connect.visibility = View.VISIBLE
                        line_no_connection.visibility = View.VISIBLE
                        progress_bar_connect.visibility = View.GONE
                    }
                }, 3000)
            }
        }
    }

    private fun startAnimation(){
        setStatusBarColor(R.color.colorInitial)
        img_camel?.visibility = View.VISIBLE
        img_camel.startAnimation(AnimationUtils.loadAnimation(this,R.anim.fromtop))
        img_tower_one?.visibility = View.VISIBLE
        img_tower_one.startAnimation(AnimationUtils.loadAnimation(this,R.anim.frombottom))
        img_tower_two?.visibility = View.VISIBLE
        img_tower_two.startAnimation(AnimationUtils.loadAnimation(this,R.anim.frombottom_two))
        Handler().postDelayed({
            kotlin.run {
                img_squeak_one?.visibility = View.VISIBLE
                img_squeak_two?.visibility = View.VISIBLE
                img_squeak_one.startAnimation(AnimationUtils.loadAnimation(this,R.anim.from_right))
                img_squeak_two.startAnimation(AnimationUtils.loadAnimation(this,R.anim.from_right_two))
            }
        }, 800)

        Handler().postDelayed({
            kotlin.run {
                img_caravan?.visibility = View.VISIBLE
                tv_init_oasis?.visibility = View.VISIBLE
                img_caravan.startAnimation(AnimationUtils.loadAnimation(this,R.anim.from_right))
                tv_init_oasis.startAnimation(AnimationUtils.loadAnimation(this,R.anim.from_right_two))
            }
        }, 1200)

        Handler().postDelayed({
            kotlin.run {
                img_sun?.visibility = View.VISIBLE
                img_sun.startAnimation(AnimationUtils.loadAnimation(this,R.anim.frombottom_sun))
            }
        }, 1400)

        Handler().postDelayed({
            kotlin.run {
                anim_container?.visibility = View.GONE
                if (isTheme) {
                    setStatusBarColor(R.color.colorPrimaryDark)
                }else{
                    setStatusBarColor(R.color.colorPrimaryNight)
                }
                isStartActivity = false
            }
        }, 4000)
    }

    private fun bindLocationManager() {
        LifecycleBoundLocationManager(
            this,
            fusedLocationProviderClient, locationCallback
        )
    }

    private fun requestLocationPermission() {
        ActivityCompat.requestPermissions(
            this,
            arrayOf(Manifest.permission.ACCESS_COARSE_LOCATION),
            1
        )
    }

    override fun onSupportNavigateUp(): Boolean {
        return NavigationUI.navigateUp(navController, drawer_layout)
    }

    private fun setStatusBarColor(color: Int){
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window.statusBarColor = ContextCompat.getColor(this, color)
    }

    fun updateAdapter(action: ActionHomeFun){
        actionHomeFun = action
    }

}