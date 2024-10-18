package org.example.concertbackend.application.payment.usecase

import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.example.concertbackend.api.concert.request.ReservationConcertSeatRequest
import org.example.concertbackend.api.payment.request.PaymentRequest
import org.example.concertbackend.application.concert.usecase.ConcertUseCase
import org.example.concertbackend.common.exception.BusinessException
import org.example.concertbackend.common.model.ErrorType
import org.example.concertbackend.domain.queue.QueueStatus
import org.example.concertbackend.helper.ConcurrentTestHelper
import org.example.concertbackend.helper.DatabaseCleanUp
import org.example.concertbackend.infrastructure.persistence.concert.entity.ConcertJpaEntity
import org.example.concertbackend.infrastructure.persistence.concert.entity.ConcertScheduleJpaEntity
import org.example.concertbackend.infrastructure.persistence.concert.entity.ConcertSeatJpaEntity
import org.example.concertbackend.infrastructure.persistence.concert.repository.DataJpaConcertRepository
import org.example.concertbackend.infrastructure.persistence.concert.repository.DataJpaConcertScheduleRepository
import org.example.concertbackend.infrastructure.persistence.concert.repository.DataJpaConcertSeatRepository
import org.example.concertbackend.infrastructure.persistence.concert.reservation.mapper.toDomain
import org.example.concertbackend.infrastructure.persistence.concert.reservation.mapper.toJpaEntity
import org.example.concertbackend.infrastructure.persistence.concert.reservation.repository.DataJpaConcertReservationRepository
import org.example.concertbackend.infrastructure.persistence.queue.entity.QueueTokenJpaEntity
import org.example.concertbackend.infrastructure.persistence.queue.entity.WaitingQueueJpaEntity
import org.example.concertbackend.infrastructure.persistence.queue.repository.DataJpaQueueTokenRepository
import org.example.concertbackend.infrastructure.persistence.queue.repository.DataJpaWaitingQueueRepository
import org.example.concertbackend.infrastructure.persistence.user.entity.UserJpaEntity
import org.example.concertbackend.infrastructure.persistence.user.repository.DataJpaUserRepository
import org.example.concertbackend.infrastructure.persistence.wallet.entity.WalletJpaEntity
import org.example.concertbackend.infrastructure.persistence.wallet.repository.DataJpaWalletRepository
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.data.repository.findByIdOrNull
import java.time.LocalDateTime

@SpringBootTest
class IntegrationPaymentUseCaseTest {
    @Autowired
    private lateinit var paymentUseCase: PaymentUseCase

    @Autowired
    private lateinit var concertUseCase: ConcertUseCase

    @Autowired
    private lateinit var databaseCleanUp: DatabaseCleanUp

    @Autowired
    private lateinit var dataJpaConcertRepository: DataJpaConcertRepository

    @Autowired
    private lateinit var dataJpaQueueTokenRepository: DataJpaQueueTokenRepository

    @Autowired
    private lateinit var dataJpaWaitingQueueRepository: DataJpaWaitingQueueRepository

    @Autowired
    private lateinit var dataJpaConcertScheduleRepository: DataJpaConcertScheduleRepository

    @Autowired
    private lateinit var dataJpaConcertSeatRepository: DataJpaConcertSeatRepository

    @Autowired
    private lateinit var dataJpaUserRepository: DataJpaUserRepository

    @Autowired
    private lateinit var dataJpaWalletRepository: DataJpaWalletRepository

    @Autowired
    private lateinit var dataJpaConcertReservationRepository: DataJpaConcertReservationRepository

    private val concertDate1 = LocalDateTime.now().plusDays(1)
    private val concertDate2 = LocalDateTime.now().plusDays(2)

    @BeforeEach
    fun setUp() {
        val user = dataJpaUserRepository.save(UserJpaEntity("user"))
        dataJpaWalletRepository.save(WalletJpaEntity(user.id, 30000))
        val queueToken = dataJpaQueueTokenRepository.save(QueueTokenJpaEntity(userId = user.id, token = "token"))
        dataJpaWaitingQueueRepository.save(
            WaitingQueueJpaEntity(
                queueToken.token,
                status = QueueStatus.ACTIVE,
                expiresAt = concertDate1,
            ),
        )
        val concert = dataJpaConcertRepository.save(ConcertJpaEntity("concert"))
        val concertSchedules =
            dataJpaConcertScheduleRepository.saveAll(
                listOf(
                    ConcertScheduleJpaEntity(
                        concertId = concert.id,
                        startTime = concertDate1,
                        endTime = concertDate1.plusHours(2),
                    ),
                    ConcertScheduleJpaEntity(
                        concertId = concert.id,
                        startTime = concertDate2,
                        endTime = concertDate2.plusHours(2),
                    ),
                ),
            )
        dataJpaConcertSeatRepository.saveAll(
            concertSchedules.flatMap { schedule ->
                (1..50).map {
                    ConcertSeatJpaEntity(
                        concertScheduleId = schedule.id,
                        name = "A1",
                        price = 10000,
                    )
                }
            },
        )
    }

    @AfterEach
    fun tearDown() {
        databaseCleanUp.execute()
    }

    @Nested
    @DisplayName("좌석 결제")
    inner class Pay {
        @Test
        @DisplayName("사용자가 예약한 좌석을 결제한다.")
        fun pay() {
            val reserveRequest = ReservationConcertSeatRequest(listOf(1L, 2L, 3L))
            concertUseCase.reserveSeats(1L, 1L, "token", reserveRequest)

            val paymentRequest = PaymentRequest(1L, 1L)
            paymentUseCase.pay("token", paymentRequest)

            val paymentHistory = dataJpaConcertReservationRepository.findAll()
            val wallet = dataJpaWalletRepository.findByUserId(1L)!!

            assertThat(paymentHistory.size).isEqualTo(1)
            assertThat(paymentHistory[0].totalPrice).isEqualTo(30000)
            assertThat(wallet.balance).isEqualTo(0)
        }

        @Test
        @DisplayName("여러 사용자가 결제를 시도하는 경우 한번만 결제된다.")
        fun payConcurrently() {
            val reserveRequest = ReservationConcertSeatRequest(listOf(1L, 2L, 3L))
            concertUseCase.reserveSeats(1L, 1L, "token", reserveRequest)

            val paymentRequest = PaymentRequest(1L, 1L)
            val threadCount = 50

            val results =
                ConcurrentTestHelper.executeAsyncTasks(
                    taskCount = threadCount,
                    task = { paymentUseCase.pay("token", paymentRequest) },
                )

            val successCount = results.count { it }
            val failCount = results.count { !it }

            assertThat(successCount).isEqualTo(1)
            assertThat(failCount).isEqualTo(threadCount - 1)
        }

        @Test
        @DisplayName("예약되지 않은 좌석을 결제 시도하는 경우 예외가 발생한다.")
        fun payWithExpiredSeat() {
            val paymentRequest = PaymentRequest(1L, 1L)

            assertThatThrownBy { paymentUseCase.pay("token", paymentRequest) }
                .isInstanceOf(BusinessException::class.java)
                .hasMessage(ErrorType.CONCERT_RESERVATION_NOT_FOUND.message)
        }

        @Test
        @DisplayName("예약 만료된 좌석을 결제 시도하는 경우 예외가 발생한다.")
        fun payWithExpiredReservation() {
            val reserveRequest = ReservationConcertSeatRequest(listOf(1L))
            concertUseCase.reserveSeats(1L, 1L, "token", reserveRequest)
            dataJpaConcertReservationRepository
                .findByIdOrNull(1L)!!
                .toDomain()
                .also { it.expire() }
                .toJpaEntity()
                .let { dataJpaConcertReservationRepository.save(it) }

            val paymentRequest = PaymentRequest(1L, 1L)

            assertThatThrownBy { paymentUseCase.pay("token", paymentRequest) }
                .isInstanceOf(BusinessException::class.java)
                .hasMessage(ErrorType.CONCERT_RESERVATION_NOT_FOUND.message)
        }
    }
}
