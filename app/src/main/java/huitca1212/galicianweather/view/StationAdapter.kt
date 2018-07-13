package huitca1212.galicianweather.view

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import huitca1212.galicianweather.R
import huitca1212.galicianweather.model.Station
import kotlinx.android.synthetic.main.item_station.view.*

class StationAdapter(
    private val context: Context,
    private val listener: (Station) -> Unit
) :
    RecyclerView.Adapter<StationAdapter.StationViewHolder>() {

    var stationNames = mutableListOf<Station>()
        set(value) {
            field.clear()
            field.addAll(value)
            notifyDataSetChanged()
        }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StationViewHolder =
        StationViewHolder(LayoutInflater.from(context).inflate(R.layout.item_station, parent, false)).apply {
            with(itemView) {
                setOnClickListener {
                    listener(stationNames[adapterPosition])
                }
            }
        }

    override fun getItemCount(): Int = stationNames.size

    override fun onBindViewHolder(holder: StationViewHolder, position: Int) {
        holder.bind(stationNames[position])
    }

    class StationViewHolder(private val item: View) : RecyclerView.ViewHolder(item) {
        fun bind(station: Station) {
            item.itemStationName.text = station.name
            item.itemStationImage.setImageResource(station.imageResId)
        }
    }
}
