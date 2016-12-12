package com.allangomes.routes.route.internal

import com.allangomes.routes.event.Event
import com.allangomes.routes.event.Handler
import com.allangomes.routes.event.internal.EventRepository
import com.allangomes.routes.route.Route
import com.allangomes.routes.route.RouteService
import com.allangomes.routes.services.IServiceMap
import com.allangomes.routes.stop.Stop
import com.allangomes.routes.utils.LatLng
import com.allangomes.routes.utils.dateOnly
import com.allangomes.routes.utils.triangle
import org.springframework.stereotype.Service
import java.util.*

/**
* Created by allangomes on 12/12/16.
*/

@Service
internal class RouteService(val map: IServiceMap, val repo: RouteRepository, val handler: Handler) : RouteService {


    override fun create(route: Route): Route {
        route.path = map.getRoute(*route.stops.toTypedArray()).filterIsInstance<LatLng>()
        return repo.insert(route)
    }

    private fun checkRunAway(coordinate: LatLng, path: List<LatLng>): Long? {
        var min: Long? = null
        path.reduce { point1, point2 ->
            val a = coordinate - point1
            val b = coordinate - point2
            val c = point1 - point2
            val distance = if (c > a && c > b) triangle.height(a, b, c) else Math.min(a, b)
            if (min == null || min!! > distance)
                min = distance
            return@reduce point2
        }
        return min
    }

    override fun checkpoint(vehicleId: Int, coordinate: LatLng): List<Event> {
        val result: MutableList<Event> = mutableListOf()
        repo.findByVehicleAndDate(vehicleId, Date().dateOnly())?.let {
            checkRunAway(coordinate, it.path)?.let {
                if (it > 500)
                    result.add(Event(vehicleId, "O veiculo está a mais que 500 metros da rota. distancia: '$it'"))
            }
            val origins = listOf(coordinate)
            val dest = it.stops.toTypedArray()
            map.closer(origins, *dest)?.let {
                val stopName = (it.second as Stop).name
                //TODO: Internacionalizar código abaixo
                //TODO: Utilizar Event Handlers
                result.add(Event(vehicleId, "A parada mais proxima é o item com o id: '$stopName'"))
            }
        }
        result.forEach { handler.raise(it) }
        return result
    }
}