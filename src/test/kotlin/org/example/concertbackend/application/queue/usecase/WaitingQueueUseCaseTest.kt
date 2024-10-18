package org.example.concertbackend.application.queue.usecase

import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import org.assertj.core.api.Assertions.assertThat
import org.example.concertbackend.api.queue.request.RegisterQueueRequest
import org.example.concertbackend.api.queue.response.RegisterQueueResponse
import org.example.concertbackend.application.queue.service.QueueTokenCommandService
import org.example.concertbackend.application.queue.service.QueueTokenQueryService
import org.example.concertbackend.application.queue.service.WaitingQueueCommandService
import org.example.concertbackend.application.queue.service.WaitingQueueQueryService
import org.example.concertbackend.application.user.service.UserQueryService
import org.example.concertbackend.domain.queue.QueueToken
import org.example.concertbackend.domain.queue.WaitingQueue
import org.example.concertbackend.domain.user.User
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(MockKExtension::class)
class WaitingQueueUseCaseTest {
    @InjectMockKs
    private lateinit var waitingQueueUseCase: WaitingQueueUseCase

    @MockK
    private lateinit var userQueryService: UserQueryService

    @MockK
    private lateinit var waitingQueueCommandService: WaitingQueueCommandService

    @MockK
    private lateinit var queueTokenCommandService: QueueTokenCommandService

    @MockK
    private lateinit var queueTokenQueryService: QueueTokenQueryService

    @MockK
    private lateinit var waitingQueueQueryService: WaitingQueueQueryService

    @Nested
    @DisplayName("대기열 큐 등록")
    inner class AddToQueue {
        private val token = "token"
        private val request = RegisterQueueRequest(userId = 1)
        private val user = User(id = request.userId, name = "test")
        private val waitingQueueInToken = QueueToken(token = token, userId = request.userId)
        private val want = RegisterQueueResponse(token)

        @Test
        @DisplayName("대기열에 이미 등록된 유저인 경우 대기열에 등록하지 않고 등록된 토큰을 반환한다.")
        fun addToQueueWhenQueueTokenExists() {
            every { userQueryService.getById(request.userId) } returns user
            every { queueTokenQueryService.findByUserId(request.userId) } returns waitingQueueInToken

            val got = waitingQueueUseCase.addToQueue(request)

            assertThat(got).isEqualTo(want)
        }

        @Test
        @DisplayName("대기열에 등록되지 않은 유저의 경우 대기열 큐에 토큰 생성 후 등록하고 토큰을 반환한다.")
        fun addToQueueWhenQueueTokenNotExists() {
            val registerToken = WaitingQueue(token)
            every { userQueryService.getById(request.userId) } returns user
            every { queueTokenQueryService.findByUserId(request.userId) } returns null
            every { queueTokenCommandService.create(request.userId) } returns waitingQueueInToken
            every { waitingQueueCommandService.addToQueue(token) } returns registerToken

            val got = waitingQueueUseCase.addToQueue(request)

            assertThat(got).isEqualTo(want)
        }
    }
}
