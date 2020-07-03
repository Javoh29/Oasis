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
import com.rangedroid.javoh.oasis.data.db.entity.firebase.HotelsModel
import com.sackcentury.shinebuttonlib.ShineButton
import com.skydoves.elasticviews.ElasticButton
import kotlin.math.roundToInt

@Suppress("DEPRECATION")
class HotelsAdapter constructor(): RecyclerView.Adapter<HotelsAdapter.HotelsViewHolder>() {

    private val cities = "cities"
    private val cityInfo = "city_more_info_en"
    private val hotels = "hotels"
    private var listHotelModel: ArrayList<HotelsModel> = ArrayList()
    private var listHotelModelReserv: ArrayList<HotelsModel> = ArrayList()
    private var listLikesBool: ArrayList<Boolean> = ArrayList()
    private var listLikesBoolReserv: ArrayList<Boolean> = ArrayList()
    private var locationA: Location = Location("point A")
    private var locationB: Location = Location("point B")

    @SuppressLint("CommitPrefEdits")
    constructor(listHotelModel: List<HotelsModel>, listLikesBool: ArrayList<Boolean>, lat: Double, lon: Double) : this(){
        this.listHotelModel = ArrayList(listHotelModel)
        listHotelModelReserv = ArrayList(listHotelModel)
        this.listLikesBool = ArrayList(listLikesBool)
        listLikesBoolReserv = ArrayList(listLikesBool)
        locationA.latitude = lat
        locationA.longitude = lon
    }

    class HotelsViewHolder(view: View): RecyclerView.ViewHolder(view){
        var tvHotelName: TextView = view.findViewById(R.id.tv_hotel_name)
        var tvHotelAddress: TextView = view.findViewById(R.id.tv_hotel_address)
        var tvDistance: TextView = view.findViewById(R.id.tv_distance_hotel)
        var fc: FoldingCell = view.findViewById(R.id.folding_hotel_cell)

        var likeBtn: ShineButton = view.findViewById(R.id.like_btn_hotels)
        var tvLikeQty: TextView = view.findViewById(R.id.tv_like_qty_hotels)
        var tvHotelNameCell: TextView = view.findViewById(R.id.tv_hotel_name_cell)
        var tvHotelTel: TextView = view.findViewById(R.id.tv_hotels_tel)
        var btnCall: TextView = view.findViewById(R.id.btn_hotel_call)
        var btnOpen: TextView = view.findViewById(R.id.btn_hotel_open)
        var tvWorkHours: TextView = view.findViewById(R.id.tv_hotels_work_hours)
        var tvTimeOut: TextView = view.findViewById(R.id.tv_time_out)
        var tvTimeIn: TextView = view.findViewById(R.id.tv_time_in)
        var tvWebsiteLink: TextView = view.findViewById(R.id.tv_website_link)
        var tvFast: TextView = view.findViewById(R.id.tv_fast)
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

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HotelsViewHolder {
        return HotelsViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_list_hotels,
                parent,
                false)
        )
    }

    override fun getItemCount(): Int {
        return listHotelModel.size
    }

    @SuppressLint("SetTextI18n", "MissingPermission", "ResourceAsColor")
    override fun onBindViewHolder(holder: HotelsViewHolder, position: Int) {
        locationB.latitude = listHotelModel[position].lat
        locationB.longitude = listHotelModel[position].lon
        val distance: Float = locationA.distanceTo(locationB)
        holder.tvHotelName.text = listHotelModel[position].name
        holder.tvHotelAddress.text = "${holder.mView.context.getString(R.string.text_address)} ${listHotelModel[position].address}"
        holder.tvLikeQty.text = listHotelModel[position].like.toString()
        holder.tvHotelNameCell.text = listHotelModel[position].name
        holder.tvHotelTel.text = "${holder.mView.context.getString(R.string.text_tel_num)} ${listHotelModel[position].tel}"
        holder.tvTimeOut.text = "${holder.mView.context.getString(R.string.text_timeOut_hotels)} ${listHotelModel[position].timeOut}"
        holder.tvTimeIn.text = "${holder.mView.context.getString(R.string.text_timeIn_hotels)} ${listHotelModel[position].timeIn}"
        holder.likeBtn.isChecked = listLikesBool[position]

        if (listHotelModel[position].time == "yes"){
            holder.tvWorkHours.text = "${holder.mView.context.getString(R.string.text_work_time)} ${holder.mView.context.getString(R.string.text_work_time_hotels_yes)}"
        }else{
            holder.tvWorkHours.text = "${holder.mView.context.getString(R.string.text_work_time)} ${holder.mView.context.getString(R.string.text_work_time_hotels_not)}"
        }

        if (listHotelModel[position].wifi == "yes"){
            holder.tvFast.setText(R.string.text_fast_yes)
        }else{
            holder.tvFast.setText(R.string.text_fast_not)
        }

        if (listHotelModel[position].website == "not" || listHotelModel[position].website == "no"){
            holder.tvWebsiteLink.setTextColor(R.color.colorMenuText)
            holder.tvWebsiteLink.setText(R.string.text_not_website)
            holder.btnOpen.visibility = View.GONE
        }else{
            holder.tvWebsiteLink.text = listHotelModel[position].website
            holder.btnOpen.visibility = View.VISIBLE
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
            it.context.startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("http://maps.google.com/maps?saddr=${locationA.latitude},${locationA.longitude}&daddr=${listHotelModel[position].lat},${listHotelModel[position].lon}")))
        }

        holder.btnCall.setOnClickListener {
            it.context.startActivity(Intent(Intent.ACTION_CALL, Uri.fromParts("tel",listHotelModel[position].tel, "hotel_fragment")))
        }

        holder.btnOpen.setOnClickListener {
            it.context.startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("http://${listHotelModel[position].website}/")))
        }

        holder.likeBtn.setOnCheckStateChangeListener { view, checked ->
            if (checked){
                holder.dataSnapshot!!.child(cities).child(cityInfo).child(listHotelModel[position].link).child(hotels)
                    .child(listHotelModel[position].dataSnap).child("like")
                    .ref.setValue(listHotelModel[position].like + 1)
                listHotelModel[position].like++
                holder.tvLikeQty.text =
                    listHotelModel[position].like.toString()
                PreferenceManager.getDefaultSharedPreferences(view.context).edit().putBoolean(listHotelModel[position].dataSnap, true).apply()
            }else {
                holder.dataSnapshot!!.child(cities).child(cityInfo).child(listHotelModel[position].link).child(hotels)
                    .child(listHotelModel[position].dataSnap).child("like")
                    .ref.setValue(listHotelModel[position].like - 1)
                listHotelModel[position].like--
                holder.tvLikeQty.text =
                    listHotelModel[position].like.toString()
                PreferenceManager.getDefaultSharedPreferences(view.context).edit().putBoolean(listHotelModel[position].dataSnap, true).apply()
            }
        }
    }
    @SuppressLint("DefaultLocale")
    fun searchText(text: String){
        listHotelModel.clear()
        listLikesBool.clear()
        if (text == ""){
            listHotelModel = ArrayList(listHotelModelReserv)
            listLikesBool = ArrayList(listLikesBoolReserv)
        }else{
            for (i in 0 until listHotelModelReserv.size){
                if (listHotelModelReserv[i].name.toLowerCase().contains(text.toLowerCase())){
                    val model = HotelsModel(
                        address = listHotelModelReserv[i].address,
                        lat = listHotelModelReserv[i].lat,
                        lon = listHotelModelReserv[i].lon,
                        like = listHotelModelReserv[i].like,
                        tel = listHotelModelReserv[i].tel,
                        name = listHotelModelReserv[i].name,
                        time = listHotelModelReserv[i].time,
                        timeOut = listHotelModelReserv[i].timeOut,
                        timeIn = listHotelModelReserv[i].timeIn,
                        website = listHotelModelReserv[i].website,
                        wifi = listHotelModelReserv[i].wifi,
                        link = listHotelModelReserv[i].link,
                        dataSnap = listHotelModelReserv[i].dataSnap
                    )
                    listHotelModel.add(model)
                    listLikesBool.add(listLikesBoolReserv[i])
                }
            }
        }
        notifyDataSetChanged()
    }
}