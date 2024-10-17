package org.example.concertbackend.application.wallet.usecase

import org.example.concertbackend.api.wallet.request.ChargeBalanceRequest
import org.example.concertbackend.api.wallet.response.ChargeBalanceResponse
import org.example.concertbackend.application.user.service.UserQueryService
import org.example.concertbackend.application.wallet.service.WalletCommandService
import org.springframework.stereotype.Component

@Component
class WalletUseCase(
    private val userQueryService: UserQueryService,
    private val walletCommandService: WalletCommandService,
) {
    fun charge(
        userId: Long,
        request: ChargeBalanceRequest,
    ): ChargeBalanceResponse {
        userQueryService.getById(userId)

        val wallet = walletCommandService.charge(userId, request.amount)
        return ChargeBalanceResponse(wallet.balance)
    }
}
