package com.design.core.extensions

import androidx.annotation.DrawableRes
import androidx.car.app.CarContext
import androidx.car.app.model.Action
import androidx.car.app.model.Row

fun Row.Builder.setAction(
    title: String,
    carContext: CarContext,
    @DrawableRes icon: Int,
    onClickListener: () -> Unit = {}
): Row.Builder {
    return addAction(createCarAction(title, carContext,icon,onClickListener))
}