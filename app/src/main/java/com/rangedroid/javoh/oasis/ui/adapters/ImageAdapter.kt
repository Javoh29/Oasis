package com.rangedroid.javoh.oasis.ui.adapters

import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.viewpager.widget.PagerAdapter
import androidx.viewpager.widget.ViewPager
import com.rangedroid.javoh.oasis.utils.UniversalImageLoader

class ImageAdapter constructor(private var listImgUrl: List<String>): PagerAdapter() {

    override fun getCount(): Int {
        return listImgUrl.size
    }

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view == `object` as ImageView
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val imageView = ImageView(container.context)
        imageView.scaleType = ImageView.ScaleType.CENTER_CROP
        UniversalImageLoader.setImage(listImgUrl[position], imageView,  null)
        (container as ViewPager).addView(imageView, 0)
        return imageView
    }

    override fun getItemPosition(`object`: Any): Int {
        return POSITION_NONE
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        (container as ViewPager).removeView(`object` as ImageView)
    }
}