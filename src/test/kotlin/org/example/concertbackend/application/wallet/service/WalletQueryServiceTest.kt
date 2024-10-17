package org.example.concertbackend.application.wallet.service

import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.example.concertbackend.common.exception.BusinessException
import org.example.concertbackend.common.model.ErrorType
import org.example.concertbackend.domain.wallet.WalletRepository
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.extension.ExtendWith
import kotlin.test.Test

@ExtendWith(MockKExtension::class)
class WalletQueryServiceTest {
    @InjectMockKs
    private lateinit var walletQueryService: WalletQueryService

    @MockK
    private lateinit var walletRepository: WalletRepository

    @Nested
    @DisplayName("사용자 ID로 지갑 조회")
    inner class GetByUserId {
        @DisplayName("전달받은 사용자 ID에 해당하는 지갑이 존재하지 않는 경우 예외가 발생한다.")
        @Test
        fun testGetByUserIdNotFound() {
            val userId = 1L
            every { walletRepository.findByUserId(userId) } returns null

            assertThatThrownBy { walletQueryService.getByUserId(userId) }
                .isInstanceOf(BusinessException::class.java)
                .hasMessage(ErrorType.WALLET_NOT_FOUND.message)
        }
    }
}
