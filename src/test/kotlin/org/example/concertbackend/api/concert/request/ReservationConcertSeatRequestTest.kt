package org.example.concertbackend.api.concert.request

import org.assertj.core.api.Assertions.assertThatThrownBy
import org.example.concertbackend.common.exception.BusinessException
import org.example.concertbackend.common.model.ErrorType
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

class ReservationConcertSeatRequestTest {
    @Nested
    @DisplayName("객체 초기화")
    inner class Init {
        @Test
        @DisplayName("예약할 좌석이 없을 때 예외 발생")
        fun emptySeats() {
            assertThatThrownBy { ReservationConcertSeatRequest(emptyList()) }
                .isInstanceOf(BusinessException::class.java)
                .hasMessageContaining(ErrorType.INVALID_REQUEST.message)
        }

        @Test
        @DisplayName("예약할 좌석이 음수일 때 예외 발생")
        fun negativeSeats() {
            assertThatThrownBy { ReservationConcertSeatRequest(listOf(-1L)) }
                .isInstanceOf(BusinessException::class.java)
                .hasMessageContaining(ErrorType.INVALID_REQUEST.message)
        }
    }
}
