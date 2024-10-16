package org.example.concertbackend.infrastructure.persistence.queue.repository

import org.example.concertbackend.domain.queue.QueueStatus
import org.example.concertbackend.infrastructure.persistence.queue.entity.WaitingQueueJpaEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

interface DataJpaWaitingQueueRepository : JpaRepository<WaitingQueueJpaEntity, Long> {
    @Query(
        """
            SELECT wq
            FROM WaitingQueueJpaEntity wq 
            WHERE wq.status = :status
            ORDER BY wq.id ASC
        """,
    )
    fun findAllByStatus(status: QueueStatus): List<WaitingQueueJpaEntity>

    fun findByToken(token: String): WaitingQueueJpaEntity?
}
