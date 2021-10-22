package com.greenmile.learning.restapi.db.impl

import com.greenmile.learning.restapi.db.DbConnector
import org.jetbrains.exposed.sql.Database

class PostgresDbConnector(
    private val url: String,
    private val user: String,
    private val password: String,
) : DbConnector {
    companion object {
        lateinit var db: Database
    }
    override val driver = "org.postgresql.Driver"

    override fun connect(
    ) {
        db = Database.connect(
            url = url,
            driver = driver,
            user = user,
            password = password
        )
    }

    fun init() {
        connect()
    }
}