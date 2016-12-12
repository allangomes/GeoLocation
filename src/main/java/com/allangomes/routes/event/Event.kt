package com.allangomes.routes.event

import org.bson.types.ObjectId
import org.springframework.data.annotation.Id
import java.util.*

/**
* Created by allangomes on 11/12/16.
*/

data class Event(val vehicle: Int, val message: String) {
    @Id
    val id: String = ObjectId().toString()
    val date: Date = Date()
}
