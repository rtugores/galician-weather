package com.galicianweather.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.galicianweather.R
import com.galicianweather.databinding.ItemStationBinding
import com.galicianweather.view.model.StationViewModel
import com.galicianweather.view.util.setImageUrl

class StationAdapter(private val listener: (StationViewModel) -> Unit) :
    RecyclerView.Adapter<StationAdapter.StationViewHolder>() {

    var stations: MutableList<StationViewModel> = mutableListOf()

    init {
        setHasStableIds(true)
    }

    fun updateStations(stationList: List<StationViewModel>) {
        stations.clear()
        stations.addAll(stationList)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StationViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemStationBinding.inflate(inflater, parent, false)

        return StationViewHolder(binding).apply {
            itemView.setOnClickListener { listener(stations[adapterPosition]) }
        }
    }

    override fun getItemCount(): Int = stations.size

    override fun getItemId(position: Int): Long = stations[position].code.toLong()

    override fun onBindViewHolder(holder: StationViewHolder, position: Int) {
        holder.bind(stations[position])
    }

    class StationViewHolder(private val binding: ItemStationBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(station: StationViewModel) {
            with(binding) {
                itemStationCity.text = station.city
                itemStationPlace.text = station.place
                itemStationImage.setImageUrl(url = station.imageUrl, errorRes = R.drawable.ic_placeholder)
            }
        }
    }
}
