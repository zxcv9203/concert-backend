package org.example.concertbackend.application.wallet.usecase

import org.example.concertbackend.api.wallet.request.ChargeBalanceRequest
import org.example.concertbackend.api.wallet.response.BalanceResponse
import org.example.concertbackend.api.wallet.response.ChargeBalanceResponse
import org.example.concertbackend.application.user.service.UserQueryService
import org.example.concertbackend.application.wallet.service.WalletCommandService
import org.example.concertbackend.application.wallet.service.WalletQueryService
import org.springframework.stereotype.Component

@Component
class WalletUseCase(
    private val userQueryService: UserQueryService,
    private val walletCommandService: WalletCommandService,
    private val walletQueryService: WalletQueryService,
) {
    fun charge(
        userId: Long,
        request: ChargeBalanceRequest,
    ): ChargeBalanceResponse {
        userQueryService.getById(userId)

        val wallet = walletCommandService.charge(userId, request.amount)
        return ChargeBalanceResponse(wallet.balance)
    }

    fun getBalance(id: Long): BalanceResponse {
        userQueryService.getById(id)

        val wallet = walletQueryService.getByUserId(id)

        return BalanceResponse(wallet.balance)
    }
}
