package com.rangedroid.javoh.oasis.ui.fragments

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.rangedroid.javoh.oasis.R
import com.rangedroid.javoh.oasis.ui.base.ScopedFragment

class SightsFragment : ScopedFragment(R.layout.sights_fragment) {

    private lateinit var viewModel: SightsViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(SightsViewModel::class.java)
    }

}
