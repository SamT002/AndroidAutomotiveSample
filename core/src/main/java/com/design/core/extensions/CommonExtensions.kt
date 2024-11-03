package com.design.core.extensions

import androidx.car.app.CarContext
import androidx.car.app.CarToast

fun CarContext.showToast(message:String){
    CarToast.makeText(this, message, CarToast.LENGTH_LONG).show()
}