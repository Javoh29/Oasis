package com.rangedroid.javoh.oasis.ui.fragments

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.github.ybq.android.spinkit.SpinKitView
import com.rangedroid.javoh.oasis.R
import com.rangedroid.javoh.oasis.data.db.entity.firebase.CitiesMoreInfo
import com.rangedroid.javoh.oasis.ui.adapters.*
import com.rangedroid.javoh.oasis.ui.base.ScopedFragment
import kotlinx.coroutines.launch
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.closestKodein
import org.kodein.di.generic.instance

class OtherFragment : ScopedFragment(R.layout.other_fragment), KodeinAware {

    override val kodein by closestKodein()
    private val viewModelFactory: OtherViewModelFactory by instance<OtherViewModelFactory>()
    private lateinit var viewModel: OtherViewModel

    private var recyclerView: RecyclerView? = null
    private var spinKit: SpinKitView? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclerView = view.findViewById(R.id.other_recycler_view)
        spinKit = view.findViewById(R.id.other_spin_kit)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(OtherViewModel::class.java)

        loadData(
            dataSnap = arguments?.let {
                OtherFragmentArgs.fromBundle(it)
            }!!.snapData,
            index = arguments?.let {
                OtherFragmentArgs.fromBundle(it)
            }!!.index
        )
    }

    private fun loadData(dataSnap: String, index: Int) = launch {
        val citiesMoreInfo = viewModel.citiesMoreInfo().value.await()

        citiesMoreInfo.observe(viewLifecycleOwner, Observer {
            if (it == null || it.isEmpty()) return@Observer
            it.forEach {list ->
                if (list.dataSnap == dataSnap) bindUI(index, list)
            }
        })
    }

    private fun bindUI(index: Int, citiesMoreInfo: CitiesMoreInfo){
        val lat: Double = viewModel.mUnitProvider.getLocation().split("/")[0].toDouble()
        val lon: Double = viewModel.mUnitProvider.getLocation().split("/")[1].toDouble()
        val listIsLiked: ArrayList<Boolean> = ArrayList()
        recyclerView?.layoutManager = LinearLayoutManager(context)
        when(index){
            0 -> {
                citiesMoreInfo.sights.forEach {
                    listIsLiked.add(viewModel.mUnitProvider.getIsLiked(it.dataSnap))
                }
                val adapter = SightsAdapter(
                    listSightModel = citiesMoreInfo.sights,
                    listLikesBool = listIsLiked, lat = lat, lon = lon,
                    isLocal = viewModel.mUnitProvider.isLocale()
                )
                recyclerView?.adapter = adapter
            }
            1 -> {
                citiesMoreInfo.hotels.forEach {
                    listIsLiked.add(viewModel.mUnitProvider.getIsLiked(it.dataSnap))
                }
                val adapter = HotelsAdapter(
                    listHotelModel = citiesMoreInfo.hotels,
                    listLikesBool = listIsLiked, lat = lat, lon = lon
                )
                recyclerView?.adapter = adapter
            }
            2 -> {
                citiesMoreInfo.restaurants.forEach {
                    listIsLiked.add(viewModel.mUnitProvider.getIsLiked(it.dataSnap))
                }
                val adapter = RestaurantsAdapter(
                    listRestModel = citiesMoreInfo.restaurants,
                    listLikesBool = listIsLiked, lat = lat, lon = lon
                )
                recyclerView?.adapter = adapter
            }
            3 -> {
                citiesMoreInfo.museums.forEach {
                    listIsLiked.add(viewModel.mUnitProvider.getIsLiked(it.dataSnap))
                }
                val adapter = MuseumsAdapter(
                    listMuseumsModel = citiesMoreInfo.museums,
                    listLikesBool = listIsLiked, lat = lat, lon = lon,
                    isLocal = viewModel.mUnitProvider.isLocale()
                )
                recyclerView?.adapter = adapter
            }
            4 -> {
                citiesMoreInfo.markets.forEach {
                    listIsLiked.add(viewModel.mUnitProvider.getIsLiked(it.dataSnap))
                }
                val adapter = MarketsAdapter(
                    listMarketsModel = citiesMoreInfo.markets,
                    listLikesBool = listIsLiked, lat = lat, lon = lon,
                    isLocal = viewModel.mUnitProvider.isLocale()
                )
                recyclerView?.adapter = adapter
            }
            5 -> {
                citiesMoreInfo.banks.forEach {
                    listIsLiked.add(viewModel.mUnitProvider.getIsLiked(it.dataSnap))
                }
                val adapter = BanksAdapter(
                    listBanksModel = citiesMoreInfo.banks,
                    listLikesBool = listIsLiked, lat = lat, lon = lon
                )
                recyclerView?.adapter = adapter
            }
        }
        spinKit?.visibility = View.GONE
        recyclerView?.visibility = View.VISIBLE
    }

}