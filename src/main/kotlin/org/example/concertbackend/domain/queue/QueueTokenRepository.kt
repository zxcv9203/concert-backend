package org.example.concertbackend.domain.queue

interface QueueTokenRepository {
    fun save(queueToken: QueueToken): QueueToken

    fun findByUserIdOrNull(userId: Long): QueueToken?
}
