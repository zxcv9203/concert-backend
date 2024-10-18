package org.example.concertbackend.application.wallet.usecase

import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.example.concertbackend.api.wallet.request.ChargeBalanceRequest
import org.example.concertbackend.common.exception.BusinessException
import org.example.concertbackend.common.model.ErrorType
import org.example.concertbackend.helper.ConcurrentTestHelper
import org.example.concertbackend.helper.DatabaseCleanUp
import org.example.concertbackend.infrastructure.persistence.user.entity.UserJpaEntity
import org.example.concertbackend.infrastructure.persistence.user.repository.DataJpaUserRepository
import org.example.concertbackend.infrastructure.persistence.wallet.entity.WalletJpaEntity
import org.example.concertbackend.infrastructure.persistence.wallet.repository.DataJpaBalanceHistoryRepository
import org.example.concertbackend.infrastructure.persistence.wallet.repository.DataJpaWalletRepository
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import kotlin.test.Test

@SpringBootTest
class IntegrationWalletUseCaseTest {
    @Autowired
    private lateinit var walletUseCase: WalletUseCase

    @Autowired
    private lateinit var dataJpaWalletRepository: DataJpaWalletRepository

    @Autowired
    private lateinit var dataJpaUserRepository: DataJpaUserRepository

    @Autowired
    private lateinit var dataJpaBalanceHistoryRepository: DataJpaBalanceHistoryRepository

    @Autowired
    private lateinit var databaseCleanUp: DatabaseCleanUp

    @BeforeEach
    fun setUp() {
        val user = dataJpaUserRepository.save(UserJpaEntity("user"))
        dataJpaWalletRepository.save(WalletJpaEntity(user.id, 0))
    }

    @AfterEach
    fun tearDown() {
        databaseCleanUp.execute()
    }

    @Nested
    @DisplayName("잔액 충전")
    inner class Charge {
        private val userId = 1L

        @Test
        @DisplayName("사용자의 잔액을 충전한다.")
        fun charge() {
            val request = ChargeBalanceRequest(1000)
            val response = walletUseCase.charge(userId, request)

            assertThat(response.balance).isEqualTo(1000)
        }

        @Test
        @DisplayName("동시에 50번 요청이 일어난 경우 히스토리 저장 및 금액이 누락되지 않지 않는다.")
        fun chargeConcurrently() {
            val request = ChargeBalanceRequest(1000)
            val threadCount = 50

            ConcurrentTestHelper.executeAsyncTasks(
                taskCount = threadCount,
                task = { walletUseCase.charge(userId, request) },
            )

            val wallet = dataJpaWalletRepository.findByUserId(userId)!!
            val balanceHistories = dataJpaBalanceHistoryRepository.findAll()

            assertThat(wallet.balance).isEqualTo(50000)
            assertThat(balanceHistories.size).isEqualTo(threadCount)
        }

        @Test
        @DisplayName("존재하지 않는 사용자의 잔액을 충전하려고 할 때 예외가 발생한다.")
        fun chargeWithNonExistingUser() {
            val userId = 2L
            val request = ChargeBalanceRequest(1000)

            assertThatThrownBy { walletUseCase.charge(userId, request) }
                .isInstanceOf(BusinessException::class.java)
                .hasMessage(ErrorType.USER_NOT_FOUND.message)
        }
    }

    @Nested
    @DisplayName("잔액 조회")
    inner class GetBalance {
        private val userId = 1L

        @Test
        @DisplayName("사용자의 잔액을 조회한다.")
        fun getBalance() {
            val response = walletUseCase.getBalance(userId)

            assertThat(response.balance).isEqualTo(0)
        }

        @Test
        @DisplayName("존재하지 않는 사용자의 잔액을 조회하려고 할 때 예외가 발생한다.")
        fun getBalanceWithNonExistingUser() {
            val userId = 2L

            assertThatThrownBy { walletUseCase.getBalance(userId) }
                .isInstanceOf(BusinessException::class.java)
                .hasMessage(ErrorType.USER_NOT_FOUND.message)
        }
    }
}
