package com.rangedroid.javoh.oasis.ui.fragments

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

import com.rangedroid.javoh.oasis.R
import com.rangedroid.javoh.oasis.data.db.entity.firebase.ToursModel
import com.rangedroid.javoh.oasis.ui.adapters.ToursAdapter
import com.rangedroid.javoh.oasis.ui.base.ScopedFragment
import kotlinx.android.synthetic.main.tours_fragment.*
import kotlinx.coroutines.launch
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.closestKodein
import org.kodein.di.generic.instance

class ToursFragment : ScopedFragment(R.layout.tours_fragment), KodeinAware {

    override val kodein by closestKodein()
    private val viewModelFactory: ToursViewModelFactory by instance<ToursViewModelFactory>()
    private lateinit var viewModel: ToursViewModel

    private var recyclerView: RecyclerView? = null
    private var ds: String = ""

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclerView = view.findViewById(R.id.recycler_view_tours)
        recyclerView?.layoutManager = LinearLayoutManager(context)
        ds = arguments?.let {
            ToursFragmentArgs.fromBundle(it)
        }?.snapData!!
    }
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(ToursViewModel::class.java)
        loadData()
    }

    private fun loadData() = launch {
        val listModel: ArrayList<ToursModel> = ArrayList()
        if (viewModel.mUnitProvider.isLocale()) {
            viewModel.toursModelEn().value.await().observe(viewLifecycleOwner, Observer {
                if (it == null || it.isEmpty()) return@Observer
                it.forEach {list ->
                    if (ds == list.dataSnap)
                    listModel.addAll(list.tours)
                }
                bindUI(listModel)
            })
        }else {
            viewModel.toursModelRu().value.await().observe(viewLifecycleOwner, Observer {
                if (it == null || it.isEmpty()) return@Observer
                it.forEach {list ->
                    if (ds == list.dataSnap)
                    listModel.addAll(list.tours)
                }
                bindUI(listModel)
            })
        }
    }

    private fun bindUI(list: List<ToursModel>){
        recyclerView?.adapter = ToursAdapter(list)
        recyclerView?.visibility = View.VISIBLE
        spin_kit_tours.visibility = View.GONE
    }
}
