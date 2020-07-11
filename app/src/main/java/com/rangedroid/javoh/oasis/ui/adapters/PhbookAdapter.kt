package com.rangedroid.javoh.oasis.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.rangedroid.javoh.oasis.R
import com.rangedroid.javoh.oasis.data.models.PhbookModel
import com.rangedroid.javoh.oasis.ui.fragments.PhrasebookFragmentDirections

class PhbookAdapter(private val listPhbookModel: List<PhbookModel>, private val language: Boolean) :
    RecyclerView.Adapter<PhbookAdapter.PhbookViewHolder>() {

    class PhbookViewHolder(view: View): RecyclerView.ViewHolder(view) {
        var btnText: Button = view.findViewById(R.id.btn_phbook)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PhbookViewHolder {
        return PhbookViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_list_phrasebook,
                parent,
                false)
        )
    }

    override fun getItemCount(): Int {
        return listPhbookModel.size
    }

    override fun onBindViewHolder(holder: PhbookViewHolder, position: Int) {
        if (language){
            holder.btnText.text = listPhbookModel[position].name_en
        }else{
            holder.btnText.text = listPhbookModel[position].name_ru
        }
        when(listPhbookModel[position].fon){
            1 -> {
                holder.btnText.setBackgroundResource(R.drawable.img_main_fon_sights)
            }
            2 -> {
                holder.btnText.setBackgroundResource(R.drawable.img_main_fon_museums)
            }
            3 -> {
                holder.btnText.setBackgroundResource(R.drawable.img_fon_restaurant)
            }
            4 -> {
                holder.btnText.setBackgroundResource(R.drawable.img_fon_hotel)
            }
            5 -> {
                holder.btnText.setBackgroundResource(R.drawable.img_main_fon_markets)
            }
            6 -> {
                holder.btnText.setBackgroundResource(R.drawable.img_fon_bank)
            }
        }

        holder.btnText.setOnClickListener {
            Navigation.findNavController(it).navigate(PhrasebookFragmentDirections.actionPhbookFragmentToPhbookListFragment(position))
        }
    }
}