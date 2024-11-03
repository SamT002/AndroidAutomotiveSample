package com.design.core.model

data class ScreenState<T>(
    val date:T? = null,
    val isLoading:Boolean = true
)