package com.greenmile.learning.restapi.datasource.impl

import com.greenmile.learning.restapi.datasource.BankDataSource
import com.greenmile.learning.restapi.model.Bank
import com.greenmile.learning.restapi.model.BankDAO
import com.greenmile.learning.restapi.model.Banks
import org.jetbrains.exposed.exceptions.ExposedSQLException
import org.jetbrains.exposed.sql.transactions.transaction
import org.postgresql.util.PSQLException
import org.springframework.stereotype.Repository
import java.sql.BatchUpdateException
import java.sql.SQLIntegrityConstraintViolationException

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
        } catch (e: Exception) {
            when ((e as? ExposedSQLException)?.cause) {
                is SQLIntegrityConstraintViolationException ->
                    throw IllegalArgumentException("Bank with account number ${bank.accountNumber} already exists")
                is BatchUpdateException -> {
                    throw IllegalArgumentException("SQL constraint violated")
                }
                else ->
                    throw Exception(e.message)
            }
        }
    }

    override fun retrieveBank(accountNumber: String): Bank {
        val foundBank = transaction {
            BankDAO.find { Banks.accountNumber eq accountNumber }.firstOrNull()
                ?: throw NoSuchElementException("Could not find a bank with account number $accountNumber")
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