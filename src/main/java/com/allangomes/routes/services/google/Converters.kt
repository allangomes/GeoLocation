package com.allangomes.routes.services.google

import com.allangomes.routes.utils.Coordinate
import com.google.maps.model.LatLng

/**
* Created by allangomes on 08/12/16.
*/


internal fun Coordinate.google(): LatLng {
    return LatLng(this.lat, this.lng)
}

internal fun LatLng.model(): Coordinate {
    return com.allangomes.routes.utils.LatLng(this.lat, this.lng)
}
