package com.greenmile.learning.restapi.controllers

import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.api.TestInstance.Lifecycle
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.get

// Example of integration test
@SpringBootTest
@AutoConfigureMockMvc
internal class BankControllerTest {
    val baseUrl = "/api/banks"

    @Autowired
    lateinit var mockMVC: MockMvc

    @Nested
    @DisplayName("getBanks()")
    @TestInstance(Lifecycle.PER_CLASS)
    inner class GetBanks {

        @Test
        fun `should return all the banks`() {
            // when/then
            mockMVC.get(baseUrl)
                .andDo { print() }
                .andExpect {
                    status { isOk() }
                    content { contentType(MediaType.APPLICATION_JSON) }
                    jsonPath("$[0].accountNumber") { value("1234") }
                }
        }

    }

    @Nested
    @DisplayName("getBank()")
    @TestInstance(Lifecycle.PER_CLASS)
    inner class GetBank {

        @Test
        fun `should return a bank with given ID`() {
            // given
            val accountNumber = 1234

            // when/then
            mockMVC.get("$baseUrl/$accountNumber")
                .andDo { print() }
                .andExpect {
                    status { isOk() }
                    content { contentType(MediaType.APPLICATION_JSON) }
                    jsonPath("$.trust") { value("2.0") }
                    jsonPath("$.transactionFee") { value("12") }
                }
        }

        @Test
        fun `should return Not found when given account number doesn't exists`() {
            // given
            val accountNumber = 9999

            // when/then
            mockMVC.get("$baseUrl/$accountNumber")
                .andDo { print() }
                .andExpect {
                    status { isNotFound() }
                }
        }
    }
}