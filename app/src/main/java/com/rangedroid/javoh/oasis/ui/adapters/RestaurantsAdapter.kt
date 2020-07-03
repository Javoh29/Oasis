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
import com.rangedroid.javoh.oasis.data.db.entity.firebase.RestaurantsModel
import com.sackcentury.shinebuttonlib.ShineButton
import com.skydoves.elasticviews.ElasticButton
import kotlin.math.roundToInt

class RestaurantsAdapter(): RecyclerView.Adapter<RestaurantsAdapter.RestaurantsViewHolder>() {

    private val cities = "cities"
    private val cityInfo = "city_more_info_en"
    private val restaurants = "restaurants"
    private var listRestModel: ArrayList<RestaurantsModel> = ArrayList()
    private var listRestModelReserv: ArrayList<RestaurantsModel> = ArrayList()
    private var listLikesBool: ArrayList<Boolean> = ArrayList()
    private var listLikesBoolReserv: ArrayList<Boolean> = ArrayList()
    private var locationA: Location = Location("point A")
    private var locationB: Location = Location("point B")

    @SuppressLint("CommitPrefEdits")
    constructor(listRestModel: List<RestaurantsModel>, listLikesBool: ArrayList<Boolean>, lat: Double, lon: Double) : this(){
        this.listRestModel = ArrayList(listRestModel)
        listRestModelReserv = ArrayList(listRestModel)
        this.listLikesBool = ArrayList(listLikesBool)
        listLikesBoolReserv = ArrayList(listLikesBool)
        locationA.latitude = lat
        locationA.longitude = lon
    }

    class RestaurantsViewHolder(view: View): RecyclerView.ViewHolder(view){
        var tvRestName: TextView = view.findViewById(R.id.tv_rest_name)
        var tvRestAddress: TextView = view.findViewById(R.id.tv_rest_address)
        var tvDistance: TextView = view.findViewById(R.id.tv_distance_rest)
        var fc: FoldingCell = view.findViewById(R.id.folding_rest_cell)

        var likeBtn: ShineButton = view.findViewById(R.id.like_btn_rest)
        var tvLikeQty: TextView = view.findViewById(R.id.tv_like_qty_rest)
        var tvRestNameCell: TextView = view.findViewById(R.id.tv_rest_name_cell)
        var tvRestTel: TextView = view.findViewById(R.id.tv_rest_tel)
        var btnCall: TextView = view.findViewById(R.id.btn_rest_call)
        var tvWorkHours: TextView = view.findViewById(R.id.tv_rest_work_hours)
        var tvTimeOpen: TextView = view.findViewById(R.id.tv_time_open)
        var tvTimeClose: TextView = view.findViewById(R.id.tv_time_close)
        var tvPrices: TextView = view.findViewById(R.id.tv_prices)
        var tvWifi: TextView = view.findViewById(R.id.tv_wifi)
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

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RestaurantsViewHolder {
        return RestaurantsViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_list_restaurants,
                parent,
                false)
        )
    }

    override fun getItemCount(): Int {
        return listRestModel.size
    }

    @SuppressLint("SetTextI18n", "MissingPermission", "ResourceAsColor")
    override fun onBindViewHolder(holder: RestaurantsViewHolder, position: Int) {
        locationB.latitude = listRestModel[position].lat
        locationB.longitude = listRestModel[position].lon
        val distance: Float = locationA.distanceTo(locationB)
        holder.tvRestName.text = listRestModel[position].name
        holder.tvRestAddress.text = "${holder.mView.context.getString(R.string.text_address)} ${listRestModel[position].address}"
        holder.tvLikeQty.text = listRestModel[position].like.toString()
        holder.tvRestNameCell.text = listRestModel[position].name
        holder.tvRestTel.text = "${holder.mView.context.getString(R.string.text_tel_num)} ${listRestModel[position].tel}"
        holder.tvTimeOpen.text = "${holder.mView.context.getString(R.string.text_time_open)} ${listRestModel[position].timeOpen}"
        holder.tvTimeClose.text = "${holder.mView.context.getString(R.string.text_time_close)} ${listRestModel[position].timeClose}"
        holder.likeBtn.isChecked = listLikesBool[position]

        if (listRestModel[position].time == "yes"){
            holder.tvWorkHours.text = "${holder.mView.context.getString(R.string.text_work_time)} ${holder.mView.context.getString(R.string.text_work_time_rest_yes)}"
        }else{
            holder.tvWorkHours.text = "${holder.mView.context.getString(R.string.text_work_time)} ${holder.mView.context.getString(R.string.text_work_time_rest_no)}"
        }

        when (listRestModel[position].prices) {
            1 -> {
                holder.tvPrices.text = "${holder.mView.context.getString(R.string.text_prices)} ${holder.mView.context.getString(R.string.text_prices_1)}"
            }
            2 -> {
                holder.tvPrices.text = "${holder.mView.context.getString(R.string.text_prices)} ${holder.mView.context.getString(R.string.text_prices_2)}"
            }
            else -> {
                holder.tvPrices.text = "${holder.mView.context.getString(R.string.text_prices)} ${holder.mView.context.getString(R.string.text_prices_3)}"
            }
        }

        if (listRestModel[position].wifi == "yes"){
            holder.tvWifi.text = "${holder.mView.context.getString(R.string.text_wifi)} ${holder.mView.context.getString(R.string.text_yes)}"
        }else{
            holder.tvWifi.text = "${holder.mView.context.getString(R.string.text_wifi)} ${holder.mView.context.getString(R.string.text_not)}"
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
            it.context.startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("http://maps.google.com/maps?saddr=${locationA.latitude},${locationA.longitude}&daddr=${listRestModel[position].lat},${listRestModel[position].lon}")))
        }

        holder.btnCall.setOnClickListener {
            it.context.startActivity(Intent(Intent.ACTION_CALL, Uri.fromParts("tel",listRestModel[position].tel, "banks_fragment")))
        }

        holder.likeBtn.setOnCheckStateChangeListener { view, checked ->
            if (checked){
                holder.dataSnapshot!!.child(cities).child(cityInfo).child(listRestModel[position].link).child(restaurants)
                    .child(listRestModel[position].dataSnap).child("like")
                    .ref.setValue(listRestModel[position].like + 1)
                listRestModel[position].like++
                holder.tvLikeQty.text =
                    listRestModel[position].like.toString()
                PreferenceManager.getDefaultSharedPreferences(view.context).edit().putBoolean(listRestModel[position].dataSnap, true).apply()
            }else {
                holder.dataSnapshot!!.child(cities).child(cityInfo).child(listRestModel[position].link).child(restaurants)
                    .child(listRestModel[position].dataSnap).child("like")
                    .ref.setValue(listRestModel[position].like - 1)
                listRestModel[position].like--
                holder.tvLikeQty.text =
                    listRestModel[position].like.toString()
                PreferenceManager.getDefaultSharedPreferences(view.context).edit().putBoolean(listRestModel[position].dataSnap, true).apply()
            }
        }
    }

    @SuppressLint("DefaultLocale")
    fun searchText(text: String){
        listRestModel.clear()
        listLikesBool.clear()
        if (text == ""){
            listRestModel = ArrayList(listRestModelReserv)
            listLikesBool = ArrayList(listLikesBoolReserv)
        }else{
            for (i in 0 until listRestModelReserv.size){
                if (listRestModelReserv[i].name.toLowerCase().contains(text.toLowerCase())){
                    val model = RestaurantsModel(
                        address = listRestModelReserv[i].address,
                        lat = listRestModelReserv[i].lat,
                        lon = listRestModelReserv[i].lon,
                        like = listRestModelReserv[i].like,
                        tel = listRestModelReserv[i].tel,
                        name = listRestModelReserv[i].name,
                        time = listRestModelReserv[i].time,
                        timeOpen = listRestModelReserv[i].timeOpen,
                        timeClose = listRestModelReserv[i].timeClose,
                        prices = listRestModelReserv[i].prices,
                        wifi = listRestModelReserv[i].wifi,
                        link = listRestModelReserv[i].link,
                        dataSnap = listRestModelReserv[i].dataSnap
                    )
                    listRestModel.add(model)
                    listLikesBool.add(listLikesBoolReserv[i])
                }
            }
        }
        notifyDataSetChanged()
    }
}