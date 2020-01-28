package kz.weather.weathercomfort.ui.base

import android.view.View
import android.widget.ProgressBar
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import kz.weather.weathercomfort.utils.EMPTY_STRING
import kz.weather.weathercomfort.utils.gone
import kz.weather.weathercomfort.utils.snackbar
import kz.weather.weathercomfort.utils.visible

abstract class BaseActivity: AppCompatActivity() {

    fun showError(@StringRes errorMessage: Int, rootView: View) = snackbar(errorMessage, rootView)

    fun showError(errorMessage: String?, rootView: View) = snackbar(errorMessage ?: EMPTY_STRING, rootView)

    fun showLoading(progressBar: ProgressBar) = progressBar.visible()

    fun hideLoading(progressBar: ProgressBar) = progressBar.gone()
}