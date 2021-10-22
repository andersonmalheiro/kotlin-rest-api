package com.greenmile.learning.restapi

import com.greenmile.learning.restapi.db.impl.PostgresDbConnector
import com.greenmile.learning.restapi.model.Banks
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.StdOutSqlLogger
import org.jetbrains.exposed.sql.addLogger
import org.jetbrains.exposed.sql.transactions.transaction
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class RestApiApplication

fun main(args: Array<String>) {
    PostgresDbConnector(
        url = "jdbc:postgresql://localhost:5432/banks-api",
        user = "postgres",
        password = "admin"
    ).connect()

    transaction {
        addLogger(StdOutSqlLogger)

        SchemaUtils.create(Banks)
    }

    runApplication<RestApiApplication>(*args)
}
