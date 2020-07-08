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

    private val _listFlags = MutableLiveData<List<Int>>().apply {
        val list: ArrayList<Int> = ArrayList()
        list.add(R.drawable.falg_aed)
        list.add(R.drawable.falg_afn)
        list.add(R.drawable.falg_amd)
        list.add(R.drawable.falg_ars)
        list.add(R.drawable.falg_aud)
        list.add(R.drawable.falg_azn)
        list.add(R.drawable.falg_bdt)
        list.add(R.drawable.falg_bgn)
        list.add(R.drawable.falg_bhd)
        list.add(R.drawable.falg_bnd)
        list.add(R.drawable.falg_brl)
        list.add(R.drawable.falg_byn)
        list.add(R.drawable.falg_cad)
        list.add(R.drawable.falg_chf)
        list.add(R.drawable.falg_cny)
        list.add(R.drawable.falg_cup)
        list.add(R.drawable.falg_czk)
        list.add(R.drawable.falg_dkk)
        list.add(R.drawable.falg_dzd)
        list.add(R.drawable.falg_egp)
        list.add(R.drawable.falg_eur)
        list.add(R.drawable.falg_gbp)
        list.add(R.drawable.falg_gel)
        list.add(R.drawable.falg_huf)
        list.add(R.drawable.falg_idr)
        list.add(R.drawable.falg_ils)
        list.add(R.drawable.falg_inr)
        list.add(R.drawable.falg_iqd)
        list.add(R.drawable.falg_irr)
        list.add(R.drawable.falg_isk)
        list.add(R.drawable.falg_jod)
        list.add(R.drawable.falg_jpy)
        list.add(R.drawable.falg_kgs)
        list.add(R.drawable.falg_khr)
        list.add(R.drawable.falg_krw)
        list.add(R.drawable.falg_kwd)
        list.add(R.drawable.falg_kzt)
        list.add(R.drawable.falg_lak)
        list.add(R.drawable.falg_lbp)
        list.add(R.drawable.falg_lyd)
        list.add(R.drawable.falg_mad)
        list.add(R.drawable.falg_mdl)
        list.add(R.drawable.falg_mmk)
        list.add(R.drawable.falg_mnt)
        list.add(R.drawable.falg_mxn)
        list.add(R.drawable.falg_myr)
        list.add(R.drawable.falg_nok)
        list.add(R.drawable.falg_nzd)
        list.add(R.drawable.falg_omr)
        list.add(R.drawable.falg_php)
        list.add(R.drawable.falg_pkr)
        list.add(R.drawable.falg_pln)
        list.add(R.drawable.falg_qar)
        list.add(R.drawable.falg_ron)
        list.add(R.drawable.falg_rsd)
        list.add(R.drawable.falg_rub)
        list.add(R.drawable.falg_sar)
        list.add(R.drawable.falg_sdg)
        list.add(R.drawable.falg_sek)
        list.add(R.drawable.falg_sgd)
        list.add(R.drawable.falg_syp)
        list.add(R.drawable.falg_thb)
        list.add(R.drawable.falg_tjs)
        list.add(R.drawable.falg_tmt)
        list.add(R.drawable.falg_tnd)
        list.add(R.drawable.falg_try)
        list.add(R.drawable.falg_uah)
        list.add(R.drawable.falg_usd)
        list.add(R.drawable.falg_uyu)
        list.add(R.drawable.falg_ves)
        list.add(R.drawable.falg_vnd)
        list.add(R.drawable.falg_yer)
        list.add(R.drawable.falg_zar)
        value = list
    }

    val listFlags: LiveData<List<Int>> = _listFlags

    val mUnitProvider = unitProvider
}