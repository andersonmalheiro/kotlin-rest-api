package com.greenmile.learning.restapi.utils

import com.greenmile.learning.restapi.model.ListResponse

fun <T>listResponseFactory(data: Collection<T>, count: Long): ListResponse<T> = ListResponse(data, count)