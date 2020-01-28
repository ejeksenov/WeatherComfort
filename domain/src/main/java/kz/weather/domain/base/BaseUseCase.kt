package kz.weather.domain.base

import kz.weather.domain.model.Result

interface BaseUseCase<T : Any, R: Any> {
    suspend operator fun invoke(param: T): Result<R>
}