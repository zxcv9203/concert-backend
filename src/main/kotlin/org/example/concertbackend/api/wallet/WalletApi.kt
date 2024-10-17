package org.example.concertbackend.api.wallet

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.example.concertbackend.api.wallet.request.ChargeBalanceRequest
import org.example.concertbackend.api.wallet.response.ChargeBalanceResponse
import org.example.concertbackend.common.model.ApiResponse
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestBody

@Tag(name = "지갑 API", description = "사용자의 지갑 정보를 조회하고, 충전하는 API 입니다.")
interface WalletApi {
    @Operation(
        summary = "사용자의 잔액 충전",
        description = "사용자의 잔액을 충전하는 API 입니다.",
    )
    @PatchMapping("/users/{userId}/wallet/balance")
    fun charge(
        @PathVariable userId: Long,
        @RequestBody request: ChargeBalanceRequest,
    ): ResponseEntity<ApiResponse<ChargeBalanceResponse>>
}
