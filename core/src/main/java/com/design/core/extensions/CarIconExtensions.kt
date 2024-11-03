package com.design.core.extensions

import android.content.Context
import androidx.annotation.DrawableRes
import androidx.car.app.CarContext
import androidx.car.app.model.CarIcon
import androidx.core.graphics.drawable.IconCompat
import com.design.core.R

fun CarIcon.fromResource(context: Context, @DrawableRes icon:Int): CarIcon {
    return CarIcon.Builder(
        IconCompat.createWithResource(
            context,
            icon
        )
    ).build()
}

fun carIconFromResources(context: Context, @DrawableRes icon:Int): CarIcon {
    return CarIcon.Builder(
        IconCompat.createWithResource(
            context,
            icon
        )
    ).build()
}