package kz.weather.domain.interaction

import kz.weather.domain.model.LocationInfo
import kz.weather.domain.model.Result
import kz.weather.domain.repository.LocationInfoRepository

class GetLocationInfoUseCaseImpl(private val locationInfoRepository: LocationInfoRepository):
    GetLocationInfoUseCase {
    override suspend operator fun invoke(location: String): Result<LocationInfo> = locationInfoRepository.getLocationInfo(location)
}