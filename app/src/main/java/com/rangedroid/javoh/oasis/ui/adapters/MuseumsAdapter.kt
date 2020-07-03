@file:Suppress("DEPRECATION")

package com.rangedroid.javoh.oasis.ui.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.location.Location
import android.net.Uri
import android.os.Build
import android.preference.PreferenceManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.appcompat.widget.AppCompatImageView
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.DataSnapshot
import com.ramotion.foldingcell.FoldingCell
import com.rangedroid.javoh.oasis.R
import com.rangedroid.javoh.oasis.data.db.entity.firebase.MuseumsModel
import com.rangedroid.javoh.oasis.utils.UniversalImageLoader
import com.sackcentury.shinebuttonlib.ShineButton
import com.skydoves.elasticviews.ElasticButton
import kotlin.math.roundToInt

@Suppress("DEPRECATION")
class MuseumsAdapter(): RecyclerView.Adapter<MuseumsAdapter.MuseumsViewHolder>() {

    private val cities = "cities"
    private val cityInfo = "city_more_info_en"
    private val museums = "museums"
    private var listMuseumsModel: ArrayList<MuseumsModel> = ArrayList()
    private var listMuseumsModelReserv: ArrayList<MuseumsModel> = ArrayList()
    private var listLikesBool: ArrayList<Boolean> = ArrayList()
    private var listLikesBoolReserv: ArrayList<Boolean> = ArrayList()
    private lateinit var dataSnapshot: DataSnapshot
    private var locationA: Location = Location("point A")
    private var locationB: Location = Location("point B")
    private var isLocal: Boolean = true

    @SuppressLint("CommitPrefEdits")
    constructor(listMuseumsModel: ArrayList<MuseumsModel>, listLikesBool: ArrayList<Boolean>, dataSnapshot: DataSnapshot, lat: Double, lon: Double, isLocal: Boolean) : this(){
        this.listMuseumsModel = ArrayList(listMuseumsModel)
        listMuseumsModelReserv = ArrayList(listMuseumsModel)
        this.listLikesBool = ArrayList(listLikesBool)
        listLikesBoolReserv = ArrayList(listLikesBool)
        this.dataSnapshot = dataSnapshot
        locationA.latitude = lat
        locationA.longitude = lon
        this.isLocal = isLocal
    }


    class MuseumsViewHolder(view: View): RecyclerView.ViewHolder(view){
        var tvMusName: TextView = view.findViewById(R.id.tv_mus_name)
        var tvMusAddress: TextView = view.findViewById(R.id.tv_mus_address)
        var tvDistance: TextView = view.findViewById(R.id.tv_distance_mus)
        var fc: FoldingCell = view.findViewById(R.id.folding_mus_cell)

        var likeBtn: ShineButton = view.findViewById(R.id.like_btn_mus)
        var tvLikeQty: TextView = view.findViewById(R.id.tv_like_qty_mus)
        var tvNameCell: TextView = view.findViewById(R.id.tv_mus_name_cell)
        var tvTimeOpen: TextView = view.findViewById(R.id.tv_mus_time_open)
        var tvTimeClose: TextView = view.findViewById(R.id.tv_mus_time_close)
        var tvPrice: TextView = view.findViewById(R.id.tv_mus_price)
        var btnOpenMap: ElasticButton = view.findViewById(R.id.btn_open_in_map)
        var imageView: AppCompatImageView = view.findViewById(R.id.img_museums)
        var progressBar: ProgressBar = view.findViewById(R.id.progress_bar_mus)
        var mView = view
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MuseumsViewHolder {
        return MuseumsViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_list_museums,
                parent,
                false)
        )
    }

    override fun getItemCount(): Int {
        return listMuseumsModel.size
    }

    @RequiresApi(Build.VERSION_CODES.M)
    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: MuseumsViewHolder, position: Int) {
        locationB.latitude = listMuseumsModel[position].lat
        locationB.longitude = listMuseumsModel[position].lon
        val distance: Float = locationA.distanceTo(locationB)
        if (isLocal) {
            holder.tvMusName.text = listMuseumsModel[position].name_ru
            holder.tvNameCell.text = listMuseumsModel[position].name_ru
        }else{
            holder.tvMusName.text = listMuseumsModel[position].name_en
            holder.tvNameCell.text = listMuseumsModel[position].name_en
        }
        holder.tvMusAddress.text = listMuseumsModel[position].address
        if (distance > 1100){
            holder.tvDistance.text = (distance / 1000).roundToInt().toString() + " " + holder.mView.context.getString(R.string.text_from_km)
        }else{
            holder.tvDistance.text = distance.roundToInt().toString() + " " + holder.mView.context.getString(R.string.text_from_m)
        }
        holder.tvLikeQty.text = listMuseumsModel[position].like.toString()
        holder.likeBtn.isChecked = listLikesBool[position]
        if (listMuseumsModel[position].timeOpen != "no"){
            holder.tvTimeOpen.text = listMuseumsModel[position].timeOpen
        }else{
            holder.tvTimeOpen.setTextColor(holder.mView.context.getColor(R.color.colorHomeLayoutFontNight))
            holder.tvTimeOpen.text = holder.mView.context.getText(R.string.text_no_info)
        }
        if (listMuseumsModel[position].timeClose != "no"){
            holder.tvTimeClose.text = listMuseumsModel[position].timeClose
        }else{
            holder.tvTimeClose.setTextColor(holder.mView.context.getColor(R.color.colorHomeLayoutFontNight))
            holder.tvTimeClose.text = holder.mView.context.getText(R.string.text_no_info)
        }
        if (listMuseumsModel[position].price != "no"){
            holder.tvPrice.text = "${listMuseumsModel[position].price} ${holder.mView.context.getString(R.string.text_sum)}"
        }else{
            holder.tvPrice.setTextColor(holder.mView.context.getColor(R.color.colorHomeLayoutFontNight))
            holder.tvPrice.text = holder.mView.context.getText(R.string.text_no_info)
        }

        if (listMuseumsModel[position].photo != "no"){
            UniversalImageLoader.setImage(listMuseumsModel[position].photo, holder.imageView, holder.progressBar)
        }else{
            holder.progressBar.visibility = View.GONE
        }

        holder.fc
            .setOnClickListener {
                holder.fc.toggle(false)
            }

        holder.btnOpenMap.setOnClickListener {
            it.context.startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("http://maps.google.com/maps?saddr=${locationA.latitude},${locationA.longitude}&daddr=${listMuseumsModel[position].lat},${listMuseumsModel[position].lon}")))
        }

        holder.likeBtn.setOnCheckStateChangeListener { view, checked ->
            if (checked){
                dataSnapshot.child(cities).child(cityInfo).child(listMuseumsModel[position].link).child(museums)
                    .child(listMuseumsModel[position].dataSnap).child("like")
                    .ref.setValue(listMuseumsModel[position].like + 1)
                listMuseumsModel[position].like++
                holder.tvLikeQty.text =
                    listMuseumsModel[position].like.toString()
                PreferenceManager.getDefaultSharedPreferences(view.context).edit().putBoolean(listMuseumsModel[position].dataSnap, true).apply()
            }else {
                dataSnapshot.child(cities).child(cityInfo).child(listMuseumsModel[position].link).child(museums)
                    .child(listMuseumsModel[position].dataSnap).child("like")
                    .ref.setValue(listMuseumsModel[position].like - 1)
                listMuseumsModel[position].like--
                holder.tvLikeQty.text =
                    listMuseumsModel[position].like.toString()
                PreferenceManager.getDefaultSharedPreferences(view.context).edit().putBoolean(listMuseumsModel[position].dataSnap, true).apply()
            }
        }
    }

    @SuppressLint("DefaultLocale")
    fun searchText(text: String){
        listMuseumsModel.clear()
        listLikesBool.clear()
        if (text == ""){
            listMuseumsModel = ArrayList(listMuseumsModelReserv)
            listLikesBool = ArrayList(listLikesBoolReserv)
        }else{
            for (i in 0 until listMuseumsModelReserv.size){
                if (listMuseumsModelReserv[i].name_en.toLowerCase().contains(text.toLowerCase()) || listMuseumsModelReserv[i].name_ru.toLowerCase().contains(text.toLowerCase())){
                    val model = MuseumsModel(
                        address = listMuseumsModelReserv[i].address,
                        lat = listMuseumsModelReserv[i].lat,
                        lon = listMuseumsModelReserv[i].lon,
                        like = listMuseumsModelReserv[i].like,
                        name_en = listMuseumsModelReserv[i].name_en,
                        name_ru = listMuseumsModelReserv[i].name_ru,
                        timeOpen = listMuseumsModelReserv[i].timeOpen,
                        timeClose = listMuseumsModelReserv[i].timeClose,
                        price = listMuseumsModelReserv[i].price,
                        photo = listMuseumsModelReserv[i].photo,
                        link = listMuseumsModelReserv[i].link,
                        dataSnap = listMuseumsModelReserv[i].dataSnap
                    )
                    listMuseumsModel.add(model)
                    listLikesBool.add(listLikesBoolReserv[i])
                }
            }
        }
        notifyDataSetChanged()
    }

}