package com.greenmile.learning.restapi.datasource

import com.greenmile.learning.restapi.model.ListResponse

interface CrudBaseDataSource<EntityType, EntityFilter> {
    fun create(bank: EntityType): Int

    fun list(filters: EntityFilter): ListResponse<EntityType>

    fun retrieveById(id: Int): EntityType

    fun update(id: Int, data: EntityType): EntityType

    fun delete(id: Int)
}
