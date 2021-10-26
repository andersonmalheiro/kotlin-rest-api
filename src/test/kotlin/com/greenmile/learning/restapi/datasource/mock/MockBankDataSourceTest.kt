package com.greenmile.learning.restapi.datasource.mock

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

internal class MockBankDataSourceTest {
    private val mockDataSource = MockBankDataSource()

    @Test
    fun `should provide a collection of banks`() {
        // when
        val response = mockDataSource.list(accountNumber = null)

        // then
        assertThat(response.data.size).isGreaterThanOrEqualTo(3)
    }

    @Test
    fun `should provide some mock data`() {
        // when
        val response = mockDataSource.list(accountNumber = null)

        val data = response.data

        // then
        assertThat(data).allMatch { it.accountNumber.isNotEmpty() }
        assertThat(data).anyMatch { it.trust != 0.0 }
        assertThat(data).allMatch { it.transactionFee != 0 }
        assertThat(data.distinctBy { it.accountNumber }.size).isEqualTo(data.size)
    }
}