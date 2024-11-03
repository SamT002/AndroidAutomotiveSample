package com.design.core.extensions

import androidx.annotation.DrawableRes
import androidx.car.app.CarContext
import androidx.car.app.model.Action

fun createCarAction(
    title: String,
    carContext: CarContext,
    @DrawableRes icon: Int,
    onClickListener: () -> Unit = {}
): Action {
    return Action.Builder()
        .setTitle(title)
        .setIcon(carIconFromResources(carContext, icon))
        .setOnClickListener(onClickListener)
        .build()
}