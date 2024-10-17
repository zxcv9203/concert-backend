package org.example.concertbackend.application.queue.usecase

import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.example.concertbackend.api.queue.request.RegisterQueueRequest
import org.example.concertbackend.common.exception.BusinessException
import org.example.concertbackend.common.model.ErrorType
import org.example.concertbackend.domain.queue.QueueStatus
import org.example.concertbackend.helper.DatabaseCleanUp
import org.example.concertbackend.infrastructure.persistence.queue.entity.QueueTokenJpaEntity
import org.example.concertbackend.infrastructure.persistence.queue.entity.WaitingQueueJpaEntity
import org.example.concertbackend.infrastructure.persistence.queue.repository.DataJpaQueueTokenRepository
import org.example.concertbackend.infrastructure.persistence.queue.repository.DataJpaWaitingQueueRepository
import org.example.concertbackend.infrastructure.persistence.user.entity.UserJpaEntity
import org.example.concertbackend.infrastructure.persistence.user.repository.DataJpaUserRepository
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class IntegrationWaitingQueueUseCaseTest {
    @Autowired
    private lateinit var waitingQueueUseCase: WaitingQueueUseCase

    @Autowired
    private lateinit var dataJpaQueueTokenRepository: DataJpaQueueTokenRepository

    @Autowired
    private lateinit var dataJpaUserRepository: DataJpaUserRepository

    @Autowired
    private lateinit var dataJpaWaitingQueueRepository: DataJpaWaitingQueueRepository

    @Autowired
    private lateinit var databaseCleanUp: DatabaseCleanUp

    @AfterEach
    fun setUp() {
        databaseCleanUp.execute()
    }

    @Nested
    @DisplayName("대기열 등록")
    inner class RegisterQueue {
        private lateinit var user: UserJpaEntity
        private lateinit var queueToken: QueueTokenJpaEntity

        @BeforeEach
        fun setUp() {
            user = dataJpaUserRepository.save(UserJpaEntity("user"))
            queueToken = dataJpaQueueTokenRepository.save(QueueTokenJpaEntity(userId = user.id, token = "token"))
            dataJpaWaitingQueueRepository.save(
                WaitingQueueJpaEntity(
                    queueToken.token,
                    status = QueueStatus.WAITING,
                    expiresAt = null,
                ),
            )
        }

        @Test
        @DisplayName("이미 등록한 사용자의 경우 기존에 발급된 토큰을 반환한다.")
        fun alreadyRegisteredUser() {
            val request = RegisterQueueRequest(user.id)

            val got = waitingQueueUseCase.addToQueue(request)

            assertThat(got.token).isEqualTo(queueToken.token)
            assertThat(dataJpaWaitingQueueRepository.count()).isEqualTo(1)
        }

        @Test
        @DisplayName("처음 등록하는 사용자의 경우 새로운 토큰을 발급한다.")
        fun firstTimeRegisterUser() {
            val user = dataJpaUserRepository.save(UserJpaEntity("user"))
            val request = RegisterQueueRequest(user.id)

            val got = waitingQueueUseCase.addToQueue(request)

            assertThat(got.token).isNotBlank
            assertThat(dataJpaWaitingQueueRepository.count()).isEqualTo(2)
        }

        @Test
        @DisplayName("사용자가 없는 경우 예외를 발생시킨다.")
        fun userNotFound() {
            val request = RegisterQueueRequest(999999)

            assertThatThrownBy { waitingQueueUseCase.addToQueue(request) }
                .isInstanceOf(BusinessException::class.java)
                .hasMessage(ErrorType.USER_NOT_FOUND.message)
        }
    }
}
