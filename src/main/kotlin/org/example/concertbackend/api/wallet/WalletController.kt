package org.example.concertbackend.api.wallet

import org.example.concertbackend.api.wallet.request.ChargeBalanceRequest
import org.example.concertbackend.api.wallet.response.BalanceResponse
import org.example.concertbackend.api.wallet.response.ChargeBalanceResponse
import org.example.concertbackend.application.wallet.usecase.WalletUseCase
import org.example.concertbackend.common.model.ApiResponse
import org.example.concertbackend.common.model.SuccessType
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/v1")
class WalletController(
    private val walletUseCase: WalletUseCase,
) : WalletApi {
    override fun charge(
        @PathVariable userId: Long,
        @RequestBody request: ChargeBalanceRequest,
    ): ResponseEntity<ApiResponse<ChargeBalanceResponse>> =
        ResponseEntity
            .status(HttpStatus.OK)
            .body(ApiResponse.success(SuccessType.BALANCE_CHARGED, walletUseCase.charge(userId, request)))

    override fun getBalance(
        @PathVariable id: Long,
    ): ResponseEntity<ApiResponse<BalanceResponse>> =
        ResponseEntity
            .status(HttpStatus.OK)
            .body(ApiResponse.success(SuccessType.BALANCE_FOUND, walletUseCase.getBalance(id)))
}
