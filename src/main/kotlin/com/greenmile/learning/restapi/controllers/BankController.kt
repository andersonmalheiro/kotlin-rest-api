package com.greenmile.learning.restapi.controllers

import com.greenmile.learning.restapi.model.Bank
import com.greenmile.learning.restapi.model.ListResponse
import com.greenmile.learning.restapi.services.BankService
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/banks")
class BankController(private val service: BankService) {
    @GetMapping
    fun getAllBanks(
        @RequestParam(required = false) accountNumber: String?,
        @RequestParam(required = false) trust: Double?,
        @RequestParam(required = false) transactionFee: Int?,
    ): ListResponse<Bank> = service.getBanks(
        accountNumber,
        trust,
        transactionFee
    )

    @GetMapping("/{id}")
    fun getBankById(@PathVariable id: Int) = service.getBankById(id)

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun addBank(@RequestBody bank: Bank) = service.addBank(bank)

    @PatchMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    fun updateBank(@PathVariable id: Int, @RequestBody data: Bank) = service.updateBank(id, data)
}