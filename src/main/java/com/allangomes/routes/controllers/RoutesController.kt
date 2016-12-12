package com.allangomes.routes.controllers

import com.allangomes.routes.route.*
import com.allangomes.routes.route.dto.RouteList
import com.allangomes.routes.utils.LatLng
import com.allangomes.routes.utils.response
import com.google.gson.Gson
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import java.io.IOException

/**
* Created by allangomes on 08/12/16.
*/

@RestController
@RequestMapping("/routes")
class RoutesController {

    @Autowired
    lateinit private var gson: Gson

    @Autowired
    lateinit private var routeService: RouteService

    @Autowired
    lateinit private var routes: Routes

    @GetMapping("/", produces = arrayOf(response.JSON))
    fun all(): Any {
        return gson.toJson(routes.all().map(::RouteList))
    }

    @GetMapping("/{id}/path", produces = arrayOf(response.JSON))
    fun path(@PathVariable("id") id: String): Any {
        return gson.toJson(routes.findOne(id).path)
    }

    @PostMapping("vehicle/{vehicleId}/checkpoint", produces = arrayOf(response.JSON))
    @ResponseStatus(value = HttpStatus.CREATED)
    fun checkpoint(@PathVariable("vehicleId") vehicleId: Int,
                   @RequestParam("lat", required = true) lat: Double,
                   @RequestParam("lng", required = true) lng: Double): Any? {
        return routeService.checkpoint(vehicleId, LatLng(lat, lng))
    }

    @PostMapping("/", produces = arrayOf(response.JSON))
    @ResponseStatus(value = HttpStatus.CREATED)
    @Throws(IOException::class)
    fun post(@RequestBody body: String): Any {
        val route = gson.fromJson(body, Route::class.java)
        return gson.toJson(routeService.create(route))
    }
}
