package org.example.concertbackend.api.queue.request

import org.assertj.core.api.Assertions.assertThatThrownBy
import org.example.concertbackend.common.exception.BusinessException
import org.example.concertbackend.common.model.ErrorType
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

class RegisterQueueRequestTest {
    @Nested
    @DisplayName("객체 생성")
    inner class Init {
        @Test
        @DisplayName("사용자 ID가 0 이하인 경우 예외를 던진다.")
        fun initWhenUserIdIsZeroOrNegative() {
            assertThatThrownBy { RegisterQueueRequest(0) }
                .isInstanceOf(BusinessException::class.java)
                .hasMessage(ErrorType.USER_NOT_FOUND.message)
        }
    }
}
