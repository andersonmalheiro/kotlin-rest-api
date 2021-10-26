package com.greenmile.learning.restapi.datasource

import com.greenmile.learning.restapi.model.Bank
import com.greenmile.learning.restapi.model.BankDAO
import com.greenmile.learning.restapi.model.ListResponse

interface BankDataSource {
    fun create(bank: Bank): Int

    fun list(accountNumber: String?): ListResponse<Bank>

    fun retrieveByAccountNumber(accountNumber: String): Bank

    fun retrieveById(id: Int): Bank

    fun update(id: Int, data: Bank): Bank
}