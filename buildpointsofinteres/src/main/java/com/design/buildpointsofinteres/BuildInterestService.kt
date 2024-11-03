package com.design.buildpointsofinteres

import android.content.Intent
import android.net.Uri
import androidx.car.app.CarContext
import androidx.car.app.CarToast
import androidx.car.app.Screen
import androidx.car.app.Session
import androidx.car.app.model.Action
import androidx.car.app.model.ActionStrip
import androidx.car.app.model.CarIcon
import androidx.car.app.model.ItemList
import androidx.car.app.model.PlaceListMapTemplate
import androidx.car.app.model.Row
import androidx.car.app.model.Template
import androidx.car.app.validation.HostValidator
import androidx.core.graphics.drawable.IconCompat
import androidx.lifecycle.lifecycleScope
import com.design.core.base.BaseCarAppService
import com.design.core.extensions.addItems
import com.design.core.extensions.carIconFromResources
import com.design.core.extensions.createCarAction
import com.design.core.model.LocationDomain
import com.design.core.model.ScreenState
import com.design.core.repository.LocationRepository
import kotlinx.coroutines.launch
import com.design.core.R as CoreR

class BuildInterestService : BaseCarAppService() {

    override fun createHostValidator(): HostValidator {
        return HostValidator.ALLOW_ALL_HOSTS_VALIDATOR;
    }

    override fun onCreateSession(): Session {
        return BuildInterestSession()
    }





}

class BuildInterestSession() : Session() {

    override fun onCreateScreen(intent: Intent): Screen {
        return NavigationScreen(carContext)
    }

}

class NavigationScreen(private val carContext: CarContext) : Screen(carContext) {

    private val repository = LocationRepository()
    private var screenState = ScreenState<List<LocationDomain>>()

    private val rowList
        get() = mapToRowItem(screenState.date.orEmpty())


    override fun onGetTemplate(): Template {
        val placeListMapTemplate =  PlaceListMapTemplate.Builder()

        if (rowList.isNotEmpty()){
            placeListMapTemplate.setItemList(ItemList.Builder().addItems(rowList).build())
        }

        return placeListMapTemplate
            .setTitle("PlaceListMapTemplate")
            .setLoading(screenState.isLoading)
            .setOnContentRefreshListener {
                //refresh content
                CarToast.makeText(carContext, "Refreshed", CarToast.LENGTH_LONG).show()
                fetchLocations()
            }
            .setActionStrip(
                ActionStrip.Builder()
                    .addAction(
                        Action.Builder()
                            .setTitle("Action")
                            .setIcon(
                                CarIcon.Builder(
                                    IconCompat.createWithResource(
                                        carContext,
                                        R.drawable.ic_launcher_foreground
                                    )
                                ).build()
                            )
                            .setOnClickListener {
                                val intent = Intent(
                                    CarContext.ACTION_NAVIGATE,
                                    Uri.parse("geo:42.841548,74.593995")
                                )
                                carContext.startCarApp(intent)
                            }.build()
                    )
                    .build()
            )
            .build()
    }

    private fun mapToRowItem(locationList: List<LocationDomain>): List<Row> {
        return locationList.map {item ->
            Row.Builder()
                .addText(item.addressName)
                .addAction(createCarAction(item.addressName, carContext, CoreR.drawable.ic_navigation_24){
                    onNavigateClicked(item)
                })
                .setImage(carIconFromResources(carContext, CoreR.drawable.ic_my_location_24))
                .build()
        }
    }

    private fun onNavigateClicked(locationDomain: LocationDomain){
        val intent = Intent(
            CarContext.ACTION_NAVIGATE,
            Uri.parse("geo:${locationDomain.latitude},${locationDomain.longitude}")
        )
        carContext.startCarApp(intent)
    }


    // TODO move to vm (or controller), and invoke invalidate like side effect
    private fun fetchLocations(){
        lifecycleScope.launch {
            screenState = screenState.copy(isLoading = true)
            invalidate()
            val result = repository.fetchLocationList()
            screenState.copy(result, isLoading = false)
            invalidate()
        }
    }

}