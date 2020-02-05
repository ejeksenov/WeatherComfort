package kz.weather.weathercomfort.ui.base

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kz.weather.data.common.coroutine.CoroutineContextProvider
import kz.weather.data.common.utils.Connectivity
import kz.weather.weathercomfort.utils.launch
import org.koin.core.KoinComponent
import org.koin.core.inject

abstract class BaseViewModel<T : Any, R: Any, U: Any> : ViewModel(), KoinComponent {

    protected val coroutineContext: CoroutineContextProvider by inject()
    private val connectivity: Connectivity by inject()

    protected val _viewState = MutableLiveData<ViewState<T>>()
    val viewState: LiveData<ViewState<T>>
        get() = _viewState

    protected val _viewData = MutableLiveData<ViewState<R>>()
    val viewData: LiveData<ViewState<R>>
        get() = _viewData

    protected val _viewDataInfo = MutableLiveData<ViewState<U>>()
    val viewDataInfo: LiveData<ViewState<U>>
        get() = _viewDataInfo

    protected fun executeUseCase(action: suspend () -> Unit, noInternetAction: () -> Unit) {
        _viewState.value = Loading()
        _viewData.value = Loading()
        _viewDataInfo.value = Loading()
        if (connectivity.hasNetworkAccess()) {
            launch { action() }
        } else {
            noInternetAction()
        }
    }

    protected fun executeUseCase(action: suspend () -> Unit) {
        _viewState.value = Loading()
        _viewData.value = Loading()
        _viewDataInfo.value = Loading()
        launch { action() }
    }
}