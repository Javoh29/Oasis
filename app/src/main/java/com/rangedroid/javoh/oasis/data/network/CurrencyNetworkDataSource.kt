package com.rangedroid.javoh.oasis.data.network

import androidx.lifecycle.LiveData
import com.rangedroid.javoh.oasis.data.network.response.CurrencyResponse

interface CurrencyNetworkDataSource {
    val downloadedCurrency: LiveData<CurrencyResponse>

    suspend fun fetchCurrency()
}