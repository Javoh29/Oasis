package com.rangedroid.javoh.oasis.ui.fragments

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.rangedroid.javoh.oasis.R
import com.rangedroid.javoh.oasis.data.db.unitlocalized.UnitSpecificTours
import com.rangedroid.javoh.oasis.ui.adapters.ToursCategoryAdapter
import com.rangedroid.javoh.oasis.ui.base.ScopedFragment
import kotlinx.android.synthetic.main.tours_category_fragment.*
import kotlinx.coroutines.launch
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.closestKodein
import org.kodein.di.generic.instance

class ToursCategoryFragment : ScopedFragment(R.layout.tours_category_fragment), KodeinAware {

    override val kodein by closestKodein()
    private val viewModelFactory: ToursCategoryViewModelFactory by instance<ToursCategoryViewModelFactory>()
    private lateinit var viewModel: ToursCategoryViewModel

    private var recyclerView: RecyclerView? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recyclerView = view.findViewById(R.id.recycler_tours)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(ToursCategoryViewModel::class.java)
        loadData()
    }

    private fun loadData() = launch {
        val listModel: ArrayList<UnitSpecificTours> = ArrayList()
        if (viewModel.mUnitProvider.isLocale()) {
            viewModel.toursModelEn().value.await().observe(viewLifecycleOwner, Observer {
                if (it == null || it.isEmpty()) return@Observer
                Log.d("BAG", it.size.toString())
                it.forEach {list ->
                    listModel.add(
                        UnitSpecificTours(
                            title = list.title,
                            photo = list.photo,
                            dataSnap = list.dataSnap,
                            tours = list.tours
                        )
                    )
                }
                bindUI(listModel)
            })
        }else{
            viewModel.toursModelRu().value.await().observe(viewLifecycleOwner, Observer {
                if (it == null || it.isEmpty()) return@Observer
                it.forEach {list ->
                    listModel.add(
                        UnitSpecificTours(
                            title = list.title,
                            photo = list.photo,
                            dataSnap = list.dataSnap,
                            tours = list.tours
                        )
                    )
                }
                bindUI(listModel)
            })
        }
    }

    private fun bindUI(list: List<UnitSpecificTours>){
        recyclerView?.layoutManager = LinearLayoutManager(context)
        recyclerView?.adapter = ToursCategoryAdapter(list)
        recyclerView?.visibility = View.VISIBLE
        spin_kit_tours.visibility = View.GONE
    }

}