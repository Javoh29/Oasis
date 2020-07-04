package com.rangedroid.javoh.oasis.ui.fragments

import androidx.lifecycle.ViewModel
import com.rangedroid.javoh.oasis.data.provider.UnitProvider
import com.rangedroid.javoh.oasis.data.repository.OasisRepository

class ToursCategoryViewModel(
    private val oasisRepository: OasisRepository,
    unitProvider: UnitProvider
) : ViewModel() {

}