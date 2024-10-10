package org.example.concertbackend.api.wallet

import org.example.concertbackend.api.wallet.request.ChargeWalletRequest
import org.example.concertbackend.api.wallet.response.ChargeWalletResponse
import org.example.concertbackend.common.model.ApiResponse
import org.example.concertbackend.common.model.SuccessType
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
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
        @RequestBody request: ChargeWalletRequest,
    ): ResponseEntity<ApiResponse<ChargeWalletResponse>> =
        ResponseEntity
            .status(HttpStatus.OK)
            .body(ApiResponse.success(SuccessType.WALLET_CHARGED, ChargeWalletResponse(1000)))
}
