package org.example.concertbackend.domain.concert.reservation

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import java.time.LocalDateTime
import kotlin.test.Test

class ConcertReservationTest {
    @Nested
    @DisplayName("예약 만료")
    inner class Expire {
        @Test
        @DisplayName("만료시, 상태가 취소로 변경된다.")
        fun expire() {
            val concertReservation =
                ConcertReservation(
                    userId = 1,
                    totalPrice = 10000,
                    expiresAt = LocalDateTime.now().minusMinutes(1),
                )

            concertReservation.expire()

            assertEquals(ConcertReservationStatus.CANCEL, concertReservation.status)
        }
    }
}
