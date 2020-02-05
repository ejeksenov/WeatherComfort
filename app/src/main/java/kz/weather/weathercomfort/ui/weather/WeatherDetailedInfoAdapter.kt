package kz.weather.weathercomfort.ui.weather

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import kz.weather.weathercomfort.R
import kz.weather.weathercomfort.databinding.RowWeatherDetailedInfoLayoutBinding

class WeatherDetailedInfoAdapter: RecyclerView.Adapter<WeatherDetailedInfoAdapter.ViewHolder>() {

    private val listName: MutableList<String> = ArrayList()
    private val listValue: MutableList<String> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding: RowWeatherDetailedInfoLayoutBinding = DataBindingUtil.inflate(layoutInflater,  R.layout.row_weather_detailed_info_layout, parent, false)
        return ViewHolder(binding.root)
    }

    override fun getItemCount(): Int {
        return listName.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val name = listName[position]
        val value = listValue[position]
        holder.bind(name, value)
    }

    fun updateData(hashMap: HashMap<String, String>) {
        listValue.clear()
        listName.clear()
        for (key in hashMap.keys) {
            val value = hashMap[key]
            listName.add(key)
            listValue.add(value!!)
        }
        notifyDataSetChanged()
    }

    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        private val rowWeatherDetailedInfoLayout: ViewGroup = itemView.findViewById(R.id.rowWeatherDetailedInfoLayout)
        private val tvWeatherDetailedInfoName: TextView = itemView.findViewById(R.id.tvWeatherDetailedInfoName)
        private val tvWeatherDetailedInfoValue: TextView = itemView.findViewById(R.id.tvWeatherDetailedInfoValue)

        fun bind(dataName: String, dataValue: String) {
            tvWeatherDetailedInfoName.text = dataName
            tvWeatherDetailedInfoValue.text = dataValue
        }

    }
}