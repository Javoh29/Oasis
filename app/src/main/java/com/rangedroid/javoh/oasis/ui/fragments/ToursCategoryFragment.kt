package com.rangedroid.javoh.oasis.ui.fragments

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.rangedroid.javoh.oasis.R

class ToursCategoryFragment : Fragment() {

    private lateinit var viewModel: ToursCategoryViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.tours_category_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(ToursCategoryViewModel::class.java)
        // TODO: Use the ViewModel
    }

}