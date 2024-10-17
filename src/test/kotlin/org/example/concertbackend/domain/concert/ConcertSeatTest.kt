package org.example.concertbackend.domain.concert

import io.mockk.junit5.MockKExtension
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.extension.ExtendWith
import kotlin.test.Test

@ExtendWith(MockKExtension::class)
class ConcertSeatTest {
    @Nested
    @DisplayName("좌석 예약 테스트")
    inner class ReserveSeatTest {
        @Test
        @DisplayName("좌석 상태를 RESERVED_TEMPORARY로 변경한다.")
        fun reserveSeat() {
            // Given
            val seat =
                ConcertSeat(
                    concertScheduleId = 1L,
                    name = "A1",
                    price = 100,
                    status = ConcertSeatStatus.AVAILABLE,
                )

            // When
            seat.reserve()

            // Then
            assertThat(seat.status).isEqualTo(ConcertSeatStatus.RESERVED_TEMPORARY)
        }
    }

    @Nested
    @DisplayName("좌석 예약 취소 테스트")
    inner class CancelSeatReservationTest {
        @Test
        @DisplayName("좌석 예약을 취소하고 상태를 AVAILABLE로 변경한다.")
        fun cancelSeatReservation() {
            // Given
            val seat =
                ConcertSeat(
                    concertScheduleId = 1L,
                    name = "A1",
                    price = 100,
                    status = ConcertSeatStatus.RESERVED_TEMPORARY,
                )

            // When
            seat.cancelReservation()

            // Then
            assertThat(seat.status).isEqualTo(ConcertSeatStatus.AVAILABLE)
        }
    }
}
