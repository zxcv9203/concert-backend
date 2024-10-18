package org.example.concertbackend.application.payment.service

import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.example.concertbackend.common.exception.BusinessException
import org.example.concertbackend.common.model.ErrorType
import org.example.concertbackend.common.util.TimeProvider
import org.example.concertbackend.domain.payment.PaymentHistoryRepository
import org.example.concertbackend.domain.wallet.WalletRepository
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(MockKExtension::class)
class PaymentCommandServiceTest {
    @InjectMockKs
    private lateinit var paymentCommandService: PaymentCommandService

    @MockK
    private lateinit var timeProvider: TimeProvider

    @MockK
    private lateinit var walletRepository: WalletRepository

    @MockK
    private lateinit var paymentHistoryRepository: PaymentHistoryRepository

    @Nested
    @DisplayName("결제")
    inner class Pay {
        @Test
        @DisplayName("지갑이 존재하지 않는 경우 예외 발생")
        fun walletNotFound() {
            val concertReservationId = 1L
            val totalPrice = 10000L
            val userId = 1L

            every { walletRepository.findByUserIdWithLock(userId) } returns null

            assertThatThrownBy { paymentCommandService.pay(concertReservationId, totalPrice, userId) }
                .isInstanceOf(BusinessException::class.java)
                .hasMessage(ErrorType.WALLET_NOT_FOUND.message)
        }
    }
}
