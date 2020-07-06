package com.rangedroid.javoh.oasis.ui.adapters
import android.content.Intent
import android.net.Uri
import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.widget.AppCompatImageView
import androidx.recyclerview.widget.RecyclerView
import com.rangedroid.javoh.oasis.R
import com.rangedroid.javoh.oasis.data.db.entity.firebase.ToursModel
import com.rangedroid.javoh.oasis.utils.UniversalImageLoader

@Suppress("DEPRECATION")
class ToursAdapter(private val listToursModel: List<ToursModel>): RecyclerView.Adapter<ToursAdapter.ToursViewHolder>(){

    class ToursViewHolder(view: View): RecyclerView.ViewHolder(view){
        var imgTour: AppCompatImageView = view.findViewById(R.id.img_tour)
        var tvTitle: TextView = view.findViewById(R.id.tv_title)
        var tvDays: TextView = view.findViewById(R.id.tv_day)
        var tvPrice: TextView = view.findViewById(R.id.tv_price)
        var tvCities: TextView = view.findViewById(R.id.tv_cities)
        var tvSeason: TextView = view.findViewById(R.id.tv_season)
        var tvText: TextView = view.findViewById(R.id.tv_text)
        var btnRead: Button = view.findViewById(R.id.btn_read_more)
        var mView = view
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ToursViewHolder {
        return ToursViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_tours_container,
                parent,
                false)
        )
    }

    override fun getItemCount(): Int {
        return listToursModel.size
    }

    override fun onBindViewHolder(holder: ToursViewHolder, position: Int) {
        UniversalImageLoader.setImage(listToursModel[position].photo, holder.imgTour, null)
        holder.tvTitle.text = listToursModel[position].title
        holder.tvDays.text = listToursModel[position].days
        holder.tvPrice.text = listToursModel[position].price
        holder.tvCities.text = Html.fromHtml("<b>${holder.mView.context.getString(R.string.text_cities)}</b> ${listToursModel[position].cities}")
        holder.tvSeason.text = Html.fromHtml("<b>${holder.mView.context.getString(R.string.text_season)}</b> ${listToursModel[position].season}")
        holder.tvText.text = listToursModel[position].text

        holder.btnRead.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW)
            intent.data = Uri.parse(listToursModel[position].url)
            it.context.startActivity(intent)
        }
    }
}