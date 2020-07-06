package com.rangedroid.javoh.oasis.ui.adapters

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.widget.AppCompatImageView
import androidx.recyclerview.widget.RecyclerView
import com.rangedroid.javoh.oasis.R
import com.rangedroid.javoh.oasis.data.db.unitlocalized.UnitSpecificTours
import com.rangedroid.javoh.oasis.utils.UniversalImageLoader

class ToursCategoryAdapter(private val listToursModel: List<UnitSpecificTours>): RecyclerView.Adapter<ToursCategoryAdapter.ToursCategoryViewHolder>(){
    class ToursCategoryViewHolder(view: View): RecyclerView.ViewHolder(view){
        var tvTitle: TextView = view.findViewById(R.id.tv_tours_title)
        var imgTours: AppCompatImageView = view.findViewById(R.id.img_tours)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ToursCategoryViewHolder {
        return ToursCategoryViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_tours_category_container,
                parent,
                false)
        )
    }

    override fun getItemCount(): Int {
        return listToursModel.size
    }

    override fun onBindViewHolder(holder: ToursCategoryViewHolder, position: Int) {
        holder.tvTitle.text = listToursModel[position].title
        UniversalImageLoader.setImage(listToursModel[position].photo, holder.imgTours, null)

        holder.imgTours.setOnClickListener {
            val bundle = Bundle()
            bundle.putString("ds", listToursModel[position].dataSnap)
        }
    }
}