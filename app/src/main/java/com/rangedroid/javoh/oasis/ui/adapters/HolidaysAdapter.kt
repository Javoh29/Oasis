package com.rangedroid.javoh.oasis.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.widget.AppCompatImageView
import androidx.recyclerview.widget.RecyclerView
import com.rangedroid.javoh.oasis.R
import com.rangedroid.javoh.oasis.data.models.HolidaysModel

class HolidaysAdapter(private val listModel: List<HolidaysModel>): RecyclerView.Adapter<HolidaysAdapter.HolidaysViewHolder>(){

    class HolidaysViewHolder(view: View) : RecyclerView.ViewHolder(view){
        val imgFon: AppCompatImageView = view.findViewById(R.id.img_view)
        val tvDate: TextView = view.findViewById(R.id.tv_view)
        val btnTitle: Button = view.findViewById(R.id.btn_view)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HolidaysViewHolder {
        return HolidaysViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_holidays_container,
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return listModel.size
    }

    override fun onBindViewHolder(holder: HolidaysViewHolder, position: Int) {
        holder.imgFon.setImageResource(listModel[position].image)
        holder.tvDate.setText(listModel[position].date)
        holder.btnTitle.setText(listModel[position].title)
    }
}