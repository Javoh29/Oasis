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
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.widget.AppCompatImageView
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.DataSnapshot
import com.ramotion.foldingcell.FoldingCell
import com.rangedroid.javoh.oasis.R
import com.rangedroid.javoh.oasis.data.db.entity.firebase.SightsModel
import com.rangedroid.javoh.oasis.utils.UniversalImageLoader
import com.sackcentury.shinebuttonlib.ShineButton
import com.skydoves.elasticviews.ElasticButton
import kotlin.math.roundToInt

class SightsAdapter(): RecyclerView.Adapter<SightsAdapter.SightsViewHolder>() {

    private val cities = "cities"
    private val cityInfo = "city_more_info_en"
    private val history = "history_places"
    private var listSightModel: ArrayList<SightsModel> = ArrayList()
    private var listSightModelReserv: ArrayList<SightsModel> = ArrayList()
    private var listLikesBool: ArrayList<Boolean> = ArrayList()
    private var listLikesBoolReserv: ArrayList<Boolean> = ArrayList()
    private lateinit var dataSnapshot: DataSnapshot
    private var locationA: Location = Location("point A")
    private var locationB: Location = Location("point B")
    private var isLocal: Boolean = true


    @SuppressLint("CommitPrefEdits")
    constructor(listSightModel: ArrayList<SightsModel>, listLikesBool: ArrayList<Boolean>, dataSnapshot: DataSnapshot, lat: Double, lon: Double, isLocal: Boolean) : this(){
        this.listSightModel = ArrayList(listSightModel)
        listSightModelReserv = ArrayList(listSightModel)
        this.listLikesBool = ArrayList(listLikesBool)
        listLikesBoolReserv = ArrayList(listLikesBool)
        this.dataSnapshot = dataSnapshot
        locationA.latitude = lat
        locationA.longitude = lon
        this.isLocal = isLocal
    }

    class SightsViewHolder(view: View): RecyclerView.ViewHolder(view) {
        var tvSightName: TextView = view.findViewById(R.id.tv_sight_name)
        var tvSightAddress: TextView = view.findViewById(R.id.tv_sight_address)
        var tvDistance: TextView = view.findViewById(R.id.tv_distance_sight)
        var fc: FoldingCell = view.findViewById(R.id.folding_sight_cell)

        var likeBtn: ShineButton = view.findViewById(R.id.like_btn_sight)
        var tvLikeQty: TextView = view.findViewById(R.id.tv_like_qty_sight)
        var tvSightNameCell: TextView = view.findViewById(R.id.tv_sight_name_cell)
        var btnOpenMap: ElasticButton = view.findViewById(R.id.btn_open_in_map)
        var imageView: AppCompatImageView = view.findViewById(R.id.img_sight_view)
        var progressBar: ProgressBar = view.findViewById(R.id.progress_bar_sight)
        var mView = view
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SightsViewHolder {
        return SightsViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_list_sights,
                parent,
                false)
        )
    }

    override fun getItemCount(): Int {
        return listSightModel.size
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: SightsViewHolder, position: Int) {
        locationB.latitude = listSightModel[position].lat
        locationB.longitude = listSightModel[position].lon
        val distance: Float = locationA.distanceTo(locationB)
        if (isLocal) {
            holder.tvSightName.text = listSightModel[position].nameRu
            holder.tvSightNameCell.text = listSightModel[position].nameRu
        }else{
            holder.tvSightName.text = listSightModel[position].nameEn
            holder.tvSightNameCell.text = listSightModel[position].nameEn
        }
        holder.tvSightAddress.text = listSightModel[position].address
        if (distance > 1100){
            holder.tvDistance.text = (distance / 1000).roundToInt().toString() + " " + holder.mView.context.getString(R.string.text_from_km)
        }else{
            holder.tvDistance.text = distance.roundToInt().toString() + " " + holder.mView.context.getString(R.string.text_from_m)
        }
        holder.tvLikeQty.text = listSightModel[position].like.toString()
        holder.likeBtn.isChecked = listLikesBool[position]

        if (listSightModel[position].photo != "no"){
            UniversalImageLoader.setImage(listSightModel[position].photo, holder.imageView, holder.progressBar)
        }else{
            holder.progressBar.visibility = View.GONE
        }

        holder.fc
            .setOnClickListener {
                holder.fc.toggle(false)
            }

        holder.btnOpenMap.setOnClickListener {
            holder.mView.context.startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("http://maps.google.com/maps?saddr=${locationA.latitude},${locationA.longitude}&daddr=${listSightModel[position].lat},${listSightModel[position].lon}")))
        }

        holder.likeBtn.setOnCheckStateChangeListener { view, checked ->
            if (checked){
                dataSnapshot.child(cities).child(cityInfo).child(listSightModel[position].link).child(history)
                    .child(listSightModel[position].dataSnap).child("like")
                    .ref.setValue(listSightModel[position].like + 1)
                listSightModel[position].like++
                holder.tvLikeQty.text =
                    listSightModel[position].like.toString()
                PreferenceManager.getDefaultSharedPreferences(view.context).edit().putBoolean(listSightModel[position].dataSnap, true).apply()
            }else {
                dataSnapshot.child(cities).child(cityInfo).child(listSightModel[position].link).child(history)
                    .child(listSightModel[position].dataSnap).child("like")
                    .ref.setValue(listSightModel[position].like - 1)
                listSightModel[position].like--
                holder.tvLikeQty.text =
                    listSightModel[position].like.toString()
                PreferenceManager.getDefaultSharedPreferences(view.context).edit().putBoolean(listSightModel[position].dataSnap, true).apply()
            }
        }
    }

    @SuppressLint("DefaultLocale")
    fun searchText(text: String){
        listSightModel.clear()
        listLikesBool.clear()
        if (text == ""){
            listSightModel = ArrayList(listSightModelReserv)
            listLikesBool = ArrayList(listLikesBoolReserv)
        }else{
            for (i in 0 until listSightModelReserv.size){
                if (listSightModelReserv[i].nameEn.toLowerCase().contains(text.toLowerCase()) || listSightModelReserv[i].nameRu.toLowerCase().contains(text.toLowerCase())){
                    val model = SightsModel(
                        address = listSightModelReserv[i].address,
                        lat = listSightModelReserv[i].lat,
                        lon = listSightModelReserv[i].lon,
                        like = listSightModelReserv[i].like,
                        nameEn = listSightModelReserv[i].nameEn,
                        nameRu = listSightModelReserv[i].nameRu,
                        photo = listSightModelReserv[i].photo,
                        link = listSightModelReserv[i].link,
                        dataSnap = listSightModelReserv[i].dataSnap
                    )
                    listSightModel.add(model)
                    listLikesBool.add(listLikesBoolReserv[i])
                }
            }
        }
        notifyDataSetChanged()
    }
}