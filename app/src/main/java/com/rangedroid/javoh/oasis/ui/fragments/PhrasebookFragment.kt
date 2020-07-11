package com.rangedroid.javoh.oasis.ui.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.rangedroid.javoh.oasis.R
import com.rangedroid.javoh.oasis.ui.activities.HomeActivity
import com.rangedroid.javoh.oasis.ui.adapters.PhbookAdapter

@Suppress("DEPRECATION")
class PhrasebookFragment(): Fragment(R.layout.phrasebook_fragment) {

    private var recyclerView: RecyclerView? = null
    private lateinit var viewModel: PhrasebookViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclerView = view.findViewById(R.id.recycler_phbook)
        recyclerView?.layoutManager = LinearLayoutManager(context)

    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(PhrasebookViewModel::class.java)
        recyclerView?.adapter = PhbookAdapter(listPhbookModel = viewModel.listModel(requireContext()), language = (activity as HomeActivity).unitProvider.isLocale())
    }
}