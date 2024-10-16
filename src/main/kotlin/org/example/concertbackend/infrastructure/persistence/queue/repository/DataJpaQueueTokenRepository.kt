package org.example.concertbackend.infrastructure.persistence.queue.repository

import org.example.concertbackend.infrastructure.persistence.queue.entity.QueueTokenJpaEntity
import org.springframework.data.jpa.repository.JpaRepository

interface DataJpaQueueTokenRepository : JpaRepository<QueueTokenJpaEntity, Long> {
    fun findByUserId(userId: Long): QueueTokenJpaEntity?
}
