package com.greenmile.learning.restapi.datasource.mock

import com.greenmile.learning.restapi.datasource.BankDataSource
import com.greenmile.learning.restapi.model.Bank
import org.springframework.stereotype.Repository

@Repository
class MockBankDataSource : BankDataSource {
    val banks = listOf(
        Bank("1234", 2.0, 12),
        Bank("2312", 1.0, 8),
        Bank("6734", 5.0, 3),
        Bank("3534", 5.0, 3),
    )

    override fun retrieveBanks(): Collection<Bank> = banks
    override fun retrieveBank(accountNumber: String): Bank =
        banks.firstOrNull { it.accountNumber == accountNumber }
            ?: throw NoSuchElementException("Could not find a bank with account number $accountNumber")
}