package com.allangomes.routes.route

import com.allangomes.routes.event.Event
import com.allangomes.routes.utils.LatLng

/**
* Created by allangomes on 12/12/16.
*/

interface RouteService {
    fun create(route: Route): Route
    fun checkpoint(vehicleId: Int, coordinate: LatLng): List<Event>
}
