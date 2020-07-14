package com.rangedroid.javoh.oasis.ui.fragments

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.LinearLayout
import android.widget.ProgressBar
import androidx.lifecycle.Observer
import androidx.viewpager.widget.ViewPager
import com.ToxicBakery.viewpager.transforms.ZoomOutTransformer
import com.rangedroid.javoh.oasis.R
import com.rangedroid.javoh.oasis.ui.adapters.AppsAdapter
import com.rangedroid.javoh.oasis.ui.base.ScopedFragment
import com.tbuonomo.viewpagerdotsindicator.SpringDotsIndicator
import kotlinx.coroutines.launch
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.closestKodein
import org.kodein.di.generic.instance

class OtherAppsFragment : ScopedFragment(R.layout.other_apps_fragment), KodeinAware {

    override val kodein by closestKodein()
    private lateinit var viewModel: OtherAppsViewModel
    private val viewModelFactory: OtherAppsViewModelFactory by instance<OtherAppsViewModelFactory>()

    private var appsAdapter: AppsAdapter? = null
    private lateinit var mViewPager: ViewPager
    private lateinit var springDotsIndicator: SpringDotsIndicator

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        springDotsIndicator = view.findViewById(R.id.spring_dots_indicator)
        mViewPager = view.findViewById(R.id.apps_viewpager)
        mViewPager.adapter = appsAdapter
        mViewPager.setPageTransformer(true, ZoomOutTransformer())

    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(OtherAppsViewModel::class.java)
        bindUI()
    }

    private fun bindUI() = launch{
        viewModel.listModel().value.await().observe(viewLifecycleOwner, Observer{
            if (it == null) return@Observer
            Log.d("BAG", it.toString())
            appsAdapter = AppsAdapter(it, viewModel.mUnitProvider.isLocale())
            mViewPager.adapter = appsAdapter
            springDotsIndicator.setViewPager(mViewPager)
        })
    }

}