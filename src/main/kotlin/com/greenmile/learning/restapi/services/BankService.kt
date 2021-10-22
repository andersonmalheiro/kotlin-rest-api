package com.greenmile.learning.restapi.services

import com.greenmile.learning.restapi.datasource.BankDataSource
import com.greenmile.learning.restapi.model.Bank
import com.greenmile.learning.restapi.model.BankDAO
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.stereotype.Service

@Service
class BankService(@Qualifier("bankDataSourceImpl") private val dataSource: BankDataSource) {
    fun getBanks(): Collection<Bank> = dataSource.retrieveBanks()
    fun getBank(accountNumber: String): Bank  = dataSource.retrieveBank(accountNumber)
    fun addBank(bank: Bank): Bank = dataSource.createBank(bank)
}