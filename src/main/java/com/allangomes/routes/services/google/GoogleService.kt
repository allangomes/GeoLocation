package com.allangomes.routes.services.google

import com.allangomes.routes.services.IServiceMap
import com.allangomes.routes.utils.Coordinate
import com.google.maps.*
import com.google.maps.model.DirectionsResult
import com.google.maps.model.DistanceMatrixElementStatus
import com.google.maps.model.TravelMode
import java.util.*

/**
* Created by allangomes on 08/12/16.
*/

class GoogleService (val context: GeoApiContext): IServiceMap {

    fun getPath(directions: DirectionsResult): List<Coordinate> {
        val results: MutableList<Coordinate> = mutableListOf()
        for (path in directions.routes) {
            path.overviewPolyline.decodePath().forEach {
                results.add(it.model())
            }
        }
        return results
    }

    override fun getRoute(vararg stops: Coordinate): List<Coordinate> {
        val points = stops.map(Coordinate::google).toMutableList()
        val first = points.removeAt(0)
        val last = points.removeAt(points.lastIndex)

        val api = DirectionsApi.newRequest(context)
        api.mode(TravelMode.DRIVING)
        api.origin(first)
        api.destination(last)
        api.optimizeWaypoints(true)
        api.waypoints(*points.toTypedArray())

        return getPath(api.await())
        //TODO: Mudar para assincrono
    }

    override fun distances(origins: List<Coordinate>, vararg destinations: Coordinate): HashMap<Coordinate, HashMap<Coordinate, Long>> {
        val api = DistanceMatrixApi.newRequest(context)
        val ori = origins.map(Coordinate::google).toTypedArray()
        api.origins(*ori)
        val dest = destinations.map(Coordinate::google).toTypedArray()
        api.destinations(*dest)
        val distances = api.await()

        val result: HashMap<Coordinate, HashMap<Coordinate, Long>> = hashMapOf()
        distances.rows.forEachIndexed { originIdx, row ->
            val destMap: HashMap<Coordinate, Long> = hashMapOf()
            row.elements.forEachIndexed { destIdx, element ->
                if (element.status == DistanceMatrixElementStatus.OK)
                    destMap[destinations[destIdx]] = element.distance.inMeters
            }
            result[origins[originIdx]] = destMap
        }
        return result
    }
}
