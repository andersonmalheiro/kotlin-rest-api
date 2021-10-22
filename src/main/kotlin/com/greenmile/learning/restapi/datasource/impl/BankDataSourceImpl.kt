package com.greenmile.learning.restapi.datasource.impl

import com.greenmile.learning.restapi.datasource.BankDataSource
import com.greenmile.learning.restapi.model.Bank
import com.greenmile.learning.restapi.model.BankDAO
import com.greenmile.learning.restapi.model.Banks
import org.jetbrains.exposed.sql.transactions.transaction
import org.postgresql.util.PSQLException
import org.springframework.stereotype.Repository

@Repository
class BankDataSourceImpl : BankDataSource {
    override fun createBank(bank: Bank): Bank {
        try {
            val newBank = transaction {
                BankDAO.new {
                    accountNumber = bank.accountNumber
                    trust = bank.trust
                    transactionFee = bank.transactionFee
                }
            }

            return Bank(
                id = newBank.id.value,
                accountNumber = newBank.accountNumber,
                trust = newBank.trust,
                transactionFee = newBank.transactionFee,
            )
        } catch (e: PSQLException) {
            throw Error("Bank with account number ${bank.accountNumber} already exists")
        }
    }

    override fun retrieveBank(accountNumber: String): Bank {
        val foundBank = transaction {
            BankDAO.find { Banks.accountNumber eq accountNumber }.first()
        }

        return Bank(
            id = foundBank.id.value,
            accountNumber = foundBank.accountNumber,
            trust = foundBank.trust,
            transactionFee = foundBank.transactionFee,
        )
    }

    override fun retrieveBanks(): Collection<Bank> = transaction {
        BankDAO.all().map {
            Bank(
                id = it.id.value,
                accountNumber = it.accountNumber,
                trust = it.trust,
                transactionFee = it.transactionFee,
            )
        }
    }
}