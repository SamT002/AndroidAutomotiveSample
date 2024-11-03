package com.design.core.repository

import androidx.car.app.model.Row
import com.design.core.model.LocationDomain
import kotlinx.coroutines.delay

class LocationRepository {

    suspend fun fetchLocationList(): List<LocationDomain> {
        delay(3_000)
        return listOf(
            LocationDomain(addressName = "Home", latitude = 42.841548, longitude = 74.593995),
            LocationDomain(addressName = "Work", latitude =  42.866737, longitude = 74.544978),
            LocationDomain(addressName = "Eat", latitude = 42.867639, longitude =  74.655192),
        )
    }

    companion object{

    }

}