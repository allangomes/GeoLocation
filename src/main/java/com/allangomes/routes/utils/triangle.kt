package com.allangomes.routes.utils

/**
* Created by allangomes on 12/12/16.
*/

object triangle {
    fun height(a: Long, b: Long, c: Long): Long {
        val base = Math.max(Math.max(a, b), c)
        val p = (a + b + c) / 2
        val area = Math.sqrt((p * (p - a) * (p - b) * (p - c)).toDouble())
        return Math.round(area * 2 / base)
    }
}