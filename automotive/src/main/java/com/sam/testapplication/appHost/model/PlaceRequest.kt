package com.sam.testapplication.appHost.model

import android.content.Intent
import android.net.Uri

data class PlaceRequest(val id:Int, val name: String, val latitude: Double, val longitude: Double) {
    fun toIntent(action: String): Intent {
        return Intent(action).apply {
            val uri = Uri.parse("geo:$latitude,$longitude")
            data = uri
        }
    }
}
