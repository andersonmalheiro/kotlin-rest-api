package com.greenmile.learning.restapi.model

data class ListResponse<T>(
    val data: Collection<T>,
    val count: Long,
)