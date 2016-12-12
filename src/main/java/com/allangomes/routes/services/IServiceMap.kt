package com.allangomes.routes.services

import com.allangomes.routes.utils.Coordinate
import java.util.*

/**
* Created by allangomes on 08/12/16.
*/

interface IServiceMap {
    fun getRoute(vararg stops: Coordinate): List<Coordinate>

    fun closer(origins: List<Coordinate>, vararg destinations: Coordinate): Pair<Coordinate, Coordinate>? {
        val distances = distances(origins, *destinations)
        var closer: Long? = null
        var result: Pair<Coordinate, Coordinate>? = null
        distances.forEach { origin, map ->
            map.forEach { dest, meters ->
                if (closer == null || closer!! > meters) {
                    closer = meters
                    result = Pair(origin, dest)
                }
            }
        }
        return result
    }

    fun distances(origins: List<Coordinate>, vararg destinations: Coordinate): HashMap<Coordinate, HashMap<Coordinate, Long>>
}