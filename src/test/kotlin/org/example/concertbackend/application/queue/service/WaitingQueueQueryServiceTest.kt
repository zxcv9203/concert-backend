package org.example.concertbackend.application.queue.service

import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.example.concertbackend.api.queue.response.TokenPositionQueueResponse
import org.example.concertbackend.common.exception.BusinessException
import org.example.concertbackend.common.model.ErrorType
import org.example.concertbackend.domain.queue.QueueStatus
import org.example.concertbackend.domain.queue.WaitingQueue
import org.example.concertbackend.domain.queue.WaitingQueueManager
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.extension.ExtendWith
import kotlin.test.Test

@ExtendWith(MockKExtension::class)
class WaitingQueueQueryServiceTest {
    @InjectMockKs
    private lateinit var waitingQueueQueryService: WaitingQueueQueryService

    @MockK
    private lateinit var waitingQueueManager: WaitingQueueManager

    @Nested
    @DisplayName("대기열에서 현재 위치 조회")
    inner class FindQueuePosition {
        private val token = "token"

        @Test
        @DisplayName("전달한 토큰이 존재하지 않는 경우 예외를 던진다.")
        fun notExistsToken() {
            every { waitingQueueManager.findByToken(token) } returns null

            assertThatThrownBy { waitingQueueQueryService.findQueuePosition(token) }
                .isInstanceOf(BusinessException::class.java)
                .hasMessage(ErrorType.WAITING_QUEUE_TOKEN_NOT_FOUND.message)
        }

        @Test
        @DisplayName("전달한 토큰이 대기 상태가 아니라면 대기번호 0을 반환한다.")
        fun notWaitingStatus() {
            val waitingQueueInToken = WaitingQueue(token, QueueStatus.ACTIVE)
            val want = TokenPositionQueueResponse(token, QueueStatus.ACTIVE, 0)
            every { waitingQueueManager.findByToken(token) } returns waitingQueueInToken

            val got = waitingQueueQueryService.findQueuePosition(token)

            assertThat(got).isEqualTo(want)
        }

        @Test
        @DisplayName("전달한 토큰이 대기 상태라면 대기번호를 반환한다.")
        fun waitingStatus() {
            val waitingQueueInToken = WaitingQueue(token)
            val position = 1
            val want = TokenPositionQueueResponse(token, QueueStatus.WAITING, position)

            every { waitingQueueManager.findByToken(token) } returns waitingQueueInToken
            every { waitingQueueManager.findPosition(token) } returns position

            val got = waitingQueueQueryService.findQueuePosition(token)

            assertThat(got).isEqualTo(want)
        }
    }
}
