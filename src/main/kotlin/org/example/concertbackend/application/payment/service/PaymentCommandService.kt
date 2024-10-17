package org.example.concertbackend.application.payment.service

import org.example.concertbackend.common.exception.BusinessException
import org.example.concertbackend.common.model.ErrorType
import org.example.concertbackend.common.util.TimeProvider
import org.example.concertbackend.domain.payment.PaymentHistory
import org.example.concertbackend.domain.payment.PaymentHistoryRepository
import org.example.concertbackend.domain.payment.PaymentStatus
import org.example.concertbackend.domain.wallet.WalletRepository
import org.springframework.stereotype.Service

@Service
class PaymentCommandService(
    private val timeProvider: TimeProvider,
    private val walletRepository: WalletRepository,
    private val paymentHistoryRepository: PaymentHistoryRepository,
) {
    fun pay(
        concertReservationId: Long,
        totalPrice: Long,
        userId: Long,
    ) {
        val wallet =
            walletRepository.findByUserIdWithLock(userId)
                ?: throw BusinessException(ErrorType.WALLET_NOT_FOUND)
        wallet.pay(totalPrice)
        walletRepository.update(wallet)

        val paymentHistory =
            PaymentHistory(
                walletId = wallet.id,
                concertReservationId = concertReservationId,
                amount = totalPrice,
                status = PaymentStatus.PAID,
                paidAt = timeProvider.now(),
            )
        paymentHistoryRepository.save(paymentHistory)
    }
}
