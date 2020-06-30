package com.rangedroid.javoh.oasis

import android.app.Application
import android.content.Context
import com.google.android.gms.location.LocationServices
import com.rangedroid.javoh.oasis.data.db.OasisDatabase
import com.rangedroid.javoh.oasis.data.db.WeatherDatabase
import com.rangedroid.javoh.oasis.data.network.*
import com.rangedroid.javoh.oasis.data.provider.UnitProvider
import com.rangedroid.javoh.oasis.data.provider.UnitProviderImpl
import com.rangedroid.javoh.oasis.data.repository.OasisRepository
import com.rangedroid.javoh.oasis.data.repository.OasisRepositoryImpl
import com.rangedroid.javoh.oasis.ui.fragments.HomeViewModelFactory
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.androidXModule
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.provider
import org.kodein.di.generic.singleton

class OasisApplication: Application(), KodeinAware {

    companion object {
        var isStartActivity: Boolean = true
    }

    override val kodein: Kodein
        get() = Kodein.lazy {
            import(androidXModule(this@OasisApplication))

            bind() from singleton { WeatherDatabase(instance()) }
            bind() from singleton { OasisDatabase(instance()) }
            bind() from singleton { instance<WeatherDatabase>().currentWeatherDao() }
            bind() from singleton { instance<OasisDatabase>().firebaseDao() }
            bind<UnitProvider>() with singleton { UnitProviderImpl(instance()) }
            bind<ConnectivityInterceptor>() with singleton { ConnectivityInterceptorImpl(instance()) }
            bind() from singleton { WeatherApiService(instance()) }
            bind() from provider { LocationServices.getFusedLocationProviderClient(instance<Context>()) }
            bind<WeatherNetworkDataSource>() with singleton { WeatherNetworkDataSourceImpl(instance()) }
            bind<OasisRepository>() with singleton { OasisRepositoryImpl(instance(), instance(), instance(), instance()) }
            bind() from provider { HomeViewModelFactory(instance(), instance()) }
        }
}