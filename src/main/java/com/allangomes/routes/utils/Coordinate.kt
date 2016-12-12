package com.allangomes.routes.utils

/**
* Created by allangomes on 12/12/16.
*/

interface Coordinate {
    val lat: Double
    val lng: Double

    operator fun minus(right: Coordinate) = distance(right)

    private fun distance(b: Coordinate): Long {
        val theta = this.lng - b.lng
        var dist = Math.sin(Math.toRadians(this.lat)) * Math.sin(Math.toRadians(b.lat)) + Math.cos(Math.toRadians(this.lat)) * Math.cos(Math.toRadians(b.lat)) * Math.cos(Math.toRadians(theta))
        dist = Math.acos(dist)
        dist = Math.toDegrees(dist)
        dist *= 60.0 * 1.1515 //MILHAS
        dist *= 1.609344 //KM
        return Math.round(dist * 1000)
    }
}

class LatLng(override val lat: Double, override val lng: Double) : Coordinate