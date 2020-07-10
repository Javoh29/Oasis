package com.rangedroid.javoh.oasis.ui.fragments

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.rangedroid.javoh.oasis.R
import com.rangedroid.javoh.oasis.data.provider.UnitProvider
import com.rangedroid.javoh.oasis.data.repository.OasisRepository
import com.rangedroid.javoh.oasis.utils.lazyDeferred

class CurrencyViewModel(
    private val oasisRepository: OasisRepository,
    unitProvider: UnitProvider
) : ViewModel() {

    fun model() = lazyDeferred {
        oasisRepository.getCurrency()
    }

    private val _listFlags = MutableLiveData<HashMap<String, Int>>().apply {
        val list: HashMap<String, Int> = HashMap()
        list["AED"] = R.drawable.falg_aed
        list["AFN"] = R.drawable.falg_afn
        list["AMD"] = R.drawable.falg_amd
        list["ARS"] = R.drawable.falg_ars
        list["AUD"] = R.drawable.falg_aud
        list["AZN"] = R.drawable.falg_azn
        list["BDT"] = R.drawable.falg_bdt
        list["BGN"] = R.drawable.falg_bgn
        list["BHD"] = R.drawable.falg_bhd
        list["BND"] = R.drawable.falg_bnd
        list["BRL"] = R.drawable.falg_brl
        list["BYN"] = R.drawable.falg_byn
        list["CAD"] = R.drawable.falg_cad
        list["CHF"] = R.drawable.falg_chf
        list["CNY"] = R.drawable.falg_cny
        list["CUP"] = R.drawable.falg_cup
        list["CZK"] = R.drawable.falg_czk
        list["DKK"] = R.drawable.falg_dkk
        list["DZD"] = R.drawable.falg_dzd
        list["EGP"] = R.drawable.falg_egp
        list["EUR"] = R.drawable.falg_eur
        list["GBP"] = R.drawable.falg_gbp
        list["GEL"] = R.drawable.falg_gel
        list["HUF"] = R.drawable.falg_huf
        list["IDR"] = R.drawable.falg_idr
        list["ILS"] = R.drawable.falg_ils
        list["INR"] = R.drawable.falg_inr
        list["IQD"] = R.drawable.falg_iqd
        list["IRR"] = R.drawable.falg_irr
        list["ISK"] = R.drawable.falg_isk
        list["JOD"] = R.drawable.falg_jod
        list["JPY"] = R.drawable.falg_jpy
        list["KGS"] = R.drawable.falg_kgs
        list["KHR"] = R.drawable.falg_khr
        list["KRW"] = R.drawable.falg_krw
        list["KWD"] = R.drawable.falg_kwd
        list["KZT"] = R.drawable.falg_kzt
        list["LAK"] = R.drawable.falg_lak
        list["LBP"] = R.drawable.falg_lbp
        list["LYD"] = R.drawable.falg_lyd
        list["MAD"] = R.drawable.falg_mad
        list["MDL"] = R.drawable.falg_mdl
        list["MMK"] = R.drawable.falg_mmk
        list["MNT"] = R.drawable.falg_mnt
        list["MXN"] = R.drawable.falg_mxn
        list["MYR"] = R.drawable.falg_myr
        list["NOK"] = R.drawable.falg_nok
        list["NZD"] = R.drawable.falg_nzd
        list["OMR"] = R.drawable.falg_omr
        list["PHP"] = R.drawable.falg_php
        list["PKR"] = R.drawable.falg_pkr
        list["PLN"] = R.drawable.falg_pln
        list["QAR"] = R.drawable.falg_qar
        list["RON"] = R.drawable.falg_ron
        list["RSD"] = R.drawable.falg_rsd
        list["RUB"] = R.drawable.falg_rub
        list["SAR"] = R.drawable.falg_sar
        list["SDG"] = R.drawable.falg_sdg
        list["SEK"] = R.drawable.falg_sek
        list["SGD"] = R.drawable.falg_sgd
        list["SYP"] = R.drawable.falg_syp
        list["THB"] = R.drawable.falg_thb
        list["TJS"] = R.drawable.falg_tjs
        list["TMT"] = R.drawable.falg_tmt
        list["TND"] = R.drawable.falg_tnd
        list["TRY"] = R.drawable.falg_try
        list["UAH"] = R.drawable.falg_uah
        list["USD"] = R.drawable.falg_usd
        list["UYU"] = R.drawable.falg_uyu
        list["VES"] = R.drawable.falg_ves
        list["VND"] = R.drawable.falg_vnd
        list["YER"] = R.drawable.falg_yer
        list["ZAR"] = R.drawable.falg_zar
        value = list
    }

    val listFlags: LiveData<HashMap<String, Int>> = _listFlags

    val mUnitProvider = unitProvider
}