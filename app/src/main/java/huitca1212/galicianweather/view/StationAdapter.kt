package huitca1212.galicianweather.view

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import huitca1212.galicianweather.R
import huitca1212.galicianweather.view.model.StationViewModel
import huitca1212.galicianweather.view.util.setImageUrl

class StationAdapter(
    private val context: Context,
    private val listener: (StationViewModel) -> Unit
) : RecyclerView.Adapter<StationAdapter.StationViewHolder>() {

    var stations: MutableList<StationViewModel> = mutableListOf()
        set(value) {
            field.clear()
            field.addAll(value)
            notifyDataSetChanged()
        }

    init {
        setHasStableIds(true)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StationViewHolder {
        val item = LayoutInflater.from(context).inflate(R.layout.item_station, parent, false)
        return StationViewHolder(item).apply {
            itemView.setOnClickListener { listener(stations[adapterPosition]) }
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

        fun bind(station: StationViewModel) {
            itemStationName.text = station.name
            itemStationImage.setImageUrl(station.imageUrl)
        }
    }
}
