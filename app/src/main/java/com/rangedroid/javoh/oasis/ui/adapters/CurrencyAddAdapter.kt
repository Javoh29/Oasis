package com.rangedroid.javoh.oasis.ui.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RadioButton
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.rangedroid.javoh.oasis.R
import com.rangedroid.javoh.oasis.data.db.entity.currency.CurrencyModel
import com.rangedroid.javoh.oasis.ui.fragments.CurrencyFragment.Companion.listMainId

@Suppress("DEPRECATION")
class CurrencyAddAdapter constructor():
    RecyclerView.Adapter<CurrencyAddAdapter.CurrencyAddHolder>() {

    private var listCcyModel: ArrayList<CurrencyModel> = ArrayList()
    private var listCcyModelReserv: ArrayList<CurrencyModel> = ArrayList()
    private var listFlags: HashMap<String, Int> = HashMap()
    private var language: Boolean = false

    constructor(listCcyModel: List<CurrencyModel>, listFlags: HashMap<String, Int>, language: Boolean) : this() {
        this.listCcyModel = ArrayList(listCcyModel)
        listCcyModelReserv = ArrayList(listCcyModel)
        this.listFlags = HashMap(listFlags)
        this.language = language
    }

    class CurrencyAddHolder(view: View) : RecyclerView.ViewHolder(view) {
        var imgFlag: ImageView = view.findViewById(R.id.img_add_flag)
        var tvCcy: TextView = view.findViewById(R.id.tv_add_ccy)
        var tvCcyNm: TextView = view.findViewById(R.id.tv_add_ccynm)
        var relative: RelativeLayout = view.findViewById(R.id.relativ_item_add_ccy)
        var viewItem: View = view.findViewById(R.id.view_item_add_ccy)
        var radioBtn: RadioButton = view.findViewById(R.id.radio_btn_add)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CurrencyAddHolder {
        return CurrencyAddHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_list_add_ccy,
                parent,
                false)
        )
    }

    override fun getItemCount(): Int {
        return listCcyModel.size
    }

    override fun onBindViewHolder(holder: CurrencyAddHolder, position: Int) {
        holder.imgFlag.setImageResource(listFlags[listCcyModel[position].ccy]!!)
        holder.tvCcy.text = listCcyModel[position].ccy
        if (language){
            holder.tvCcyNm.text = listCcyModel[position].ccyNmEN
        }else {
            holder.tvCcyNm.text = listCcyModel[position].ccyNmRU
        }
        if (listMainId.contains(listCcyModel[position].ccy)){
            holder.radioBtn.isChecked = true
            holder.relative.setBackgroundResource(R.color.colorPanelMainCcy)
        }else{
            holder.radioBtn.isChecked = false
            holder.relative.setBackgroundResource(R.color.colorPanel)
        }

        holder.viewItem.setOnClickListener {
            if (holder.radioBtn.isChecked){
                holder.radioBtn.isChecked = false
                listMainId.remove(listCcyModel[position].ccy)
                holder.relative.setBackgroundResource(R.color.colorPanel)
            }else{
                listMainId.add(listCcyModel[position].ccy)
                holder.radioBtn.isChecked = true
                holder.relative.setBackgroundResource(R.color.colorPanelMainCcy)
            }
        }
    }

    @SuppressLint("DefaultLocale")
    fun searchText(text: String){
        listCcyModel.clear()
        if (text.isEmpty()){
            listCcyModel = ArrayList(listCcyModelReserv)
        }else{
            for (i in 0 until listCcyModelReserv.size){
                if (listCcyModelReserv[i].ccy.toLowerCase().contains(text.toLowerCase()) || listCcyModelReserv[i].ccyNmEN.toLowerCase().contains(text.toLowerCase()) || listCcyModelReserv[i].ccyNmRU.toLowerCase().contains(text.toLowerCase())){
                    val model = CurrencyModel(
                        id = listCcyModelReserv[i].id,
                        ccy = listCcyModelReserv[i].ccy,
                        ccyNmEN = listCcyModelReserv[i].ccyNmEN,
                        ccyNmRU = listCcyModelReserv[i].ccyNmRU,
                        rate = listCcyModelReserv[i].rate,
                        date = listCcyModelReserv[i].date,
                        nominal = listCcyModelReserv[i].nominal,
                        ccyNmUZC = listCcyModelReserv[i].ccyNmUZC,
                        ccyNmUZ = listCcyModelReserv[i].ccyNmUZ,
                        ccyMnrUnts = listCcyModelReserv[i].ccyMnrUnts,
                        ccyId = listCcyModelReserv[i].ccyId
                    )
                    listCcyModel.add(model)
                }
            }
        }
        notifyDataSetChanged()
    }
}