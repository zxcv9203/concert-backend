package org.example.concertbackend.infrastructure.persistence.queue.repository

import org.example.concertbackend.common.exception.BusinessException
import org.example.concertbackend.common.model.ErrorType
import org.example.concertbackend.domain.queue.QueueStatus
import org.example.concertbackend.domain.queue.WaitingQueue
import org.example.concertbackend.domain.queue.WaitingQueueManager
import org.example.concertbackend.infrastructure.persistence.queue.mapper.toDomain
import org.example.concertbackend.infrastructure.persistence.queue.mapper.toJpaEntity
import org.springframework.data.domain.PageRequest
import org.springframework.stereotype.Repository
import java.time.LocalDateTime

@Repository
class JpaWaitingQueueRepository(
    private val dataJpaWaitingQueueRepository: DataJpaWaitingQueueRepository,
) : WaitingQueueManager {
    override fun addToQueue(queueToken: WaitingQueue): WaitingQueue =
        dataJpaWaitingQueueRepository
            .save(queueToken.toJpaEntity())
            .toDomain()

    override fun findPosition(queueToken: String): Int =
        dataJpaWaitingQueueRepository
            .findAllByStatus(QueueStatus.WAITING)
            .indexOfFirst { it.token == queueToken }
            .takeIf { it >= 0 }
            ?.inc()
            ?: throw BusinessException(ErrorType.WAITING_QUEUE_TOKEN_NOT_FOUND)

    override fun findByToken(token: String): WaitingQueue? =
        dataJpaWaitingQueueRepository
            .findByToken(token)
            ?.toDomain()

    override fun findByStatus(
        status: QueueStatus,
        limit: Int,
    ): List<WaitingQueue> {
        val pageable = PageRequest.of(0, limit)
        return dataJpaWaitingQueueRepository
            .findAllByStatus(status, pageable)
            .map { it.toDomain() }
    }

    override fun update(queueToken: WaitingQueue) {
        dataJpaWaitingQueueRepository
            .save(queueToken.toJpaEntity())
    }

    override fun findExpiredTokens(now: LocalDateTime): List<WaitingQueue> {
        return dataJpaWaitingQueueRepository
            .findAllByStatusAndExpiresAtBefore(QueueStatus.ACTIVE, now)
            .map { it.toDomain() }
    }
}
