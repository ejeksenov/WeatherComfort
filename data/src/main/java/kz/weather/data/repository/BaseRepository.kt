package kz.weather.data.repository

import kotlinx.coroutines.withContext
import kz.weather.data.common.coroutine.CoroutineContextProvider
import kz.weather.data.common.utils.Connectivity
import kz.weather.data.database.DB_ENTRY_ERROR
import kz.weather.data.networking.GENERAL_NETWORK_ERROR
import kz.weather.data.networking.base.DomainMapper
import kz.weather.domain.model.Failure
import kz.weather.domain.model.HttpError
import kz.weather.domain.model.Success
import kz.weather.domain.model.Result
import org.koin.core.KoinComponent
import org.koin.core.inject

abstract class BaseRepository<T : Any, R : DomainMapper<T>> : KoinComponent {
  private val connectivity: Connectivity by inject()
  private val contextProvider: CoroutineContextProvider by inject()
  
  /**
   * Use this if you need to cache data after fetching it from the api, or retrieve something from cache
   */
  protected suspend fun fetchData(
      apiDataProvider: suspend () -> Result<T>,
      dbDataProvider: suspend () -> R
  ): Result<T> {
    return if (connectivity.hasNetworkAccess()) {
      withContext(contextProvider.io) {
        apiDataProvider()
      }
    } else {
      withContext(contextProvider.io) {
        val dbResult = dbDataProvider()
        if (dbResult != null) Success(dbResult.mapToDomainModel()) else Failure(HttpError(Throwable(DB_ENTRY_ERROR)))
      }
    }
  }
  
  /**
   * Use this when communicating only with the api service
   */
  protected suspend fun fetchData(dataProvider: () -> Result<T>): Result<T> {
    return if (connectivity.hasNetworkAccess()) {
      withContext(contextProvider.io) {
        dataProvider()
      }
    } else {
      Failure(HttpError(Throwable(GENERAL_NETWORK_ERROR)))
    }
  }
}