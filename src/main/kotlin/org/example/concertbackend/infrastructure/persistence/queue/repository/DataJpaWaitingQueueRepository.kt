package org.example.concertbackend.infrastructure.persistence.queue.repository

import org.example.concertbackend.domain.queue.QueueStatus
import org.example.concertbackend.infrastructure.persistence.queue.entity.WaitingQueueJpaEntity
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import java.time.LocalDateTime

interface DataJpaWaitingQueueRepository : JpaRepository<WaitingQueueJpaEntity, Long> {
    fun findAllByStatus(status: QueueStatus): List<WaitingQueueJpaEntity>

    fun findAllByStatus(
        status: QueueStatus,
        pageable: Pageable,
    ): List<WaitingQueueJpaEntity>

    fun findByToken(token: String): WaitingQueueJpaEntity?

    fun findAllByStatusAndExpiresAtBefore(
        active: QueueStatus,
        now: LocalDateTime,
    ): List<WaitingQueueJpaEntity>
}
