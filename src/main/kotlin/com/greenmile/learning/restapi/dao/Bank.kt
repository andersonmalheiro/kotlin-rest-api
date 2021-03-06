package com.greenmile.learning.restapi.dao

import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.Column

object Banks : IntIdTable() {
    val accountNumber: Column<String> = varchar("accountNumber", 30).uniqueIndex()
    val trust: Column<Double> = double("trust")
    val transactionFee: Column<Int> = integer("transactionFee")
}

class BankDAO(id: EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<BankDAO>(Banks)

    var accountNumber by Banks.accountNumber
    var trust by Banks.trust
    var transactionFee by Banks.transactionFee
}