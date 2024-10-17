package org.example.concertbackend.application.concert.service

import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import io.mockk.mockk
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.example.concertbackend.common.exception.BusinessException
import org.example.concertbackend.common.model.ErrorType
import org.example.concertbackend.common.util.TimeProvider
import org.example.concertbackend.domain.concert.ConcertSeat
import org.example.concertbackend.domain.concert.ConcertSeatRepository
import org.example.concertbackend.domain.concert.reservation.ConcertReservation
import org.example.concertbackend.domain.concert.reservation.ConcertReservationItem
import org.example.concertbackend.domain.concert.reservation.ConcertReservationItemRepository
import org.example.concertbackend.domain.concert.reservation.ConcertReservationRepository
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import java.time.LocalDateTime

@ExtendWith(MockKExtension::class)
class ConcertReservationCommandServiceTest {
    @InjectMockKs
    private lateinit var concertReservationCommandService: ConcertReservationCommandService

    @MockK
    private lateinit var timeProvider: TimeProvider

    @MockK
    private lateinit var concertSeatRepository: ConcertSeatRepository

    @MockK
    private lateinit var concertReservationRepository: ConcertReservationRepository

    @MockK
    private lateinit var concertReservationItemRepository: ConcertReservationItemRepository

    @Nested
    @DisplayName("좌석 예약")
    inner class ReserveSeats {
        @Test
        @DisplayName("값이 정상적으로 주어졌을 때 좌석 예약에 성공한다.")
        fun success() {
            val scheduleId = 1L
            val seatIds = listOf(1L, 2L)
            val userId = 1L
            val concertSeat1 =
                ConcertSeat(
                    id = 1L,
                    concertScheduleId = 1L,
                    name = "1A",
                    price = 1000,
                )
            val concertSeat2 =
                ConcertSeat(
                    id = 1L,
                    concertScheduleId = 1L,
                    name = "2A",
                    price = 1000,
                )
            val concertSeats = listOf(concertSeat1, concertSeat2)
            val now = LocalDateTime.now()
            val want = ConcertReservation(1L, 2000, now.plusMinutes(5))

            every { concertSeatRepository.findByScheduleIdAndIdsWithLock(scheduleId, seatIds) } returns concertSeats
            every { timeProvider.now() } returns now
            every { concertReservationRepository.save(any()) } returns want
            every { concertReservationItemRepository.save(any()) } returns mockk<ConcertReservationItem>()
            every { concertSeatRepository.update(any()) } returns mockk<ConcertSeat>()

            val got = concertReservationCommandService.reserveSeats(scheduleId, seatIds, userId)

            assertThat(got).isEqualTo(want)
        }

        @Test
        @DisplayName("이미 예약된 좌석이 포함되어 있을 때 좌석 예약에 실패한다.")
        fun alreadyReserved() {
            val scheduleId = 1L
            val seatIds = listOf(1L, 2L)
            val userId = 1L
            val concertSeat1 =
                ConcertSeat(
                    id = 1L,
                    concertScheduleId = 1L,
                    name = "1A",
                    price = 1000,
                )

            every { concertSeatRepository.findByScheduleIdAndIdsWithLock(scheduleId, seatIds) } returns
                listOf(
                    concertSeat1,
                )
            every { timeProvider.now() } returns LocalDateTime.now()

            assertThatThrownBy({ concertReservationCommandService.reserveSeats(scheduleId, seatIds, userId) })
                .isInstanceOf(BusinessException::class.java)
                .hasMessage(ErrorType.ALREADY_RESERVED.message)
        }
    }
}
