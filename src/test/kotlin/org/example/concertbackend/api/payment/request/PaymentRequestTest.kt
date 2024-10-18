package org.example.concertbackend.api.payment.request

import org.assertj.core.api.Assertions.assertThatThrownBy
import org.example.concertbackend.common.exception.BusinessException
import org.example.concertbackend.common.model.ErrorType
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

class PaymentRequestTest {
    @Nested
    @DisplayName("객체 생성")
    inner class Init {
        @Test
        @DisplayName("유저 ID가 0 이하인 경우 예외 발생")
        fun userIdIsZeroOrNegative() {
            assertThatThrownBy { PaymentRequest(userId = 0, reservationId = 1) }
                .isInstanceOf(BusinessException::class.java)
                .hasMessage(ErrorType.USER_NOT_FOUND.message)
        }

        @Test
        @DisplayName("예약 ID가 0 이하인 경우 예외 발생")
        fun reservationIdIsZeroOrNegative() {
            assertThatThrownBy { PaymentRequest(userId = 1, reservationId = 0) }
                .isInstanceOf(BusinessException::class.java)
                .hasMessage(ErrorType.CONCERT_RESERVATION_NOT_FOUND.message)
        }
    }
}
