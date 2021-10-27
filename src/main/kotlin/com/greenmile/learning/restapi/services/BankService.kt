package com.greenmile.learning.restapi.services

import com.greenmile.learning.restapi.datasource.BankDataSource
import com.greenmile.learning.restapi.model.Bank
import com.greenmile.learning.restapi.model.BankFilters
import com.greenmile.learning.restapi.model.ListResponse
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.stereotype.Service

@Service
class BankService(@Qualifier("bankDataSourceImpl") private val dataSource: BankDataSource) {
    fun getBanks(
        accountNumber: String?,
        trust: Double?,
        transactionFee: Int?,
    ): ListResponse<Bank> =
        dataSource.list(
            filters = BankFilters(
                accountNumber,
                trust,
                transactionFee,
            )
        )

    fun getBankById(id: Int): Bank = dataSource.retrieveById(id)

    fun getBankByAccountNumber(accountNumber: String): Bank = dataSource.retrieveByAccountNumber(accountNumber)

    fun addBank(bank: Bank): Int = dataSource.create(bank)

    fun updateBank(id: Int, data: Bank): Bank = dataSource.update(id, data)
}