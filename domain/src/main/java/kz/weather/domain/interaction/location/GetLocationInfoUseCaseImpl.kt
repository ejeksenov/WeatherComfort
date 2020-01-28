package kz.weather.domain.interaction.location

import kz.weather.domain.interaction.location.GetLocationInfoUseCase
import kz.weather.domain.model.LocationInfo
import kz.weather.domain.model.Result
import kz.weather.domain.repository.LocationInfoRepository

class GetLocationInfoUseCaseImpl(private val locationInfoRepository: LocationInfoRepository):
    GetLocationInfoUseCase {
    override suspend operator fun invoke(location: String): Result<LocationInfo> = locationInfoRepository.getLocationInfo(location)
}