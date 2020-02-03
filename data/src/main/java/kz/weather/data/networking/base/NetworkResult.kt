package kz.weather.data.networking.base

import kz.weather.data.database.DB_ENTRY_ERROR
import kz.weather.data.networking.GENERAL_NETWORK_ERROR
import kz.weather.domain.model.Failure
import kz.weather.domain.model.HttpError
import kz.weather.domain.model.Success
import retrofit2.Response
import java.io.IOException
import kz.weather.domain.model.Result

interface DomainMapper<T : Any> {
  fun mapToDomainModel(): T
}

interface RoomMapper<out T : Any> {
  fun mapToRoomEntity(): T
}

inline fun <T : Any> Response<T>.onSuccess(action: (T) -> Unit): Response<T> {
  if (isSuccessful) body()?.run(action)
  return this
}

inline fun <T : Any> Response<T>.onFailure(action: (HttpError) -> Unit) {
  if (!isSuccessful) errorBody()?.run { action(HttpError(Throwable(message()), code())) }
}

inline fun <T : Any> Response<List<T>>.onSuccessList(action: (List<T>) -> Unit): Response<List<T>> {
  if (isSuccessful) body()?.run(action)
  return this
}

inline fun <T : Any> Response<List<T>>.onFailureList(action: (HttpError) -> Unit) {
  if (!isSuccessful) errorBody()?.run { action(HttpError(Throwable(message()), code())) }
}

/**
 * Use this if you need to cache data after fetching it from the api, or retrieve something from cache
 */

inline fun <T : RoomMapper<R>, R : DomainMapper<U>, U : Any> Response<T>.getData(
    cacheAction: (R) -> Unit,
    fetchFromCacheAction: () -> R): Result<U> {
  try {
    onSuccess {
      val databaseEntity = it.mapToRoomEntity()
      cacheAction(databaseEntity)
      return Success(databaseEntity.mapToDomainModel())
    }
    onFailure {
      val cachedModel = fetchFromCacheAction()
      if (cachedModel != null) Success(cachedModel.mapToDomainModel()) else Failure(HttpError(Throwable(DB_ENTRY_ERROR)))
    }
    return Failure(HttpError(Throwable(GENERAL_NETWORK_ERROR)))
  } catch (e: IOException) {
    return Failure(HttpError(Throwable(GENERAL_NETWORK_ERROR)))
  }
}


inline fun <T : RoomMapper<R>, R : DomainMapper<U>, U : Any> Response<List<T>>.getDataList(
  cacheAction: (List<R>) -> Unit,
  fetchFromCacheAction: () -> List<R>): Result<List<U>> {
  try {
    onSuccessList {
      val databaseEntity = it.map { it1 -> it1.mapToRoomEntity() }
      cacheAction(databaseEntity)
      return Success(databaseEntity.map { it1 -> it1.mapToDomainModel() })
    }
    onFailureList {
      val cachedModel = fetchFromCacheAction()
      if (cachedModel != null) Success(cachedModel.map { it1 -> it1.mapToDomainModel() }) else Failure(HttpError(Throwable(DB_ENTRY_ERROR)))
    }
    return Failure(HttpError(Throwable(GENERAL_NETWORK_ERROR)))
  } catch (e: IOException) {
    return Failure(HttpError(Throwable(GENERAL_NETWORK_ERROR)))
  }
}

/**
 * Use this when communicating only with the api service
 */
fun <T : DomainMapper<R>, R : Any> Response<T>.getData(): Result<R> {
  try {
    onSuccess { return Success(it.mapToDomainModel()) }
    onFailure { return Failure(it) }
    return Failure(HttpError(Throwable(GENERAL_NETWORK_ERROR)))
  } catch (e: IOException) {
    return Failure(HttpError(Throwable(GENERAL_NETWORK_ERROR)))
  }
}
