package com.greenmile.learning.restapi.datasource.impl

import com.greenmile.learning.restapi.datasource.BankDataSource
import com.greenmile.learning.restapi.model.Bank
import com.greenmile.learning.restapi.model.BankDAO
import com.greenmile.learning.restapi.model.Banks
import com.greenmile.learning.restapi.model.ListResponse
import com.greenmile.learning.restapi.utils.bankDAOToEntity
import com.greenmile.learning.restapi.utils.listResponseFactory
import org.jetbrains.exposed.exceptions.ExposedSQLException
import org.jetbrains.exposed.sql.SortOrder
import org.jetbrains.exposed.sql.transactions.transaction
import org.springframework.stereotype.Repository
import java.sql.BatchUpdateException
import java.sql.SQLIntegrityConstraintViolationException

@Repository
class BankDataSourceImpl : BankDataSource {
    override fun create(bank: Bank): Int {
        try {
            val newBank = transaction {
                BankDAO.new {
                    accountNumber = bank.accountNumber
                    trust = bank.trust
                    transactionFee = bank.transactionFee
                }
            }

            return newBank.id.value
        } catch (e: Exception) {
            when ((e as? ExposedSQLException)?.cause) {
                is SQLIntegrityConstraintViolationException, is BatchUpdateException ->
                    throw IllegalArgumentException("Bank with account number ${bank.accountNumber} already exists")
                else ->
                    throw e
            }
        }
    }

    override fun retrieveByAccountNumber(accountNumber: String): Bank {
        val foundBank = transaction {
            BankDAO.find { Banks.accountNumber eq accountNumber }.firstOrNull()
                ?: throw NoSuchElementException("Could not find a bank with account number $accountNumber")
        }

        return bankDAOToEntity(foundBank)
    }

    override fun retrieveById(id: Int): Bank {
        val bank = transaction {
            BankDAO.findById(id) ?: throw NoSuchElementException("Could not find a bank with ID $id")
        }

        return bankDAOToEntity(bank)
    }

    override fun list(accountNumber: String?): ListResponse<Bank> = transaction {
        if (accountNumber != null) {
            val banks = BankDAO.find { Banks.accountNumber eq accountNumber }.orderBy(Banks.id to SortOrder.ASC)
                .map { bankDAOToEntity(it) }
            val count = BankDAO.find { Banks.accountNumber eq accountNumber }.count()
            listResponseFactory(banks, count)
        } else {
            val banks = BankDAO.all().orderBy(Banks.id to SortOrder.ASC).map { bankDAOToEntity(it) }
            val count = BankDAO.all().count()
            listResponseFactory(banks, count)
        }
    }

    override fun update(id: Int, data: Bank): Bank {
        try {
            val bank = transaction {
                val bank = BankDAO.findById(id) ?: throw NoSuchElementException("Could not find a bank with ID $id")
                bank.accountNumber = data.accountNumber
                bank.trust = data.trust
                bank.transactionFee = data.transactionFee

                bank.flush()

                bank
            }

            return bankDAOToEntity(bank)
        } catch (e: Exception) {
            throw e
        }
    }
}