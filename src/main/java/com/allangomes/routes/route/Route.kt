package com.allangomes.routes.route

import com.allangomes.routes.stop.Stop
import com.allangomes.routes.utils.LatLng
import org.bson.types.ObjectId
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import java.util.*

/**
* Created by allangomes on 12/12/16.
*/

@Document(collection = "Route")
class Route(val name: String, val vehicleId: Int) {
    @Id
    val id: String = ObjectId().toString()
    var routeDate: Date = Date()
    var stops: List<Stop> = mutableListOf()
    var path: List<LatLng> = mutableListOf()
}
