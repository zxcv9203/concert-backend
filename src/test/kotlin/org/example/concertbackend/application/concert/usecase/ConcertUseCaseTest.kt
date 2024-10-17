package org.example.concertbackend.application.concert.usecase

import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import org.assertj.core.api.Assertions.assertThat
import org.example.concertbackend.api.concert.response.ConcertScheduleResponse
import org.example.concertbackend.api.concert.response.ConcertScheduleResponses
import org.example.concertbackend.application.concert.service.ConcertQueryService
import org.example.concertbackend.application.concert.service.ConcertReservationCommandService
import org.example.concertbackend.application.concert.service.ConcertScheduleQueryService
import org.example.concertbackend.application.concert.service.ConcertSeatQueryService
import org.example.concertbackend.application.queue.service.QueueTokenQueryService
import org.example.concertbackend.domain.concert.Concert
import org.example.concertbackend.domain.concert.ConcertSchedule
import org.example.concertbackend.domain.queue.WaitingQueueManager
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import java.time.LocalDateTime

@ExtendWith(MockKExtension::class)
class ConcertUseCaseTest {
    @InjectMockKs
    private lateinit var concertUseCase: ConcertUseCase

    @MockK
    private lateinit var waitingQueueManager: WaitingQueueManager

    @MockK
    private lateinit var concertQueryService: ConcertQueryService

    @MockK
    private lateinit var concertScheduleQueryService: ConcertScheduleQueryService

    @MockK
    private lateinit var concertSeatQueryService: ConcertSeatQueryService

    @MockK
    private lateinit var concertReservationCommandService: ConcertReservationCommandService

    @MockK
    private lateinit var queueTokenQueryService: QueueTokenQueryService

    @Nested
    @DisplayName("콘서트 일정 조회")
    inner class FindSchedules {
        @Test
        @DisplayName("콘서트 ID를 조회하면 모든 콘서트 일정을 반환한다.")
        fun findSchedules() {
            val concertId = 1L
            val token = "token"
            val concert = Concert(id = concertId, name = "concert")
            val availableSeats = 50
            val date = LocalDateTime.now()
            val schedules =
                listOf(
                    ConcertSchedule(id = 1L, concertId = concertId, startTime = date, endTime = date.plusHours(2)),
                )
            val want =
                ConcertScheduleResponses(
                    listOf(
                        ConcertScheduleResponse(id = 1L, date = date, availableSeats = availableSeats),
                    ),
                )
            every { waitingQueueManager.checkActiveToken(any()) } returns Unit
            every { concertQueryService.getById(concertId) } returns concert
            every { concertScheduleQueryService.findAllByConcertId(concertId) } returns schedules
            every { concertSeatQueryService.countAvailableSeats(1L) } returns availableSeats

            val got = concertUseCase.findSchedules(concertId, token)

            assertThat(got).isEqualTo(want)
        }
    }
}
