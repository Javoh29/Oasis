package com.rangedroid.javoh.oasis.ui.fragments

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.rangedroid.javoh.oasis.data.repository.OasisRepository

class ToursViewModelFactory(
    private val oasisRepository: OasisRepository
) : ViewModelProvider.NewInstanceFactory() {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return ToursViewModel(oasisRepository) as T
    }
}
