package com.rangedroid.javoh.oasis.ui.fragments

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.EditText
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.github.ybq.android.spinkit.SpinKitView
import com.rangedroid.javoh.oasis.R
import com.rangedroid.javoh.oasis.data.db.entity.firebase.*
import com.rangedroid.javoh.oasis.ui.activities.HomeActivity
import com.rangedroid.javoh.oasis.ui.adapters.*
import com.rangedroid.javoh.oasis.ui.base.ScopedFragment
import kotlinx.android.synthetic.main.single_fragment.*
import kotlinx.coroutines.launch
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.closestKodein
import org.kodein.di.generic.instance

class SingleFragment : ScopedFragment(R.layout.single_fragment), KodeinAware {

    override val kodein by closestKodein()
    private val viewModelFactory: SingleViewModelFactory by instance<SingleViewModelFactory>()
    private lateinit var viewModel: SingleViewModel

    private var recyclerView: RecyclerView? = null
    private var spinKit: SpinKitView? = null
    private var editSearch: EditText? = null
    private var label: String = ""

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        label = (activity as HomeActivity).navController.currentDestination?.label.toString()
        recyclerView = view.findViewById(R.id.single_recycler_view)
        spinKit = view.findViewById(R.id.single_spin_kit)
        editSearch = view.findViewById(R.id.single_edit_search)
        recyclerView?.layoutManager = LinearLayoutManager(context)

        editSearch?.addTextChangedListener(object: TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                when(label){
                    "Sights" -> {
                        (recyclerView?.adapter as SightsAdapter).searchText(editSearch?.text.toString())
                    }
                    "Hotels" -> {
                        (recyclerView?.adapter as HotelsAdapter).searchText(editSearch?.text.toString())
                    }
                    "Restaurants" -> {
                        (recyclerView?.adapter as RestaurantsAdapter).searchText(editSearch?.text.toString())
                    }
                    "Museums" -> {
                        (recyclerView?.adapter as MuseumsAdapter).searchText(editSearch?.text.toString())
                    }
                    "Markets" -> {
                        (recyclerView?.adapter as MarketsAdapter).searchText(editSearch?.text.toString())
                    }
                    "Banks" -> {
                        (recyclerView?.adapter as BanksAdapter).searchText(editSearch?.text.toString())
                    }
                }
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }

        })
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(SingleViewModel::class.java)
        loadData()
    }

    private fun loadData() = launch {
        viewModel.citiesMoreInfo().value.await().observe(viewLifecycleOwner, Observer {
            if (it == null || it.isEmpty()) return@Observer
            bindUI(it)
        })
    }

    private fun bindUI(citiesMoreInfo: List<CitiesMoreInfo>){
        val lat: Double = viewModel.mUnitProvider.getLocation().split("/")[0].toDouble()
        val lon: Double = viewModel.mUnitProvider.getLocation().split("/")[1].toDouble()
        val listIsLiked: ArrayList<Boolean> = ArrayList()
        when(label){
            "Sights" -> {
                val list: ArrayList<SightsModel> = ArrayList()
                citiesMoreInfo.forEach {
                    it.sights.forEach { model ->
                        list.add(model)
                        listIsLiked.add(viewModel.mUnitProvider.getIsLiked(model.dataSnap))
                    }
                }
                val adapter = SightsAdapter(
                    listSightModel = list,
                    listLikesBool = listIsLiked,
                    lat = lat,
                    lon = lon,
                    isLocal = viewModel.mUnitProvider.isLocale()
                )
                recyclerView?.adapter = adapter
            }
            "Hotels" -> {
                val list: ArrayList<HotelsModel> = ArrayList()
                citiesMoreInfo.forEach {
                    it.hotels.forEach { model ->
                        list.add(model)
                        listIsLiked.add(viewModel.mUnitProvider.getIsLiked(model.dataSnap))
                    }
                }
                val adapter = HotelsAdapter(
                    listHotelModel = list,
                    listLikesBool = listIsLiked,
                    lat = lat,
                    lon = lon
                )
                recyclerView?.adapter = adapter
            }
            "Restaurants" -> {
                val list: ArrayList<RestaurantsModel> = ArrayList()
                citiesMoreInfo.forEach {
                    it.restaurants.forEach { model ->
                        list.add(model)
                        listIsLiked.add(viewModel.mUnitProvider.getIsLiked(model.dataSnap))
                    }
                }
                val adapter = RestaurantsAdapter(
                    listRestModel = list,
                    listLikesBool = listIsLiked,
                    lat = lat,
                    lon = lon
                )
                recyclerView?.adapter = adapter
            }
            "Museums" -> {
                val list: ArrayList<MuseumsModel> = ArrayList()
                citiesMoreInfo.forEach {
                    it.museums.forEach { model ->
                        list.add(model)
                        listIsLiked.add(viewModel.mUnitProvider.getIsLiked(model.dataSnap))
                    }
                }
                val adapter = MuseumsAdapter(
                    listMuseumsModel = list,
                    listLikesBool = listIsLiked,
                    lat = lat,
                    lon = lon,
                    isLocal = viewModel.mUnitProvider.isLocale()
                )
                recyclerView?.adapter = adapter
            }
            "Markets" -> {
                val list: ArrayList<MarketsModel> = ArrayList()
                citiesMoreInfo.forEach {
                    it.markets.forEach { model ->
                        list.add(model)
                        listIsLiked.add(viewModel.mUnitProvider.getIsLiked(model.dataSnap))
                    }
                }
                val adapter = MarketsAdapter(
                    listMarketsModel = list,
                    listLikesBool = listIsLiked,
                    lat = lat,
                    lon = lon,
                    isLocal = viewModel.mUnitProvider.isLocale()
                )
                recyclerView?.adapter = adapter
            }
            "Banks" -> {
                val list: ArrayList<BanksModel> = ArrayList()
                citiesMoreInfo.forEach {
                    it.banks.forEach { model ->
                        list.add(model)
                        listIsLiked.add(viewModel.mUnitProvider.getIsLiked(model.dataSnap))
                    }
                }
                val adapter = BanksAdapter(
                    listBanksModel = list,
                    listLikesBool = listIsLiked,
                    lat = lat,
                    lon = lon
                )
                recyclerView?.adapter = adapter
            }
        }

        single_frame.visibility = View.VISIBLE
        spinKit?.visibility = View.GONE
    }
}