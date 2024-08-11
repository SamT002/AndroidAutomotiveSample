package com.sam.testapplication.appHost.screen

import android.content.Intent
import androidx.car.app.CarContext
import androidx.car.app.Screen
import androidx.car.app.model.Action
import androidx.car.app.model.CarIcon
import androidx.car.app.model.MessageTemplate
import androidx.car.app.model.Pane
import androidx.car.app.model.PaneTemplate
import androidx.car.app.model.Row
import androidx.car.app.model.Template
import androidx.core.graphics.drawable.IconCompat
import com.sam.testapplication.R
import com.sam.testapplication.appHost.repository.PlacesRepository

class DetailScreen(carContext:CarContext, private val placeId:Int) : Screen(carContext) {

    private val placesRepository = PlacesRepository()

    override fun onGetTemplate(): Template {
        val place = placesRepository.getPlaceById(placeId) ?: return MessageTemplate.Builder("Place not found")
            .setHeaderAction(Action.BACK)
            .build()

        val navigateAction = Action.Builder()
            .setTitle("Navigate")
            .setIcon(CarIcon.Builder(IconCompat.createWithResource(carContext, R.drawable.ic_navigation_24)).build())
            .setOnClickListener {
                carContext.startCarApp(place.toIntent(CarContext.ACTION_NAVIGATE))
            }
            .build()

        return PaneTemplate.Builder(
            Pane.Builder()
                .addAction(navigateAction)
                .addRow(
                    Row.Builder()
                        .setTitle("Coordinates")
                        .addText("${place.latitude}, ${place.longitude}")
                        .build()
                ).addRow(
                    Row.Builder()
                        .setTitle("Description")
                        .addText("Description text")
                        .build()
                ).build()
        )
            .setTitle(place.name)
            .setHeaderAction(Action.BACK)
            .build()
    }


}