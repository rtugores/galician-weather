package huitca1212.galicianweather.view

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import huitca1212.galicianweather.R
import huitca1212.galicianweather.view.util.setImageUrl
import kotlinx.android.synthetic.main.item_station.view.*
import java.io.Serializable

class StationAdapter(
    private val context: Context,
    stations: List<Station>,
    private val listener: (Station) -> Unit
) : RecyclerView.Adapter<StationAdapter.StationViewHolder>() {

    private val stations = mutableListOf<Station>()

    init {
        this.stations.clear()
        this.stations.addAll(stations)
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

    class StationViewHolder(private val item: View) : RecyclerView.ViewHolder(item) {
        fun bind(station: Station) {
            item.itemStationName.text = station.name
            item.itemStationImage.setImageUrl("${station.imageUrl}${System.currentTimeMillis()}")
        }
    }
}

data class Station(
    val code: String,
    val name: String,
    val imageUrl: String
) : Serializable