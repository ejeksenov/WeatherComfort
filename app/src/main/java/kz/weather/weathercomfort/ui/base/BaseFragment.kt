package kz.weather.weathercomfort.ui.base

import android.view.View
import android.widget.ProgressBar
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment

abstract class BaseFragment : Fragment() {
    open fun showError(@StringRes errorMessage: Int, rootView: View) {
        (activity as BaseActivity).showError(errorMessage, rootView)
    }

    open fun showError(errorMessage: String?, rootView: View) {
        (activity as BaseActivity).showError(errorMessage, rootView)
    }

    open fun showLoading(progressBar: ProgressBar) {
        (activity as BaseActivity).showLoading(progressBar)
    }

    open fun hideLoading(progressBar: ProgressBar) {
        (activity as BaseActivity).hideLoading(progressBar)
    }
}