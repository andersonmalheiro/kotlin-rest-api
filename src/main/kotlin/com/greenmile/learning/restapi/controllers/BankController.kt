package com.greenmile.learning.restapi.controllers

import com.greenmile.learning.restapi.model.Bank
import com.greenmile.learning.restapi.services.BankService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/banks")
class BankController(private val service: BankService) {

    @GetMapping
    fun getBanks(): Collection<Bank> = service.getBanks()
}