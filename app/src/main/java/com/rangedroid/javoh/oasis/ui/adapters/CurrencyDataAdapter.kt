package com.rangedroid.javoh.oasis.ui.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.rangedroid.javoh.oasis.R
import com.rangedroid.javoh.oasis.data.db.entity.currency.CurrencyModel
import com.rangedroid.javoh.oasis.ui.fragments.CurrencyFragment.Companion.ccyRate
import com.rangedroid.javoh.oasis.ui.fragments.CurrencyFragment.Companion.clicItem
import com.rangedroid.javoh.oasis.ui.fragments.CurrencyFragment.Companion.listMainId
import java.lang.ref.WeakReference
import java.text.DecimalFormat

@Suppress("DEPRECATION")
class CurrencyDataAdapter (
    private var listCurrencyModel: List<CurrencyModel>,
    private var listFlags: HashMap<String, Int>, private val language: Boolean,
    private var editCcy: EditText, private var relativeMain: RelativeLayout
): RecyclerView.Adapter<CurrencyDataAdapter.CurrencyViewHolder>() {
    var summ: Double = 0.0
    private var df: DecimalFormat = DecimalFormat("#,###.##")
    private var pos: Int = 0

    class CurrencyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var imgFlag: ImageView = view.findViewById(R.id.img_flag)
        var tvCcy: TextView = view.findViewById(R.id.tv_ccy)
        var tvCcyNm: TextView = view.findViewById(R.id.tv_ccynm)
        var tvRate: TextView = view.findViewById(R.id.tv_ccy_rate)
        var relative: RelativeLayout = view.findViewById(R.id.relativ_item_ccy)
        var viewItem: View = view.findViewById(R.id.view_item_ccy)
        var mView = view
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CurrencyViewHolder {
        return CurrencyViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_list_currency,
                parent,
                false)
        )
    }

    override fun getItemCount(): Int {
        return listMainId.size
}

    override fun onBindViewHolder(holder: CurrencyViewHolder, position: Int) {
        holder.relative.setBackgroundResource(R.color.colorPanelCcy)
        if (clicItem){
            if (pos == position){
                listCurrencyModel.forEach {
                    if (it.ccy == listMainId[position]) {
                        holder.imgFlag.setImageResource(listFlags[listMainId[position]]!!)
                        holder.tvCcy.text = it.ccy
                        if (language) {
                            holder.tvCcyNm.text = it.ccyNmEN
                        } else {
                            holder.tvCcyNm.text = it.ccyNmRU
                        }
                        holder.tvRate.text = df.format(summ)
                        holder.relative.setBackgroundResource(R.color.colorPanelMainCcy)
                    }
                }
            }else{
                listCurrencyModel.forEach {
                    if (it.ccy == listMainId[position]){
                        holder.imgFlag.setImageResource(listFlags[listMainId[position]]!!)
                        holder.tvCcy.text = it.ccy
                        if (language){
                            holder.tvCcyNm.text = it.ccyNmEN
                        }else {
                            holder.tvCcyNm.text = it.ccyNmRU
                        }
                        holder.tvRate.text = df.format((ccyRate / it.rate.toDouble()) * summ)
                    }
                }
            }
        }else{
            listCurrencyModel.forEach {
                if (it.ccy == listMainId[position]){
                    holder.imgFlag.setImageResource(listFlags[listMainId[position]]!!)
                    holder.tvCcy.text = it.ccy
                    if (language){
                        holder.tvCcyNm.text = it.ccyNmEN
                    }else {
                        holder.tvCcyNm.text = it.ccyNmRU
                    }
                    holder.tvRate.text = df.format(summ / it.rate.toDouble())
                }
            }
        }

        holder.viewItem.setOnClickListener {
            editCcy.setText("")
            holder.tvRate.text = ""
            holder.relative.setBackgroundResource(R.color.colorPanelMainCcy)
            relativeMain.setBackgroundResource(R.color.colorPanelCcy)
            clicItem = true
            listCurrencyModel.forEach { list ->
                if (list.ccy == listMainId[position]){
                    ccyRate = list.rate.toDouble()
                }
            }
            pos = position
            editCcy.requestFocus()
            val imm: WeakReference<InputMethodManager> = WeakReference(it.context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager)
            imm.get()?.showSoftInput(editCcy, InputMethodManager.SHOW_IMPLICIT)
        }
    }

}