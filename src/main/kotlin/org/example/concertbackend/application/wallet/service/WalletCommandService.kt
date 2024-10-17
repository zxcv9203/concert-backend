package org.example.concertbackend.application.wallet.service

import org.example.concertbackend.common.exception.BusinessException
import org.example.concertbackend.common.model.ErrorType
import org.example.concertbackend.domain.wallet.BalanceHistory
import org.example.concertbackend.domain.wallet.BalanceHistoryRepository
import org.example.concertbackend.domain.wallet.BalanceHistoryType
import org.example.concertbackend.domain.wallet.Wallet
import org.example.concertbackend.domain.wallet.WalletRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class WalletCommandService(
    private val walletRepository: WalletRepository,
    private val balanceHistoryRepository: BalanceHistoryRepository,
) {
    @Transactional
    fun charge(
        userId: Long,
        amount: Long,
    ): Wallet {
        val wallet =
            walletRepository.findByUserIdWithLock(userId)
                ?: throw BusinessException(ErrorType.WALLET_NOT_FOUND)

        wallet.charge(amount)

        val balanceHistory =
            BalanceHistory(
                walletId = wallet.id,
                balance = amount,
                type = BalanceHistoryType.DEPOSIT,
            )
        balanceHistoryRepository.save(balanceHistory)

        return walletRepository.update(wallet)
    }
}
