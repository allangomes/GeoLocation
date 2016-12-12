package com.allangomes.routes.event

/**
* Created by allangomes on 11/12/16.
*/

interface Handler {
    fun raise(event: Event): Event
}