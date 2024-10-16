package org.example.concertbackend.domain.queue

interface QueueTokenRepository {
    fun save(queueToken: QueueToken): QueueToken

    fun findByUserId(userId: Long): QueueToken?

    fun findByToken(token: String): QueueToken?
}
