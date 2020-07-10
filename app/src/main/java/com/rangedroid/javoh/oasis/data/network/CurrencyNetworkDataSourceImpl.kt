package com.rangedroid.javoh.oasis.data.network

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.rangedroid.javoh.oasis.data.network.response.CurrencyResponse
import com.rangedroid.javoh.oasis.utils.NoConnectivityException
import java.net.UnknownHostException

class CurrencyNetworkDataSourceImpl(
    private val currencyApiService: CurrencyApiService
) : CurrencyNetworkDataSource {

    private var _downloadedCurrency = MutableLiveData<CurrencyResponse>()
    override val downloadedCurrency: LiveData<CurrencyResponse>
        get() = _downloadedCurrency

    override suspend fun fetchCurrency() {
        try {
            val fetchedCurrency = currencyApiService.getCurrencyAsync().await()
            _downloadedCurrency.postValue(fetchedCurrency)
        }catch (e: NoConnectivityException) {
            Log.e("Connectivity", "No internet connection.", e)
        }catch (e: UnknownHostException){
            Log.e("BAG", "unknown exception. ", e)
        }
    }
}