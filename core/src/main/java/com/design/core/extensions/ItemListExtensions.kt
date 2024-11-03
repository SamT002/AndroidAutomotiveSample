package com.design.core.extensions

import androidx.car.app.model.ItemList
import androidx.car.app.model.Row

fun ItemList.Builder.addItems(rowList: List<Row>): ItemList.Builder {
    rowList.forEach {
        addItem(it)
    }
    return this
}