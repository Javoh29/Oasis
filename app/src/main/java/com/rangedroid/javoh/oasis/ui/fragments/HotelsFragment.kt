package com.rangedroid.javoh.oasis.ui.fragments

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.rangedroid.javoh.oasis.R
import com.rangedroid.javoh.oasis.ui.base.ScopedFragment

class HotelsFragment : ScopedFragment(R.layout.hotels_fragment) {

    private lateinit var viewModel: HotelsViewModel

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(HotelsViewModel::class.java)
    }

}
