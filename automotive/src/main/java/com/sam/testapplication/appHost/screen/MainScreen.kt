package com.sam.testapplication.appHost.screen

import android.text.Spannable
import android.text.SpannableString
import androidx.car.app.CarContext
import androidx.car.app.Screen
import androidx.car.app.model.CarLocation
import androidx.car.app.model.Distance
import androidx.car.app.model.DistanceSpan
import androidx.car.app.model.ItemList
import androidx.car.app.model.Metadata
import androidx.car.app.model.Place
import androidx.car.app.model.PlaceListMapTemplate
import androidx.car.app.model.PlaceMarker
import androidx.car.app.model.Row
import androidx.car.app.model.Template
import com.sam.testapplication.appHost.repository.PlacesRepository

class MainScreen(carContext: CarContext) : Screen(carContext) {

    private val placesRepository = PlacesRepository()


    override fun onGetTemplate(): Template {
        val placesItemBuilder = ItemList.Builder()
            .setNoItemsMessage("No places to show")

        placesRepository.getPlaces()
            .forEach {
                placesItemBuilder.addItem(
                    Row.Builder()
                        .setTitle(it.name)
                        .addText(SpannableString(" ").apply {
                            setSpan(
                                DistanceSpan.create(
                                    Distance.create(Math.random() * 100, Distance.UNIT_KILOMETERS)
                                ), 0, 1, Spannable.SPAN_INCLUSIVE_INCLUSIVE
                            )
                        })
                        .setOnClickListener { screenManager.push(DetailScreen(carContext, it.id)) }
                        .setMetadata(
                            Metadata.Builder()
                                .setPlace(
                                    Place.Builder(CarLocation.create(it.latitude, it.longitude))
                                        .setMarker(PlaceMarker.Builder().build())
                                        .build()
                                )
                                .build()
                        ).build()
                )
            }
        return PlaceListMapTemplate.Builder()
            .setTitle("Places")
            .setItemList(placesItemBuilder.build())
            .build()
    }

}