package com.allangomes.routes.stop

import com.allangomes.routes.utils.Coordinate
import com.allangomes.routes.utils.LatLng
import org.bson.types.ObjectId
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

/**
* Created by allangomes on 08/12/16.
*/

@Document
class Stop(val name: String, val coordinate: LatLng) : Coordinate by coordinate {
    @Id
    val id: String = ObjectId().toString()
}
