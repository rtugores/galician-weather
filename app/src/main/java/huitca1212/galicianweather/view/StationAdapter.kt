package huitca1212.galicianweather.view

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import huitca1212.galicianweather.R
import huitca1212.galicianweather.view.util.setImageUrl
import java.io.Serializable

class StationAdapter(
    private val context: Context,
    private val stations: List<Station>,
    private val listener: (Station) -> Unit
) : RecyclerView.Adapter<StationAdapter.StationViewHolder>() {

    init {
        setHasStableIds(true)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StationViewHolder =
        StationViewHolder(LayoutInflater.from(context).inflate(R.layout.item_station, parent, false)).apply {
            itemView.setOnClickListener {
                listener(stations[adapterPosition])
            }
        }

    override fun getItemCount(): Int = stations.size

    override fun getItemId(position: Int): Long = stations[position].code.toLong()

    override fun onBindViewHolder(holder: StationViewHolder, position: Int) {
        holder.bind(stations[position])
    }

    class StationViewHolder(item: View) : RecyclerView.ViewHolder(item) {

        private val itemStationName = item.findViewById<TextView>(R.id.itemStationName)
        private val itemStationImage = item.findViewById<ImageView>(R.id.itemStationImage)

        fun bind(station: Station) {
            itemStationName.text = station.name
            itemStationImage.setImageUrl("${station.imageUrl}${System.currentTimeMillis()}")
        }
    }
}

data class Station(
    val code: String,
    val name: String,
    val imageUrl: String
) : Serializable