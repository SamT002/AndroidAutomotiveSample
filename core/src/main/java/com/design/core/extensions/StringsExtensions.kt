package com.design.core.extensions

import android.text.SpannableString
import android.text.Spanned
import androidx.car.app.model.Distance
import androidx.car.app.model.DistanceSpan
import androidx.car.app.model.Row

fun String.createSpanStringKm(distance: Double) {
    SpannableString(this).setSpan(
        DistanceSpan.create(Distance.create(distance, Distance.UNIT_KILOMETERS)),
        0,
        this.length,
        Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
    )
}