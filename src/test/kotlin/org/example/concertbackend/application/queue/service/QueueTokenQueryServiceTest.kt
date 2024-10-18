package org.example.concertbackend.application.queue.service

import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.example.concertbackend.common.exception.BusinessException
import org.example.concertbackend.common.model.ErrorType
import org.example.concertbackend.domain.queue.QueueTokenRepository
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(MockKExtension::class)
class QueueTokenQueryServiceTest {
    @InjectMockKs
    private lateinit var queueTokenQueryService: QueueTokenQueryService

    @MockK
    private lateinit var queueTokenRepository: QueueTokenRepository

    @Nested
    @DisplayName("토큰을 통해 대기열 토큰 조회")
    inner class GetByToken {
        @Test
        @DisplayName("토큰이 존재하지 않는 경우 예외를 던진다.")
        fun notExistsToken() {
            val invalidToken = "invalidToken"

            every { queueTokenRepository.findByToken(invalidToken) } returns null

            assertThatThrownBy { queueTokenQueryService.getByToken(invalidToken) }
                .isInstanceOf(BusinessException::class.java)
                .hasMessage(ErrorType.WAITING_QUEUE_TOKEN_NOT_FOUND.message)
        }
    }
}
