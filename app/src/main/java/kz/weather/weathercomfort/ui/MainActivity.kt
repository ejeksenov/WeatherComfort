package kz.weather.weathercomfort.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kz.weather.weathercomfort.R
import kz.weather.weathercomfort.ui.base.BaseActivity

class MainActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}
