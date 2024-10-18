package org.example.concertbackend.application.concert.usecase

import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.example.concertbackend.common.exception.BusinessException
import org.example.concertbackend.common.model.ErrorType
import org.example.concertbackend.domain.queue.QueueStatus
import org.example.concertbackend.helper.DatabaseCleanUp
import org.example.concertbackend.infrastructure.persistence.concert.entity.ConcertJpaEntity
import org.example.concertbackend.infrastructure.persistence.concert.entity.ConcertScheduleJpaEntity
import org.example.concertbackend.infrastructure.persistence.concert.entity.ConcertSeatJpaEntity
import org.example.concertbackend.infrastructure.persistence.concert.repository.DataJpaConcertRepository
import org.example.concertbackend.infrastructure.persistence.concert.repository.DataJpaConcertScheduleRepository
import org.example.concertbackend.infrastructure.persistence.concert.repository.DataJpaConcertSeatRepository
import org.example.concertbackend.infrastructure.persistence.queue.entity.QueueTokenJpaEntity
import org.example.concertbackend.infrastructure.persistence.queue.entity.WaitingQueueJpaEntity
import org.example.concertbackend.infrastructure.persistence.queue.repository.DataJpaQueueTokenRepository
import org.example.concertbackend.infrastructure.persistence.queue.repository.DataJpaWaitingQueueRepository
import org.example.concertbackend.infrastructure.persistence.user.entity.UserJpaEntity
import org.example.concertbackend.infrastructure.persistence.user.repository.DataJpaUserRepository
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import java.time.LocalDateTime
import kotlin.test.Test

@SpringBootTest
class IntegrationConcertUseCaseTest {
    @Autowired
    private lateinit var concertUseCase: ConcertUseCase

    @Autowired
    private lateinit var dataJpaConcertRepository: DataJpaConcertRepository

    @Autowired
    private lateinit var dataJpaConcertScheduleRepository: DataJpaConcertScheduleRepository

    @Autowired
    private lateinit var dataJpaConcertSeatRepository: DataJpaConcertSeatRepository

    @Autowired
    private lateinit var dataJpaWaitingQueueRepository: DataJpaWaitingQueueRepository

    @Autowired
    private lateinit var dataJpaUserRepository: DataJpaUserRepository

    @Autowired
    private lateinit var dataJpaQueueTokenRepository: DataJpaQueueTokenRepository


    @Autowired
    private lateinit var databaseCleanUp: DatabaseCleanUp

    private val concertDate1 = LocalDateTime.now().plusDays(1)
    private val concertDate2 = LocalDateTime.now().plusDays(2)
    private lateinit var concert: ConcertJpaEntity

    @BeforeEach
    fun setUp() {
        val user = dataJpaUserRepository.save(UserJpaEntity("user"))
        val queueToken = dataJpaQueueTokenRepository.save(QueueTokenJpaEntity(userId = user.id, token = "token"))
        dataJpaWaitingQueueRepository.save(
            WaitingQueueJpaEntity(
                queueToken.token,
                status = QueueStatus.ACTIVE,
                expiresAt = concertDate1,
            ),
        )
        concert = dataJpaConcertRepository.save(ConcertJpaEntity("concert"))
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
    @DisplayName("콘서트 일정 조회")
    inner class FindSchedules {


        @Test
        @DisplayName("콘서트 ID를 전달하면 해당 콘서트의 일정을 반환한다.")
        fun findSchedules() {
            val concertId = 1L

            val got = concertUseCase.findSchedules(concertId, "token")

            assertThat(got.schedules)
                .extracting("date")
                .contains(concertDate1, concertDate2)
        }

        @Test
        @DisplayName("전달된 대기열 토큰이 유효하지 않으면 예외를 던진다.")
        fun invalidToken() {
            val concertId = 1L

            assertThatThrownBy { concertUseCase.findSchedules(concertId, "invalidToken") }
                .isInstanceOf(BusinessException::class.java)
                .hasMessage(ErrorType.NOT_ACTIVE_TOKEN.message)
        }

        @Test
        @DisplayName("존재하지 않는 콘서트 ID를 전달하면 예외를 던진다.")
        fun notFoundConcert() {
            val concertId = 999999L

            assertThatThrownBy { concertUseCase.findSchedules(concertId, "token") }
                .isInstanceOf(BusinessException::class.java)
                .hasMessage(ErrorType.CONCERT_NOT_FOUND.message)
        }
    }

    @Nested
    @DisplayName("콘서트 좌석 조회")
    inner class FindSeats {
        @Test
        @DisplayName("해당하는 일자의 콘서트 좌석을 조회한다.")
        fun findSeats() {
            val concertId = 1L
            val concertScheduleId = 1L
            val ids = (1L..50L).toList()
            val token = "token"

            val got = concertUseCase.findSeats(concertId, concertScheduleId, token)

            assertThat(got.seats)
                .extracting("id")
                .containsExactlyElementsOf(ids)
        }

        @Test
        @DisplayName("전달된 대기열 토큰이 유효하지 않으면 예외를 던진다.")
        fun invalidToken() {
            val concertId = 1L
            val concertScheduleId = 1L
            val invalidToken = "invalidToken"

            assertThatThrownBy { concertUseCase.findSeats(concertId, concertScheduleId, invalidToken) }
                .isInstanceOf(BusinessException::class.java)
                .hasMessage(ErrorType.NOT_ACTIVE_TOKEN.message)
        }

        @Test
        @DisplayName("전달된 콘서트 ID가 존재하지 않으면 예외를 던진다.")
        fun notFoundConcert() {
            val concertId = 9999L
            val concertScheduleId = 1L
            val token = "token"

            assertThatThrownBy { concertUseCase.findSeats(concertId, concertScheduleId, token) }
                .isInstanceOf(BusinessException::class.java)
                .hasMessage(ErrorType.CONCERT_NOT_FOUND.message)
        }

        @Test
        @DisplayName("전달된 콘서트 스케줄 ID가 존재하지 않으면 예외를 던진다.")
        fun notFoundConcertSchedule() {
            val concertId = 1L
            val concertScheduleId = 9999L
            val token = "token"

            assertThatThrownBy { concertUseCase.findSeats(concertId, concertScheduleId, token) }
                .isInstanceOf(BusinessException::class.java)
                .hasMessage(ErrorType.CONCERT_SCHEDULE_NOT_FOUND.message)
        }
    }

    @Nested
    @DisplayName("콘서트 좌석 예약")
    inner class ReserveSeats {

        @Test
        @DisplayName("좌석 아이디 리스트가 주어지면 해당 하는 좌석들을 예약한다.")
        fun reserveSeats() {
            val concertId = 1L
            val concertScheduleId = 1L
            val token = "token"
        }

        @Test
        @DisplayName("동시에 여러 사용자가 좌석 예약을 시도하는 경우 한명만 좌석 예약에 성공한다.")
        fun concurrencyReserveSeats() {

        }
        @Test
        @DisplayName("전달된 대기열 토큰이 유효하지 않으면 예외를 던진다.")
        fun invalidToken() {

        }

        @Test
        @DisplayName("전달된 콘서트 ID가 존재하지 않으면 예외를 던진다.")
        fun notFoundConcert() {}

        @Test
        @DisplayName("전달된 콘서트 스케줄 ID가 존재하지 않으면 예외를 던진다.")
        fun notFoundConcertSchedule() {}

        @Test
        @DisplayName("대기열 토큰 정보가 존재하지 않는 경우 예외를 던진다.")
        fun notFoundToken() {

        }

        @Test
        @DisplayName("예약 요청 Id리스트와 예약 가능한 좌석 Id 리스트의 수가 다르면 예외를 던진다.")
        fun compareSeat() {

        }
    }

}
