package com.allangomes.routes.event.internal

import com.allangomes.routes.event.Event
import org.springframework.data.mongodb.core.MongoOperations
import org.springframework.stereotype.Repository

/**
* Created by allangomes on 12/12/16.
*/

internal interface EventRepository {
    fun insert(event: Event): Event
}

@Repository
internal open class DefaultEventRepository(var mongo: MongoOperations) : EventRepository {

    override fun insert(event: Event): Event {
        this.mongo.insert(event)
        return event
    }
}