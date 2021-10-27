package com.greenmile.learning.restapi.utils

import com.greenmile.learning.restapi.model.Bank
import com.greenmile.learning.restapi.dao.BankDAO

fun bankDAOToEntity(bank: BankDAO): Bank = Bank(
    id = bank.id.value,
    accountNumber = bank.accountNumber,
    trust = bank.trust,
    transactionFee = bank.transactionFee,
)