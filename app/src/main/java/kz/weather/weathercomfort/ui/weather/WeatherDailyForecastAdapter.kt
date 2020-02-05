package kz.weather.weathercomfort.ui.weather

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import kz.weather.domain.model.DayForecast
import kz.weather.weathercomfort.R
import kz.weather.weathercomfort.databinding.RowDailyForecastLayoutBinding

class WeatherDailyForecastAdapter: RecyclerView.Adapter<WeatherDailyForecastAdapter.ViewHolder>() {

    lateinit var dayForecastList: MutableList<DayForecast>

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding: RowDailyForecastLayoutBinding = DataBindingUtil.inflate(layoutInflater,  R.layout.row_daily_forecast_layout, parent, false)
        return ViewHolder(binding.root)
    }

    override fun getItemCount(): Int {
        return if (::dayForecastList.isInitialized) dayForecastList.size else 0
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val dayForecast = dayForecastList[position]
        holder.bind(dayForecast)
    }

    fun updateDailyForecast(dayForecastList: List<DayForecast>) {
        this.dayForecastList = dayForecastList.toMutableList()
        notifyDataSetChanged()
    }

    var onItemClick: ((String) -> Unit)? = null

    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {

        private val rowDailyForecastLayout: ViewGroup = itemView.findViewById(R.id.rowDailyForecastLayout)
        private val tvDailyForecastDate: TextView = itemView.findViewById(R.id.tvDailyForecastDate)
        private val tvDailyForecastDayTemp: TextView = itemView.findViewById(R.id.tvDailyForecastDayTemp)
        private val tvDailyForecastNightTemp: TextView = itemView.findViewById(R.id.tvDailyForecastNightTemp)
        private val ivDailyForecastDayIcon: ImageView = itemView.findViewById(R.id.ivDailyForecastDayIcon)
        private val ivDailyForecastNightIcon: ImageView = itemView.findViewById(R.id.ivDailyForecastNightIcon)


        fun bind(dayForecast: DayForecast) {

            tvDailyForecastDate.text = dayForecast.date
            tvDailyForecastDayTemp.text = dayForecast.temperatureMaximum
            tvDailyForecastNightTemp.text = dayForecast.temperatureMinimum
            Picasso.get().load(dayForecast.dayIcon).fit().centerCrop().into(ivDailyForecastDayIcon)
            Picasso.get().load(dayForecast.nightIcon).fit().centerCrop().into(ivDailyForecastNightIcon)

            rowDailyForecastLayout.setOnClickListener {
                onItemClick?.invoke(dayForecast.mobileLink)
            }
        }
    }
}