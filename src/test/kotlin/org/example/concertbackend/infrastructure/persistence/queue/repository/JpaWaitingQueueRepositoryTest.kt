package org.example.concertbackend.infrastructure.persistence.queue.repository

import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.example.concertbackend.common.exception.BusinessException
import org.example.concertbackend.common.model.ErrorType
import org.example.concertbackend.domain.queue.QueueStatus
import org.example.concertbackend.infrastructure.persistence.queue.entity.WaitingQueueJpaEntity
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.extension.ExtendWith
import kotlin.test.Test

@ExtendWith(MockKExtension::class)
class JpaWaitingQueueRepositoryTest {
    @InjectMockKs
    private lateinit var jpaWaitingQueueRepository: JpaWaitingQueueRepository

    @MockK
    private lateinit var dataJpaWaitingQueueRepository: DataJpaWaitingQueueRepository

    @Nested
    @DisplayName("전달한 토큰의 현재 위치 조회")
    inner class FindPosition {
        private val token = "myToken"

        private val data =
            listOf(
                WaitingQueueJpaEntity(token = token, status = QueueStatus.WAITING, expiresAt = null),
                WaitingQueueJpaEntity(token = "test", status = QueueStatus.WAITING, expiresAt = null),
            )

        @Test
        @DisplayName("토큰이 존재하는 경우 위치를 반환한다.")
        fun tokenExists() {
            val want = 1

            every { dataJpaWaitingQueueRepository.findAllByStatus(QueueStatus.WAITING) } returns data

            val got = jpaWaitingQueueRepository.findPosition(token)

            assertThat(got).isEqualTo(want)
        }

        @Test
        @DisplayName("토큰이 존재하지 않는 경우 예외를 발생시킵니다.")
        fun tokenNotExists() {
            every { dataJpaWaitingQueueRepository.findAllByStatus(QueueStatus.WAITING) } returns data

            assertThatThrownBy { jpaWaitingQueueRepository.findPosition("notExists") }
                .isInstanceOf(BusinessException::class.java)
                .hasMessage(ErrorType.WAITING_QUEUE_TOKEN_NOT_FOUND.message)
        }
    }
}
