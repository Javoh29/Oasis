package com.rangedroid.javoh.oasis.ui.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.rangedroid.javoh.oasis.R
import com.rangedroid.javoh.oasis.data.models.HolidaysModel
import com.rangedroid.javoh.oasis.ui.adapters.HolidaysAdapter

class HolidaysFragment : Fragment(R.layout.holidays_fragment) {

    private lateinit var recyclerView: RecyclerView

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recyclerView = view.findViewById(R.id.recycler_view)

        val list: ArrayList<HolidaysModel> = ArrayList()
        list.add(HolidaysModel(image = R.drawable.img_yan1, title = R.string.text_yan1, date = R.string.text_yan_1))
        list.add(HolidaysModel(image = R.drawable.img_yan14, title = R.string.text_yan14, date = R.string.text_yan_14))
        list.add(HolidaysModel(image = R.drawable.img_mar8, title = R.string.text_mar8, date = R.string.text_mar_8))
        list.add(HolidaysModel(image = R.drawable.img_mar21, title = R.string.text_mar21, date = R.string.text_mar_21))
        list.add(HolidaysModel(image = R.drawable.img_may9, title = R.string.text_may9, date = R.string.text_may))
        list.add(HolidaysModel(image = R.drawable.img_sep1, title = R.string.text_sep1, date = R.string.text_sep))
        list.add(HolidaysModel(image = R.drawable.img_oct1, title = R.string.text_oct1, date = R.string.text_oct))
        list.add(HolidaysModel(image = R.drawable.img_dec8, title = R.string.text_dec8, date = R.string.text_dec))
        list.add(HolidaysModel(image = R.drawable.img_eid, title = R.string.def_text, date = R.string.def_text))
        list.add(HolidaysModel(image = R.drawable.img_eid_al_adha, title = R.string.def_text, date = R.string.def_text))

        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = HolidaysAdapter(list)
    }

}