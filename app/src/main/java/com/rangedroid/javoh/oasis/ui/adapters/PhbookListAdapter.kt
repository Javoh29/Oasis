package com.rangedroid.javoh.oasis.ui.adapters

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.RecyclerView
import com.rangedroid.javoh.oasis.R
import com.rangedroid.javoh.oasis.data.models.PhbookModel

class PhbookListAdapter(private val listPhbookModel: List<PhbookModel>, private val pos: Int, private val language: Boolean) :
    RecyclerView.Adapter<PhbookListAdapter.PhbookViewHolder>() {

    class PhbookViewHolder(view: View): RecyclerView.ViewHolder(view) {
        var tvTextEn = view.findViewById<TextView>(R.id.tv_text_en)
        var tvTextUz = view.findViewById<TextView>(R.id.tv_text_uz)
        var imgSound = view.findViewById<ImageView>(R.id.img_sound)
        var imgCopy = view.findViewById<ImageView>(R.id.img_copy)
        var linear = view.findViewById<LinearLayout>(R.id.linear_phbook)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PhbookViewHolder {
        return PhbookViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_list_phrasebooklist,
                parent,
                false)
        )
    }

    override fun getItemCount(): Int {
        return listPhbookModel[pos].text_en.size
    }

    override fun onBindViewHolder(holder: PhbookViewHolder, position: Int) {
        if (language){
            holder.tvTextEn.text = listPhbookModel[pos].text_en[position]
        }else{
            holder.tvTextEn.text = listPhbookModel[pos].text_ru[position]
        }
        holder.tvTextUz.text = listPhbookModel[pos].text_uz[position]
        when(listPhbookModel[pos].fon){
            1 -> {
                holder.linear.setBackgroundResource(R.drawable.ab_fon_sights)
            }
            2 -> {
                holder.linear.setBackgroundResource(R.drawable.ab_fon_museums)
            }
            3 -> {
                holder.linear.setBackgroundResource(R.drawable.ab_fon_res)
            }
            4 -> {
                holder.linear.setBackgroundResource(R.drawable.ab_fon_hotels)
            }
            5 -> {
                holder.linear.setBackgroundResource(R.drawable.ab_fon_markets)
            }
            6 -> {
                holder.linear.setBackgroundResource(R.drawable.ab_fon_banks)
            }
        }

        holder.imgCopy.setOnClickListener {
            val clipboard: ClipboardManager = it.context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
            val clip: ClipData = ClipData.newPlainText("", listPhbookModel[pos].text_uz[position])
            clipboard.setPrimaryClip(clip)
            Toast.makeText(it.context, "Text copy", Toast.LENGTH_LONG).show()
        }

        holder.imgSound.setOnClickListener {
            Toast.makeText(it.context, it.context.getString(R.string.text_soon), Toast.LENGTH_LONG).show()
        }

    }
}