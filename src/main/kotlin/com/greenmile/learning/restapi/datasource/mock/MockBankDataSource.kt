package com.greenmile.learning.restapi.datasource.mock

import com.greenmile.learning.restapi.datasource.BankDataSource
import com.greenmile.learning.restapi.model.Bank
import com.greenmile.learning.restapi.model.ListResponse
import org.springframework.stereotype.Repository

@Repository
class MockBankDataSource : BankDataSource {
    private val banks = mutableListOf(
        Bank(accountNumber = "1234", trust = 2.0, transactionFee = 12),
        Bank(accountNumber = "2312", trust = 1.0, transactionFee = 8),
        Bank(accountNumber = "6734", trust = 5.0, transactionFee = 3),
        Bank(accountNumber = "3534", trust = 5.0, transactionFee = 3),
    )

    val response = ListResponse(data = banks, banks.size.toLong())

    override fun list(accountNumber: String?): ListResponse<Bank> = response

    override fun retrieveByAccountNumber(accountNumber: String): Bank =
        response.data.firstOrNull { it.accountNumber == accountNumber }
            ?: throw NoSuchElementException("Could not find a bank with account number $accountNumber")


    override fun retrieveById(id: Int): Bank =
        response.data.firstOrNull { it.id == id }
            ?: throw NoSuchElementException("Could not find a bank with ID $id")

    override fun create(bank: Bank): Int {
        if (response.data.any { it.accountNumber == bank.accountNumber }) {
            throw IllegalArgumentException("Bank with account number ${bank.accountNumber} already exists")
        }

        response.data.plus(bank)

        return bank.id!!
    }

    override fun update(id: Int, data: Bank): Bank {
        response.data.find { it.id == id } ?: throw IllegalArgumentException("Bank with account number $id not found")

        response.data.map {
            if (it.id == id) it.copy(
                accountNumber = data.accountNumber,
                trust = data.trust,
                transactionFee = data.transactionFee,
            ) else it
        }

        return data
    }
}