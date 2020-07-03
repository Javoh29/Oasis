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
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.ramotion.foldingcell.FoldingCell
import com.rangedroid.javoh.oasis.R
import com.rangedroid.javoh.oasis.data.db.entity.firebase.MarketsModel
import com.rangedroid.javoh.oasis.utils.UniversalImageLoader
import com.sackcentury.shinebuttonlib.ShineButton
import com.skydoves.elasticviews.ElasticButton
import kotlin.math.roundToInt

@Suppress("DEPRECATION")
class MarketsAdapter(): RecyclerView.Adapter<MarketsAdapter.MarketsViewHolder>() {

    private val cities = "cities"
    private val cityInfo = "city_more_info_en"
    private val markets = "markets"
    private var listMarketsModel: ArrayList<MarketsModel> = ArrayList()
    private var listMarketsModelReserv: ArrayList<MarketsModel> = ArrayList()
    private var listLikesBool: ArrayList<Boolean> = ArrayList()
    private var listLikesBoolReserv: ArrayList<Boolean> = ArrayList()
    private var locationA: Location = Location("point A")
    private var locationB: Location = Location("point B")
    private var isLocal: Boolean = true

    @SuppressLint("CommitPrefEdits")
    constructor(listMarketsModel: List<MarketsModel>, listLikesBool: ArrayList<Boolean>, lat: Double, lon: Double, isLocal: Boolean) : this(){
        this.listMarketsModel = ArrayList(listMarketsModel)
        listMarketsModelReserv = ArrayList(listMarketsModel)
        this.listLikesBool = ArrayList(listLikesBool)
        listLikesBoolReserv = ArrayList(listLikesBool)
        locationA.latitude = lat
        locationA.longitude = lon
        this.isLocal = isLocal
    }


    class MarketsViewHolder(view: View): RecyclerView.ViewHolder(view){
        var tvMusName: TextView = view.findViewById(R.id.tv_mar_name)
        var tvMusAddress: TextView = view.findViewById(R.id.tv_mar_address)
        var tvDistance: TextView = view.findViewById(R.id.tv_distance_mar)
        var fc: FoldingCell = view.findViewById(R.id.folding_mar_cell)

        var likeBtn: ShineButton = view.findViewById(R.id.like_btn_mar)
        var tvLikeQty: TextView = view.findViewById(R.id.tv_like_qty_mar)
        var tvNameCell: TextView = view.findViewById(R.id.tv_mar_name_cell)
        var tvTimeOpen: TextView = view.findViewById(R.id.tv_mar_time_open)
        var tvTimeClose: TextView = view.findViewById(R.id.tv_mar_time_close)
        var btnOpenMap: ElasticButton = view.findViewById(R.id.btn_open_in_map)
        var imageView: AppCompatImageView = view.findViewById(R.id.img_market)
        var progressBar: ProgressBar = view.findViewById(R.id.progress_bar_mar)
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

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MarketsViewHolder {
        return MarketsViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_list_markets,
                parent,
                false)
        )
    }

    override fun getItemCount(): Int {
        return listMarketsModel.size
    }

    @RequiresApi(Build.VERSION_CODES.M)
    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: MarketsViewHolder, position: Int) {
        locationB.latitude = listMarketsModel[position].lat
        locationB.longitude = listMarketsModel[position].lon
        val distance: Float = locationA.distanceTo(locationB)
        if (isLocal) {
            holder.tvMusName.text = listMarketsModel[position].name_en
            holder.tvNameCell.text = listMarketsModel[position].name_en
        }else{
            holder.tvMusName.text = listMarketsModel[position].name_ru
            holder.tvNameCell.text = listMarketsModel[position].name_ru
        }
        holder.tvMusAddress.text = listMarketsModel[position].address
        if (distance > 1100){
            holder.tvDistance.text = (distance / 1000).roundToInt().toString() + " " + holder.mView.context.getString(R.string.text_from_km)
        }else{
            holder.tvDistance.text = distance.roundToInt().toString() + " " + holder.mView.context.getString(R.string.text_from_m)
        }
        holder.tvLikeQty.text = listMarketsModel[position].like.toString()
        holder.likeBtn.isChecked = listLikesBool[position]
        if (listMarketsModel[position].timeOpen != "no"){
            holder.tvTimeOpen.text = listMarketsModel[position].timeOpen
        }else{
            holder.tvTimeOpen.text = holder.mView.context.getText(R.string.text_no_info)
        }
        if (listMarketsModel[position].timeClose != "no"){
            holder.tvTimeClose.text = listMarketsModel[position].timeClose
        }else{
            holder.tvTimeClose.text = holder.mView.context.getText(R.string.text_no_info)
        }

        if (listMarketsModel[position].photo != "no"){
            UniversalImageLoader.setImage(listMarketsModel[position].photo, holder.imageView, holder.progressBar)
        }else{
            holder.progressBar.visibility = View.GONE
        }

        holder.fc
            .setOnClickListener {
                holder.fc.toggle(false)
            }

        holder.btnOpenMap.setOnClickListener {
            it.context.startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("http://maps.google.com/maps?saddr=${locationA.latitude},${locationA.longitude}&daddr=${listMarketsModel[position].lat},${listMarketsModel[position].lon}")))
        }

        holder.likeBtn.setOnCheckStateChangeListener { view, checked ->
            if (checked){
                holder.dataSnapshot!!.child(cities).child(cityInfo).child(listMarketsModel[position].link).child(markets)
                    .child(listMarketsModel[position].dataSnap).child("like")
                    .ref.setValue(listMarketsModel[position].like + 1)
                listMarketsModel[position].like++
                holder.tvLikeQty.text =
                    listMarketsModel[position].like.toString()
                PreferenceManager.getDefaultSharedPreferences(view.context).edit().putBoolean(listMarketsModel[position].dataSnap, true).apply()
            }else {
                holder.dataSnapshot!!.child(cities).child(cityInfo).child(listMarketsModel[position].link).child(markets)
                    .child(listMarketsModel[position].dataSnap).child("like")
                    .ref.setValue(listMarketsModel[position].like - 1)
                listMarketsModel[position].like--
                holder.tvLikeQty.text =
                    listMarketsModel[position].like.toString()
                PreferenceManager.getDefaultSharedPreferences(view.context).edit().putBoolean(listMarketsModel[position].dataSnap, true).apply()
            }
        }
    }

    @SuppressLint("DefaultLocale")
    fun searchText(text: String){
        listMarketsModel.clear()
        listLikesBool.clear()
        if (text == ""){
            listMarketsModel = ArrayList(listMarketsModelReserv)
            listLikesBool = ArrayList(listLikesBoolReserv)
        }else{
            for (i in 0 until listMarketsModelReserv.size){
                if (listMarketsModelReserv[i].name_en.toLowerCase().contains(text.toLowerCase()) || listMarketsModelReserv[i].name_ru.toLowerCase().contains(text.toLowerCase())){
                    val model = MarketsModel(
                        address = listMarketsModelReserv[i].address,
                        lat = listMarketsModelReserv[i].lat,
                        lon = listMarketsModelReserv[i].lon,
                        like = listMarketsModelReserv[i].like,
                        name_en = listMarketsModelReserv[i].name_en,
                        name_ru = listMarketsModelReserv[i].name_ru,
                        timeOpen = listMarketsModelReserv[i].timeOpen,
                        timeClose = listMarketsModelReserv[i].timeClose,
                        photo = listMarketsModelReserv[i].photo,
                        link = listMarketsModelReserv[i].link,
                        dataSnap = listMarketsModelReserv[i].dataSnap
                    )
                    listMarketsModel.add(model)
                    listLikesBool.add(listLikesBoolReserv[i])
                }
            }
        }
        notifyDataSetChanged()
    }

}