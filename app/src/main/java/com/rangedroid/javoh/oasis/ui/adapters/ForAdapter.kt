package com.rangedroid.javoh.oasis.ui.adapters

import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.ms.square.android.expandabletextview.ExpandableTextView
import com.rangedroid.javoh.oasis.R
import com.rangedroid.javoh.oasis.data.models.ForTouristsModel

@Suppress("DEPRECATION")
class ForAdapter(private val listForModel: List<ForTouristsModel>, private val language: Boolean): RecyclerView.Adapter<ForAdapter.ForViewHolder>() {

    class ForViewHolder(view: View): RecyclerView.ViewHolder(view){
        var tvTitle: TextView = view.findViewById(R.id.tv_title)
        var tvExpand: ExpandableTextView = view.findViewById(R.id.tv_expand)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ForViewHolder {
        return ForViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_tourists_container,
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return listForModel.size
    }

    override fun onBindViewHolder(holder: ForViewHolder, position: Int) {
        if (language){
            holder.tvTitle.text = listForModel[position].titleEn
            holder.tvExpand.text = Html.fromHtml(listForModel[position].textEn)
        }else{
            holder.tvTitle.text = listForModel[position].titleRu
            holder.tvExpand.text = Html.fromHtml(listForModel[position].textRu)
        }

    }
}