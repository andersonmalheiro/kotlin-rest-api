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
    fun getAllBanks(@RequestParam(required = false) accountNumber: String?): ListResponse<Bank> = service.getBanks(accountNumber)

    @GetMapping("/{id}")
    fun getBankById(@PathVariable id: Int) = service.getBankById(id)

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun addBank(@RequestBody bank: Bank) = service.addBank(bank)
}