package com.greenmile.learning.restapi.datasource

import com.greenmile.learning.restapi.model.Bank
import com.greenmile.learning.restapi.model.BankDAO

interface BankDataSource {
    fun retrieveBanks(): Collection<Bank>

    fun retrieveBank(accountNumber: String): Bank

    fun createBank(bank: Bank): Bank
}