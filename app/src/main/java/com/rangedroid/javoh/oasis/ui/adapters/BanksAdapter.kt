@file:Suppress("DEPRECATION")

package com.rangedroid.javoh.oasis.ui.adapters

import android.annotation.SuppressLint
import android.content.Intent
import android.location.Location
import android.net.Uri
import android.preference.PreferenceManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.ramotion.foldingcell.FoldingCell
import com.rangedroid.javoh.oasis.R
import com.rangedroid.javoh.oasis.data.db.entity.firebase.BanksModel
import com.sackcentury.shinebuttonlib.ShineButton
import com.skydoves.elasticviews.ElasticButton
import kotlin.math.roundToInt

class BanksAdapter(): RecyclerView.Adapter<BanksAdapter.BanksViewHolder>() {

    private val cities = "cities"
    private val cityInfo = "city_more_info_en"
    private val banks = "banks"
    private var listBanksModel: ArrayList<BanksModel> = ArrayList()
    private var listBanksModelReserv: ArrayList<BanksModel> = ArrayList()
    private var listLikesBool: ArrayList<Boolean> = ArrayList()
    private var listLikesBoolReserv: ArrayList<Boolean> = ArrayList()
    private var locationA: Location = Location("point A")
    private var locationB: Location = Location("point B")

    @SuppressLint("CommitPrefEdits")
    constructor(listBanksModel: List<BanksModel>, listLikesBool: ArrayList<Boolean>, lat: Double, lon: Double) : this(){
        this.listBanksModel = ArrayList(listBanksModel)
        listBanksModelReserv = ArrayList(listBanksModel)
        this.listLikesBool = ArrayList(listLikesBool)
        listLikesBoolReserv = ArrayList(listLikesBool)
        locationA.latitude = lat
        locationA.longitude = lon
    }

    class BanksViewHolder(view: View): RecyclerView.ViewHolder(view){
        var tvHotelName: TextView = view.findViewById(R.id.tv_banks_name)
        var tvHotelAddress: TextView = view.findViewById(R.id.tv_banks_address)
        var tvDistance: TextView = view.findViewById(R.id.tv_distance_banks)
        var fc: FoldingCell = view.findViewById(R.id.folding_banks_cell)

        var likeBtn: ShineButton = view.findViewById(R.id.like_btn_bank)
        var tvLikeQty: TextView = view.findViewById(R.id.tv_like_qty_bank)
        var tvHotelNameCell: TextView = view.findViewById(R.id.tv_bank_name_cell)
        var tvHotelTel: TextView = view.findViewById(R.id.tv_bank_tel)
        var btnCall: TextView = view.findViewById(R.id.btn_bank_call)
        var btnOpen: TextView = view.findViewById(R.id.btn_bank_open)
        var tvWorkHours: TextView = view.findViewById(R.id.tv_bank_work_hours)
        var tvTimeOpen: TextView = view.findViewById(R.id.tv_time_open)
        var tvTimeClose: TextView = view.findViewById(R.id.tv_time_close)
        var tvWebsiteLink: TextView = view.findViewById(R.id.tv_website_link)
        var tvCcyEx: TextView = view.findViewById(R.id.tv_ccyEx)
        var btnOpenMap: ElasticButton = view.findViewById(R.id.btn_open_in_map)
        var dataSnapshot: DataSnapshot? = null
        var mView = view

        init {
            FirebaseDatabase.getInstance().reference.addListenerForSingleValueEvent(object :
                ValueEventListener {
                override fun onCancelled(p0: DatabaseError) {
                }
                override fun onDataChange(ds: DataSnapshot) {
                    dataSnapshot = ds
                }
            })
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BanksViewHolder {
        return BanksViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_list_banks,
                parent,
                false)
        )
    }

    override fun getItemCount(): Int {
        return listBanksModel.size
    }

    @SuppressLint("MissingPermission", "ResourceAsColor", "SetTextI18n")
    override fun onBindViewHolder(holder: BanksViewHolder, position: Int) {
        locationB.latitude = listBanksModel[position].lat
        locationB.longitude = listBanksModel[position].lon
        val distance: Float = locationA.distanceTo(locationB)
        holder.tvHotelName.text = listBanksModel[position].name
        holder.tvHotelAddress.text = "${holder.mView.context.getString(R.string.text_address)} ${listBanksModel[position].address}"
        holder.tvLikeQty.text = listBanksModel[position].like.toString()
        holder.tvHotelNameCell.text = listBanksModel[position].name
        holder.tvHotelTel.text = "${holder.mView.context.getString(R.string.text_tel_num)} ${listBanksModel[position].tel}"
        holder.tvTimeOpen.text = "${holder.mView.context.getString(R.string.text_time_open)} ${listBanksModel[position].timeOpen}"
        holder.tvTimeClose.text = "${holder.mView.context.getString(R.string.text_time_close)} ${listBanksModel[position].timeClose}"
        holder.likeBtn.isChecked = listLikesBool[position]
        holder.tvWebsiteLink.text = listBanksModel[position].website

        if (listBanksModel[position].time == "yes"){
            holder.tvWorkHours.text = "${holder.mView.context.getString(R.string.text_work_time)} ${holder.mView.context.getString(R.string.text_work_time_banks_yes)}"
        }else{
            holder.tvWorkHours.text = "${holder.mView.context.getString(R.string.text_work_time)} ${holder.mView.context.getString(R.string.text_work_time_banks_not)}"
        }

        if (listBanksModel[position].ccyEx == "yes"){
            holder.tvCcyEx.text = "${holder.mView.context.getString(R.string.text_ccy_ex)} ${holder.mView.context.getString(R.string.text_yes)}"
        }else{
            holder.tvCcyEx.text = "${holder.mView.context.getString(R.string.text_ccy_ex)} ${holder.mView.context.getString(R.string.text_not)}"
        }

        if (distance > 1100){
            holder.tvDistance.text = (distance / 1000).roundToInt().toString() + " " + holder.mView.context.getString(R.string.text_from_km)
        }else{
            holder.tvDistance.text = distance.roundToInt().toString() + " " + holder.mView.context.getString(R.string.text_from_m)
        }

        holder.fc
            .setOnClickListener {
                holder.fc.toggle(false)
            }

        holder.btnOpenMap.setOnClickListener {
            it.context.startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("http://maps.google.com/maps?saddr=${locationA.latitude},${locationA.longitude}&daddr=${listBanksModel[position].lat},${listBanksModel[position].lon}")))
        }

        holder.btnCall.setOnClickListener {
            it.context.startActivity(Intent(Intent.ACTION_CALL, Uri.fromParts("tel",listBanksModel[position].tel, "banks_fragment")))
        }

        holder.btnOpen.setOnClickListener {
            it.context.startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("http://${listBanksModel[position].website}/")))
        }

        holder.likeBtn.setOnCheckStateChangeListener { view, checked ->
            if (checked){
                holder.dataSnapshot!!.child(cities).child(cityInfo).child(listBanksModel[position].link).child(banks)
                    .child(listBanksModel[position].dataSnap).child("like")
                    .ref.setValue(listBanksModel[position].like + 1)
                listBanksModel[position].like++
                holder.tvLikeQty.text =
                    listBanksModel[position].like.toString()
                PreferenceManager.getDefaultSharedPreferences(view.context).edit().putBoolean(listBanksModel[position].dataSnap, true).apply()
            }else {
                holder.dataSnapshot!!.child(cities).child(cityInfo).child(listBanksModel[position].link).child(banks)
                    .child(listBanksModel[position].dataSnap).child("like")
                    .ref.setValue(listBanksModel[position].like - 1)
                listBanksModel[position].like--
                holder.tvLikeQty.text =
                    listBanksModel[position].like.toString()
                PreferenceManager.getDefaultSharedPreferences(view.context).edit().putBoolean(listBanksModel[position].dataSnap, true).apply()
            }
        }
    }

    @SuppressLint("DefaultLocale")
    fun searchText(text: String){
        listBanksModel.clear()
        listLikesBool.clear()
        if (text == ""){
            listBanksModel = ArrayList(listBanksModelReserv)
            listLikesBool = ArrayList(listLikesBoolReserv)
        }else{
            for (i in 0 until listBanksModelReserv.size){
                if (listBanksModelReserv[i].name.toLowerCase().contains(text.toLowerCase())){
                    val model = BanksModel(
                        address = listBanksModelReserv[i].address,
                        lat = listBanksModelReserv[i].lat,
                        lon = listBanksModelReserv[i].lon,
                        like = listBanksModelReserv[i].like,
                        tel = listBanksModelReserv[i].tel,
                        name = listBanksModelReserv[i].name,
                        time = listBanksModelReserv[i].time,
                        timeOpen = listBanksModelReserv[i].timeOpen,
                        timeClose = listBanksModelReserv[i].timeClose,
                        website = listBanksModelReserv[i].website,
                        ccyEx = listBanksModelReserv[i].ccyEx,
                        link = listBanksModelReserv[i].link,
                        dataSnap = listBanksModelReserv[i].dataSnap
                    )
                    listBanksModel.add(model)
                    listLikesBool.add(listLikesBoolReserv[i])
                }
            }
        }
        notifyDataSetChanged()
    }
}