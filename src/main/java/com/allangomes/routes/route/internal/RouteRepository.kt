package com.allangomes.routes.route.internal

import com.allangomes.routes.route.Route
import com.allangomes.routes.route.Routes
import com.allangomes.routes.utils.eq
import com.allangomes.routes.utils.exc
import org.springframework.data.mongodb.core.MongoOperations
import org.springframework.data.mongodb.core.query.Query
import org.springframework.stereotype.Repository
import java.util.*

/**
* Created by allangomes on 12/12/16.
*/

interface RouteRepository : Routes {
    fun insert(route: Route): Route
}

@Repository
internal open class DefaultRouteRepository(val mongo: MongoOperations): RouteRepository {

    override fun findOne(id: String): Route {
        return mongo.findById(id, Route::class.java)
    }

    override fun insert(route: Route): Route {
        mongo.insert(route)
        return route
    }

    override fun all(): List<Route> {
        val query = Query().exc("path")
        return mongo.find(query, Route::class.java)
    }

    override fun findByVehicleAndDate(vehicleId: Int, date: Date): Route? {
        val query = Query()
        query.eq("vehicleId", vehicleId).eq("routeDate", date)
        val result = mongo.find(query, Route::class.java)
        return if (result.size === 1) result[0] else null
    }
}