package org.example.concertbackend.infrastructure.persistence.queue.repository

import org.example.concertbackend.common.exception.BusinessException
import org.example.concertbackend.common.model.ErrorType
import org.example.concertbackend.domain.queue.QueueStatus
import org.example.concertbackend.domain.queue.WaitingQueue
import org.example.concertbackend.domain.queue.WaitingQueueManager
import org.example.concertbackend.infrastructure.persistence.queue.mapper.toDomain
import org.example.concertbackend.infrastructure.persistence.queue.mapper.toJpaEntity
import org.springframework.stereotype.Repository

@Repository
class JpaWaitingQueueRepository(
    private val dataJpaWaitingQueueRepository: DataJpaWaitingQueueRepository,
) : WaitingQueueManager {
    override fun addToQueue(queue: WaitingQueue): WaitingQueue =
        dataJpaWaitingQueueRepository
            .save(queue.toJpaEntity())
            .toDomain()

    override fun findPosition(queueToken: String): Int =
        dataJpaWaitingQueueRepository
            .findAllByStatus(QueueStatus.WAITING)
            .indexOfFirst { it.token == queueToken }
            .takeIf { it >= 0 }
            ?.inc()
            ?: throw BusinessException(ErrorType.WAITING_QUEUE_TOKEN_NOT_FOUND)
}
