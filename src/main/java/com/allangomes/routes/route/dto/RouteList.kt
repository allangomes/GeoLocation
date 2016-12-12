package com.allangomes.routes.route.dto

import com.allangomes.routes.route.Route
import com.allangomes.routes.stop.Stop
import java.util.*

/**
* Created by allangomes on 12/12/16.
*/

class RouteList(route: Route) {
    val id: String = route.id
    val name: String = route.name
    val vehicleId: Int = route.vehicleId
    val routeDate: Date = route.routeDate
    val stops: List<Stop> = route.stops
}
