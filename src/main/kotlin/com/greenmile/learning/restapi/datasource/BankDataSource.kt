package com.greenmile.learning.restapi.datasource

import com.greenmile.learning.restapi.model.Bank
import com.greenmile.learning.restapi.model.BankFilters

interface BankDataSource: CrudBaseDataSource<Bank, BankFilters> {
    fun retrieveByAccountNumber(accountNumber: String): Bank
}