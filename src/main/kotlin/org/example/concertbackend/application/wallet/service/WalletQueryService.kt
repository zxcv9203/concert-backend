package org.example.concertbackend.application.wallet.service

import org.example.concertbackend.common.exception.BusinessException
import org.example.concertbackend.common.model.ErrorType
import org.example.concertbackend.domain.wallet.WalletRepository
import org.springframework.stereotype.Service

@Service
class WalletQueryService(
    private val walletQueryService: WalletRepository,
) {
    fun getByUserId(userId: Long) =
        walletQueryService.findByUserId(userId)
            ?: throw BusinessException(ErrorType.WALLET_NOT_FOUND)
}
