package kz.weather.domain.interaction

import kz.weather.domain.base.BaseUseCase
import kz.weather.domain.model.LocationInfo
import kz.weather.domain.model.Result

interface GetLocationInfoUseCase: BaseUseCase<String, LocationInfo> {
    override suspend operator fun invoke(location: String): Result<LocationInfo>
}