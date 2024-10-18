package org.example.concertbackend.application.concert.scheduler

import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import io.mockk.mockk
import org.assertj.core.api.Assertions.assertThat
import org.example.concertbackend.common.util.TimeProvider
import org.example.concertbackend.domain.concert.ConcertSeat
import org.example.concertbackend.domain.concert.ConcertSeatRepository
import org.example.concertbackend.domain.concert.ConcertSeatStatus
import org.example.concertbackend.domain.concert.reservation.ConcertReservation
import org.example.concertbackend.domain.concert.reservation.ConcertReservationItem
import org.example.concertbackend.domain.concert.reservation.ConcertReservationItemRepository
import org.example.concertbackend.domain.concert.reservation.ConcertReservationRepository
import org.example.concertbackend.domain.concert.reservation.ConcertReservationStatus
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.extension.ExtendWith
import java.time.LocalDateTime
import kotlin.test.Test

@ExtendWith(MockKExtension::class)
class ConcertReservationSchedulerTest {
    @InjectMockKs
    private lateinit var concertReservationScheduler: ConcertReservationScheduler

    @MockK
    private lateinit var timeProvider: TimeProvider

    @MockK
    private lateinit var concertReservationRepository: ConcertReservationRepository

    @MockK
    private lateinit var concertReservationItemRepository: ConcertReservationItemRepository

    @MockK
    private lateinit var concertSeatRepository: ConcertSeatRepository

    @Nested
    @DisplayName("만료 시간에 도달한 경우 예약을 취소 처리하고 좌석을 예약 가능한 상태로 변경")
    inner class ExpireReservations {
        @Test
        @DisplayName("만료된 예약을 처리한다.")
        fun expireReservations() {
            val now = LocalDateTime.now()
            val expiredReservations =
                listOf(
                    ConcertReservation(id = 1L, userId = 1L, totalPrice = 100, expiresAt = now.minusMinutes(10)),
                    ConcertReservation(id = 2L, userId = 2L, totalPrice = 200, expiresAt = now.minusMinutes(20)),
                )
            val reservationItems1 =
                listOf(
                    ConcertReservationItem(concertSeatId = 101L, concertReservationId = 1L),
                )
            val reservationItems2 =
                listOf(
                    ConcertReservationItem(concertSeatId = 102L, concertReservationId = 2L),
                )
            val seat1 =
                ConcertSeat(
                    id = 101L,
                    concertScheduleId = 1L,
                    name = "A1",
                    price = 100,
                    status = ConcertSeatStatus.RESERVED_TEMPORARY,
                )
            val seat2 =
                ConcertSeat(
                    id = 102L,
                    concertScheduleId = 1L,
                    name = "A2",
                    price = 200,
                    status = ConcertSeatStatus.RESERVED_TEMPORARY,
                )

            every { timeProvider.now() } returns now
            every { concertReservationRepository.findExpiredReservations(now) } returns expiredReservations
            every { concertReservationRepository.update(any()) } returns mockk<ConcertReservation>()
            every { concertReservationItemRepository.findByReservationId(1L) } returns reservationItems1
            every { concertReservationItemRepository.findByReservationId(2L) } returns reservationItems2
            every { concertSeatRepository.findById(101L) } returns seat1
            every { concertSeatRepository.findById(102L) } returns seat2
            every { concertSeatRepository.update(any()) } returns mockk<ConcertSeat>()

            concertReservationScheduler.expireReservations()

            assertThat(seat1.status).isEqualTo(ConcertSeatStatus.AVAILABLE)
            assertThat(seat2.status).isEqualTo(ConcertSeatStatus.AVAILABLE)
            assertThat(expiredReservations[0].status).isEqualTo(ConcertReservationStatus.CANCEL)
            assertThat(expiredReservations[1].status).isEqualTo(ConcertReservationStatus.CANCEL)
        }
    }
}
