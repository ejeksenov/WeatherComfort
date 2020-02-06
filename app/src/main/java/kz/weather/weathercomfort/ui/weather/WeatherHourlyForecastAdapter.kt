package kz.weather.weathercomfort.ui.weather

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import kz.weather.domain.model.Weather12HourlyForecast
import kz.weather.weathercomfort.R
import kz.weather.weathercomfort.databinding.RowHourlyForecastLayoutBinding

class WeatherHourlyForecastAdapter: RecyclerView.Adapter<WeatherHourlyForecastAdapter.ViewHolder>() {

    lateinit var list: MutableList<Weather12HourlyForecast>

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding: RowHourlyForecastLayoutBinding = DataBindingUtil.inflate(layoutInflater,  R.layout.row_hourly_forecast_layout, parent, false)
        return ViewHolder(binding.root)
    }

    override fun getItemCount(): Int {
       return if (::list.isInitialized) list.size else 0
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val weather12HourlyForecast = list[position]
        holder.bind(weather12HourlyForecast)
    }

    fun updateHourlyForecast(list: List<Weather12HourlyForecast>) {
        this.list = list.toMutableList()
        notifyDataSetChanged()
    }

    var onItemClick: ((String) -> Unit)? = null

    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {

        private val rowHourlyForecastLayout: ViewGroup = itemView.findViewById(R.id.rowHourlyForecastLayout)
        private val tvHourlyForecastTime: TextView = itemView.findViewById(R.id.tvHourlyForecastTime)
        private val ivHourlyForecastIcon: ImageView = itemView.findViewById(R.id.ivHourlyForecastIcon)
        private val tvHourlyForecastHumidity: TextView = itemView.findViewById(R.id.tvHourlyForecastHumidity)
        private val tvHourlyForecastTemperature: TextView = itemView.findViewById(R.id.tvHourlyForecastTemperature)



        fun bind(weather12HourlyForecast: Weather12HourlyForecast) {

            tvHourlyForecastHumidity.text = weather12HourlyForecast.relativeHumidity
            tvHourlyForecastTemperature.text = weather12HourlyForecast.temperature
            tvHourlyForecastTime.text = weather12HourlyForecast.dateTime
            Picasso.get().load(weather12HourlyForecast.weatherIcon).fit().centerCrop().error(R.drawable.ic_broken_image_white).into(ivHourlyForecastIcon)

            rowHourlyForecastLayout.setOnClickListener {
                onItemClick?.invoke(weather12HourlyForecast.mobileLink)
            }
        }
    }
}