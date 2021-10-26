package com.greenmile.learning.restapi.services

import com.greenmile.learning.restapi.datasource.BankDataSource
import com.greenmile.learning.restapi.model.Bank
import com.greenmile.learning.restapi.model.ListResponse
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.stereotype.Service

@Service
class BankService(@Qualifier("bankDataSourceImpl") private val dataSource: BankDataSource) {
    fun getBanks(accountNumber: String?): ListResponse<Bank> = dataSource.list(accountNumber)
    fun getBankById(id: Int): Bank = dataSource.retrieveById(id)
    fun getBankByAccountNumber(accountNumber: String): Bank  = dataSource.retrieveByAccountNumber(accountNumber)
    fun addBank(bank: Bank): Int = dataSource.create(bank)
}