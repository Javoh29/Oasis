package com.rangedroid.javoh.oasis.ui.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.rangedroid.javoh.oasis.R
import com.rangedroid.javoh.oasis.ui.activities.HomeActivity
import com.rangedroid.javoh.oasis.ui.adapters.PhbookListAdapter

class PhrasebookListFragment(): Fragment(R.layout.phrasebook_list_fragment) {

    private var recyclerView: RecyclerView? = null
    private lateinit var viewModel: PhrasebookViewModel
    private var pos: Int = 0

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclerView = view.findViewById(R.id.recycler_phbook)
        recyclerView?.layoutManager = LinearLayoutManager(context)
        pos = arguments?.let {
            PhrasebookListFragmentArgs.fromBundle(it)
        }?.index!!
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(PhrasebookViewModel::class.java)
        recyclerView?.adapter = PhbookListAdapter(listPhbookModel = viewModel.listModel(requireContext()), pos = pos, language = (activity as HomeActivity).unitProvider.isLocale())
    }

}