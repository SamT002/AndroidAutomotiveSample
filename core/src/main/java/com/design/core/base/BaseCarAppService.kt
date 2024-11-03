package com.design.core.base

import androidx.car.app.CarAppService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel

abstract class BaseCarAppService : CarAppService() {

    protected val serviceScope = CoroutineScope(Dispatchers.Main.immediate + SupervisorJob())

    override fun onDestroy() {
        super.onDestroy()
        serviceScope.cancel()
    }

}