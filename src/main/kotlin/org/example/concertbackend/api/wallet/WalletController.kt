package org.example.concertbackend.api.wallet

import org.example.concertbackend.api.wallet.request.ChargeBalanceRequest
import org.example.concertbackend.api.wallet.response.BalanceResponse
import org.example.concertbackend.api.wallet.response.ChargeBalanceResponse
import org.example.concertbackend.common.model.ApiResponse
import org.example.concertbackend.common.model.SuccessType
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestHeader
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/v1")
class WalletController {
    @PatchMapping("/users/{userId}/wallet/charge")
    fun charge(
        @RequestHeader("QUEUE-AUTH-TOKEN") token: String,
        @PathVariable userId: Long,
        @RequestBody request: ChargeBalanceRequest,
    ): ResponseEntity<ApiResponse<ChargeBalanceResponse>> =
        ResponseEntity
            .status(HttpStatus.OK)
            .body(ApiResponse.success(SuccessType.BALANCE_CHARGED, ChargeBalanceResponse(1000)))

    @GetMapping("/users/{id}/wallet")
    fun getBalance(
        @RequestHeader("QUEUE-AUTH-TOKEN") token: String,
        @PathVariable id: Long,
    ): ResponseEntity<ApiResponse<BalanceResponse>> =
        ResponseEntity
            .status(HttpStatus.OK)
            .body(ApiResponse.success(SuccessType.BALANCE_FOUND, BalanceResponse(1000)))
}
