package com.greenmile.learning.restapi.datasource.mock

import com.greenmile.learning.restapi.datasource.BankDataSource
import com.greenmile.learning.restapi.model.Bank
import org.springframework.stereotype.Repository

@Repository
class MockBankDataSource : BankDataSource {
    val banks = mutableListOf(
        Bank(accountNumber = "1234", trust = 2.0, transactionFee = 12),
        Bank(accountNumber = "2312", trust = 1.0, transactionFee = 8),
        Bank(accountNumber = "6734", trust = 5.0, transactionFee = 3),
        Bank(accountNumber = "3534", trust = 5.0, transactionFee = 3),
    )

    override fun retrieveBanks(): Collection<Bank> = banks

    override fun retrieveBank(accountNumber: String): Bank =
        banks.firstOrNull { it.accountNumber == accountNumber }
            ?: throw NoSuchElementException("Could not find a bank with account number $accountNumber")

    override fun createBank(bank: Bank): Bank {
        if (banks.any { it.accountNumber == bank.accountNumber }) {
            throw IllegalArgumentException("Bank with account number ${bank.accountNumber} already exists")
        }

        banks.add(bank)

        return bank
    }
}