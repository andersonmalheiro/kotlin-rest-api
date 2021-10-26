package com.greenmile.learning.restapi.services

import com.greenmile.learning.restapi.datasource.BankDataSource
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.Test

internal class BankServiceTest {
    private val dataSource: BankDataSource = mockk(relaxed = true) // mocks the implementation of the methods
    private val bankService = BankService(dataSource)

    @Test
    fun `should call its data source to retrieve banks`() {
        // mocking the return value of a method
        // every { dataSource.retrieveBanks() } returns emptyList()

        // when
        bankService.getBanks(null)

        // then
        verify(exactly = 1) { dataSource.list(null) } // verify if the method was called exactly one time
    }
}