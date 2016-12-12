package com.allangomes.routes.utils

import java.util.*
//import kotlin.properties.ReadOnlyProperty
//import kotlin.properties.ReadWriteProperty
//import kotlin.reflect.KProperty
//import kotlin.reflect.jvm.javaField

/**
* Created by allangomes on 12/12/16.
*/

//class DateOnly<in T>(private val initializer: () -> Date) : ReadWriteProperty<T, Date> {
//
//    private var value: Date? = null
//
//    override fun getValue(thisRef: T, property: KProperty<*>): Date {
//        if (value == null)
//            value = initializer()
//        return value!!
//    }
//
//    override fun setValue(thisRef: T, property: KProperty<*>, value: Date) {
//        this.value = value
//    }
//}


internal fun Date.dateOnly(): Date {
    val cal = Calendar.getInstance()
    cal.time = this
    cal[Calendar.HOUR_OF_DAY] = 0
    cal[Calendar.MINUTE] = 0
    cal[Calendar.SECOND] = 0
    cal[Calendar.MILLISECOND] = 0
    return cal.time
}