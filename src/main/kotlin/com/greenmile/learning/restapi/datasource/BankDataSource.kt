package com.greenmile.learning.restapi.datasource

import com.greenmile.learning.restapi.model.Bank

interface BankDataSource {
    fun retrieveBanks(): Collection<Bank>
}