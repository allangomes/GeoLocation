package com.allangomes.routes.event.internal

import com.allangomes.routes.event.Event
import com.allangomes.routes.event.Handler
import org.springframework.stereotype.Service

/**
* Created by allangomes on 12/12/16.
*/

@Service
internal open class HandlerService(val repo: EventRepository) : Handler {

    override fun raise(event: Event): Event {
        return repo.insert(event)
    }

}