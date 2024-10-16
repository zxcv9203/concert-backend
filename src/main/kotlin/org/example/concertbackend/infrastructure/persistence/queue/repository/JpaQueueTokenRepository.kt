package org.example.concertbackend.infrastructure.persistence.queue.repository

import org.example.concertbackend.domain.queue.QueueToken
import org.example.concertbackend.domain.queue.QueueTokenRepository
import org.example.concertbackend.infrastructure.persistence.queue.mapper.toDomain
import org.example.concertbackend.infrastructure.persistence.queue.mapper.toJpaEntity
import org.springframework.stereotype.Repository

@Repository
class JpaQueueTokenRepository(
    private val dataJpaQueueTokenRepository: DataJpaQueueTokenRepository,
) : QueueTokenRepository {
    override fun save(queueToken: QueueToken): QueueToken =
        dataJpaQueueTokenRepository
            .save(queueToken.toJpaEntity())
            .toDomain()

    override fun findByUserIdOrNull(userId: Long): QueueToken? {
        return dataJpaQueueTokenRepository
            .findByUserId(userId)
            ?.toDomain()
    }
}
