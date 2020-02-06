package kz.weather.weathercomfort.ui

import android.Manifest
import android.content.pm.PackageManager
import android.location.Location
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Looper
import androidx.core.app.ActivityCompat
import com.google.android.gms.location.*
import kz.weather.weathercomfort.R
import kz.weather.weathercomfort.ui.base.BaseActivity
import kz.weather.weathercomfort.ui.weather.WeatherFragment.Companion.locationStr

class MainActivity : BaseActivity() {

    private lateinit var fusedLocationClient: FusedLocationProviderClient

    private lateinit var locationCallback: LocationCallback
    private lateinit var locationRequest: LocationRequest

    private val permissionAccessCoarseLocationApproved = ActivityCompat
        .checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) ==
            PackageManager.PERMISSION_GRANTED

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)

        locationCallback = object : LocationCallback() {
            override fun onLocationResult(locationResult: LocationResult?) {
                locationResult ?: return
                for (location in locationResult.locations) {
                    if (location != null) {
                        locationStr = "${location.latitude},${location.longitude}"
                    }
                }
            }
        }


        if (permissionAccessCoarseLocationApproved) {
            getLocationUpdates()
        } else
            ActivityCompat.requestPermissions(this,arrayOf(Manifest.permission.ACCESS_COARSE_LOCATION), 100)

    }

    private fun getLocationUpdates() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }


}
