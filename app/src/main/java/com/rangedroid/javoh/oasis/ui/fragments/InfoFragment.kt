package com.rangedroid.javoh.oasis.ui.fragments

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import android.view.View
import android.widget.FrameLayout
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.viewpager.widget.ViewPager
import com.ToxicBakery.viewpager.transforms.ZoomOutTransformer
import com.github.ybq.android.spinkit.SpinKitView
import com.rangedroid.javoh.oasis.R
import com.rangedroid.javoh.oasis.data.db.entity.firebase.CitiesMoreInfo
import com.rangedroid.javoh.oasis.ui.adapters.ImageAdapter
import com.rangedroid.javoh.oasis.ui.base.ScopedFragment
import com.rangedroid.javoh.oasis.utils.UnitTheme
import kotlinx.android.synthetic.main.info_fragment.*
import kotlinx.coroutines.launch
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.closestKodein
import org.kodein.di.generic.instance
import ru.tinkoff.scrollingpagerindicator.ScrollingPagerIndicator

@Suppress("DEPRECATION")
class InfoFragment : ScopedFragment(R.layout.info_fragment), KodeinAware {

    override val kodein by closestKodein()
    private val viewModelFactory: InfoViewModelFactory by instance<InfoViewModelFactory>()
    private lateinit var viewModel: InfoViewModel

    private var mViewPager: ViewPager? = null
    private var imageAdapter: ImageAdapter? = null
    private var indicator: ScrollingPagerIndicator? = null
    private lateinit var spinKit: SpinKitView
    private lateinit var frameInfo: FrameLayout
    private lateinit var tvInfo: TextView

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mViewPager = view.findViewById(R.id.view_pager_info)
        indicator = view.findViewById(R.id.indicator)
        tvInfo = view.findViewById(R.id.tv_full_info)
        spinKit = view.findViewById(R.id.spin_kit_info)
        frameInfo = view.findViewById(R.id.frame_info)

        clickListener()
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(InfoViewModel::class.java)

        loadData(snapData = arguments?.let {
            InfoFragmentArgs.fromBundle(it)
        }?.snapData!!)
    }

    private fun loadData(snapData: String) = launch {
        val citiesMoreInfo = viewModel.citiesMoreInfo().value.await()
        citiesMoreInfo.observe(viewLifecycleOwner, Observer {
            if (it == null || it.isEmpty()) return@Observer
            it.forEach {list ->
                if (list.dataSnap == snapData) bindUI(list)
            }
        })
    }

    private fun bindUI(citiesMoreInfo: CitiesMoreInfo){
        imageAdapter = if (viewModel.mUnitProvider.getUnitTheme() == UnitTheme.DAY) {
            ImageAdapter(citiesMoreInfo.photos)
        }else ImageAdapter(citiesMoreInfo.photosNight)
        tvInfo.text = if (viewModel.mUnitProvider.isLocale()){
            citiesMoreInfo.textInfoEn
        }else citiesMoreInfo.textInfoRu
        mViewPager?.adapter = imageAdapter
        mViewPager?.setPageTransformer(true, ZoomOutTransformer())
        indicator?.attachToPager(mViewPager!!)
        spinKit.visibility = View.GONE
        spinKit.clearAnimation()
        frameInfo.visibility = View.VISIBLE
    }

    private fun clickListener(){
        relative_info_sight.setOnClickListener {
        }

        relative_info_hotel.setOnClickListener {
        }

        relative_info_bank.setOnClickListener {
        }

        relative_info_resta.setOnClickListener {
        }

        relative_info_museums.setOnClickListener {
        }

        relative_info_markets.setOnClickListener {
        }
    }

}