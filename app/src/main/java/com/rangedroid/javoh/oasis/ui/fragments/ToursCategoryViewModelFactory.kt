package com.rangedroid.javoh.oasis.ui.fragments

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.rangedroid.javoh.oasis.data.provider.UnitProvider
import com.rangedroid.javoh.oasis.data.repository.OasisRepository

class ToursCategoryViewModelFactory(
    private val oasisRepository: OasisRepository,
    private val unitProvider: UnitProvider
) : ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return ToursCategoryViewModel(oasisRepository, unitProvider) as T
    }
}