package com.rangedroid.javoh.oasis.ui.fragments

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.rangedroid.javoh.oasis.R
import com.rangedroid.javoh.oasis.data.models.ForTouristsModel
import com.rangedroid.javoh.oasis.ui.activities.HomeActivity
import com.rangedroid.javoh.oasis.ui.adapters.ForAdapter

class ForTouristsFragment : Fragment(R.layout.for_tourists_fragment) {

    private lateinit var viewModel: ForTouristsViewModel
    private lateinit var recyclerView: RecyclerView

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recyclerView = view.findViewById(R.id.recycler_for)
        recyclerView.layoutManager = LinearLayoutManager(context)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(ForTouristsViewModel::class.java)
        bindUI(viewModel.listForModel(requireContext()))
    }

    private fun bindUI(list: List<ForTouristsModel>){
        recyclerView.adapter = ForAdapter(listForModel = list,language = (activity as HomeActivity).unitProvider.isLocale())
    }

}