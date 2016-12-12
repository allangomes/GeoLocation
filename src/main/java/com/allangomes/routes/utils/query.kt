package com.allangomes.routes.utils

import org.springframework.data.mongodb.core.query.Criteria
import org.springframework.data.mongodb.core.query.Query

/**
* Created by allangomes on 12/12/16.
*/

fun Query.exc(vararg field: String): Query {
    field.forEach {
        this.fields().exclude(it)
    }
    return this
}

internal fun Query.eq(field: String, value: Any?): Query {
    this.addCriteria(Criteria.where(field).`is`(value))
    return this
}