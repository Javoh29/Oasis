package com.rangedroid.javoh.oasis.ui.fragments

import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.View
import android.view.WindowManager
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.github.ybq.android.spinkit.SpinKitView
import com.rangedroid.javoh.oasis.OasisApplication
import com.rangedroid.javoh.oasis.R
import com.rangedroid.javoh.oasis.data.db.entity.current.CurrentWeatherModel
import com.rangedroid.javoh.oasis.ui.activities.HomeActivity
import com.rangedroid.javoh.oasis.ui.adapters.HomeFragmentAdapter
import com.rangedroid.javoh.oasis.ui.base.ScopedFragment
import com.rangedroid.javoh.oasis.utils.ActionHomeFun
import com.rangedroid.javoh.oasis.utils.UnitTheme
import com.scwang.smartrefresh.header.PhoenixHeader
import com.scwang.smartrefresh.header.TaurusHeader
import com.scwang.smartrefresh.layout.SmartRefreshLayout
import com.scwang.smartrefresh.layout.api.RefreshLayout
import kotlinx.android.synthetic.main.error_connect.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.closestKodein
import org.kodein.di.generic.instance

@Suppress("DEPRECATION")
class HomeFragment : ScopedFragment(R.layout.home_fragment), KodeinAware, ActionHomeFun {

    override val kodein by closestKodein()
    private val viewModelFactory: HomeViewModelFactory by instance<HomeViewModelFactory>()

    private lateinit var viewModel: HomeViewModel
    private var pullToRefresh: RefreshLayout? = null
    private var recyclerView: RecyclerView? = null
    private var spinKit: SpinKitView? = null
    private var homeAdapter: HomeFragmentAdapter? = null
    private val weatherModel = CurrentWeatherModel()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(HomeViewModel::class.java)

        pullToRefresh = view.findViewById<SmartRefreshLayout>(R.id.pull_to_refresh)
        recyclerView = view.findViewById(R.id.list_view_home)
        spinKit = view.findViewById(R.id.spin_kit_home)
        if (viewModel.mUnitProvider.getUnitTheme() == UnitTheme.DAY)
            pullToRefresh?.setRefreshHeader(TaurusHeader(context))
        else pullToRefresh?.setRefreshHeader(PhoenixHeader(context))
        (activity as HomeActivity).updateAdapter(this@HomeFragment)

        onLoadBase()
    }

    private fun onLoadBase() = launch {
        val currentClimate = viewModel.climate().value.await()
        val currentWeather = viewModel.weather().value.await()
        val currentWind = viewModel.wind().value.await()

        currentClimate.observe(viewLifecycleOwner, Observer{
            if (it == null || it.size < 12) return@Observer
            if (it.isNotEmpty()) {
                weatherModel.climate = ArrayList(it)
                bindUI()
            }else errorConnection()
        })

        currentWeather.observe(viewLifecycleOwner, Observer{
            if (it == null || it.size < 12) return@Observer
            weatherModel.weather = ArrayList(it)
        })

        currentWind.observe(viewLifecycleOwner, Observer{
            if (it == null || it.size < 12) return@Observer
            weatherModel.wind = ArrayList(it)
        })

    }

    private fun bindUI() = launch{
        val citiesInfo = viewModel.citiesInfoModels().value.await()
        citiesInfo.observe(viewLifecycleOwner, Observer {
            if (it == null || it.isEmpty()) return@Observer
            homeAdapter = HomeFragmentAdapter(listInfoModel = it, listCurrentWeather = weatherModel, unitProvider = viewModel.mUnitProvider)
            recyclerView?.layoutManager = LinearLayoutManager(context)
            recyclerView?.adapter = homeAdapter
            pullToRefresh?.finishRefresh()
            spinKit?.visibility = View.GONE
            spinKit?.clearAnimation()
        })
    }

    override fun loadBaseHome() {
        onLoadBase()
    }

    override fun restartAdapterHome() {
        homeAdapter?.notifyDataSetChanged()
    }

    private fun errorConnection(){
        if (OasisApplication.isStartActivity) {
            Handler().postDelayed({
                setStatusBarColor(R.color.colorConnectTwo)
                activity?.frame_connect?.visibility = View.VISIBLE
            }, 4000)
        } else {
            setStatusBarColor(R.color.colorConnectTwo)
            activity?.frame_connect?.visibility = View.VISIBLE
        }
    }

    private fun setStatusBarColor(color: Int){
        activity?.window?.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        activity?.window?.statusBarColor = ContextCompat.getColor(requireContext(), color)
    }

}
