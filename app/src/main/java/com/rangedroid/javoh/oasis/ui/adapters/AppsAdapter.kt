package com.rangedroid.javoh.oasis.ui.adapters

import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.TextView
import androidx.viewpager.widget.PagerAdapter
import com.rangedroid.javoh.oasis.R
import com.rangedroid.javoh.oasis.data.db.entity.firebase.MoreAppsModel
import com.rangedroid.javoh.oasis.utils.UniversalImageLoader

class AppsAdapter(private val listModel: List<MoreAppsModel>, private val language: Boolean): PagerAdapter() {

    override fun getCount(): Int {
        return listModel.size
    }

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view == `object` as FrameLayout
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val view: View = LayoutInflater.from(container.context).inflate(R.layout.item_list_more_apps, container, false)

        val photoApp: ImageView = view.findViewById(R.id.img_apps)
        val appName: TextView = view.findViewById(R.id.tv_app_name)
        val appInfo: TextView = view.findViewById(R.id.tv_app_info)
        val btnInstall: Button = view.findViewById(R.id.btn_install)

        UniversalImageLoader.setImage(listModel[position].photo_app, photoApp, null)
        if (language){
            appInfo.text = listModel[position].app_info_en
            appName.text = listModel[position].app_name_en
            btnInstall.text = listModel[position].btn_text_en
        }else{
            appInfo.text = listModel[position].app_info_ru
            appName.text = listModel[position].app_name_ru
            btnInstall.text = listModel[position].btn_text_ru
        }

        btnInstall.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW)
            if (listModel[position].app_url.substring(0,1) == "h"){
                intent.data = Uri.parse(listModel[position].app_url)
            }else {
                intent.data = Uri.parse("market://details?id=" + listModel[position].app_url)
            }
            container.context.startActivity(intent)
        }

        container.addView(view)

        return view
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        super.destroyItem(container, position, `object`)
        container.removeView(`object` as FrameLayout?)
    }
}