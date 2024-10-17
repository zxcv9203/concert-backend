package org.example.concertbackend.application.wallet.service

import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.example.concertbackend.common.exception.BusinessException
import org.example.concertbackend.common.model.ErrorType
import org.example.concertbackend.domain.wallet.BalanceHistory
import org.example.concertbackend.domain.wallet.BalanceHistoryRepository
import org.example.concertbackend.domain.wallet.BalanceHistoryType
import org.example.concertbackend.domain.wallet.Wallet
import org.example.concertbackend.domain.wallet.WalletRepository
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.extension.ExtendWith
import kotlin.test.Test

@ExtendWith(MockKExtension::class)
class WalletCommandServiceTest {
    @InjectMockKs
    private lateinit var walletCommandService: WalletCommandService

    @MockK
    private lateinit var walletRepository: WalletRepository

    @MockK
    private lateinit var balanceHistoryRepository: BalanceHistoryRepository

    @Nested
    @DisplayName("충전")
    inner class Charge {
        @Test
        @DisplayName("전달한 잔액만큼 충전된다.")
        fun charge() {
            val wallet = Wallet(1, 0)
            every { walletRepository.findByUserIdWithLock(1) } returns wallet
            every { walletRepository.update(wallet) } returns wallet
            every { balanceHistoryRepository.save(any()) } returns BalanceHistory(1, 1000, BalanceHistoryType.DEPOSIT)

            val result = walletCommandService.charge(1, 1000)

            assertThat(result.balance).isEqualTo(1000)
        }

        @Test
        @DisplayName("지갑이 없을 경우 예외가 발생한다.")
        fun walletNotFound() {
            every { walletRepository.findByUserIdWithLock(1) } returns null

            assertThatThrownBy { walletCommandService.charge(1, 1000) }
                .isInstanceOf(BusinessException::class.java)
                .hasMessage(ErrorType.WALLET_NOT_FOUND.message)
        }
    }
}
