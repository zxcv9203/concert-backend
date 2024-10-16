package org.example.concertbackend.application.queue.scheduler

import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import io.mockk.just
import io.mockk.runs
import io.mockk.verify
import org.assertj.core.api.Assertions.assertThat
import org.example.concertbackend.common.util.TimeProvider
import org.example.concertbackend.domain.queue.QueueStatus
import org.example.concertbackend.domain.queue.WaitingQueue
import org.example.concertbackend.domain.queue.WaitingQueueManager
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import java.time.LocalDateTime

@ExtendWith(MockKExtension::class)
class WaitingQueueSchedulerTest {
    @InjectMockKs
    private lateinit var waitingQueueScheduler: WaitingQueueScheduler

    @MockK
    private lateinit var waitingQueueManager: WaitingQueueManager

    @MockK
    private lateinit var timeProvider: TimeProvider

    @Nested
    @DisplayName("대기열 토큰 활성화")
    inner class ActiveWaitingTokens {
        @Test
        @DisplayName("대기열 토큰 리스트를 조회하고 활성 상태로 변경한다.")
        fun activeWaitingTokens() {
            val waitingQueue = WaitingQueue("token")
            val waitingQueueList = listOf(waitingQueue)
            val now = LocalDateTime.now()

            every { waitingQueueManager.findByStatus(any(), any()) } returns waitingQueueList
            every { timeProvider.now() } returns now
            every { waitingQueueManager.update(waitingQueue) } just runs

            waitingQueueScheduler.activeWaitingTokens()

            assertThat(waitingQueue.status).isEqualTo(QueueStatus.ACTIVE)
            verify { waitingQueueManager.update(waitingQueue) }
        }
    }
}
