package com.sam.testapplication.appHost.repository

import com.sam.testapplication.appHost.model.PlaceRequest

class PlacesRepository() {

    private val placesList = listOf(
        PlaceRequest(0,"First Location", 42.856927, 74.6001211),
        PlaceRequest(1, "Second Location", 42.8641851, 74.601029)
    )

    fun getPlaces(): List<PlaceRequest> {
        return placesList
    }

    fun getPlaceById(id:Int): PlaceRequest? {
        return placesList.find { it.id == id }
    }
}