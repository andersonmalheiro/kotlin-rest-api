package com.greenmile.learning.restapi.model

data class Bank(
    val id: Int? = null,
    val accountNumber: String,
    val trust: Double,
    val transactionFee: Int,
)

class BankFilters(
    val accountNumber: String? = null,
    val trust: Double? = null,
    val transactionFee: Int? = null,
    override val createdAt: String? = null,
    override val updatedAt: String? = null,
    override val id: Long? = null,
) : FilterBase