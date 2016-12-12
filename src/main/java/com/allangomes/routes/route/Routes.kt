package com.allangomes.routes.route

import java.util.*

/**
* Created by allangomes on 12/12/16.
*/

interface Routes {
    fun all(): List<Route>
    fun findOne(id: String): Route
    fun findByVehicleAndDate(vehicleId: Int, date: Date): Route?
}
