package com.greenmile.learning.restapi.db

import org.jetbrains.exposed.sql.Database

interface DbConnector {
    val driver: String

    fun connect()
}