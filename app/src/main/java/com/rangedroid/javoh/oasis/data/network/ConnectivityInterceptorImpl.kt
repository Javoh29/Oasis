package com.rangedroid.javoh.oasis.data.network

import com.rangedroid.javoh.oasis.data.provider.UnitProvider
import com.rangedroid.javoh.oasis.utils.NoConnectivityException
import okhttp3.Interceptor
import okhttp3.Response

@Suppress("DEPRECATION")
class ConnectivityInterceptorImpl(
    private val unitProvider: UnitProvider
) : ConnectivityInterceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        if (!unitProvider.isOnline())
            throw NoConnectivityException()
        return chain.proceed(chain.request())
    }
}