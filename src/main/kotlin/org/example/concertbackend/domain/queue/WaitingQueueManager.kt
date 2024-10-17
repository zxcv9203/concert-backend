package org.example.concertbackend.domain.queue

import java.time.LocalDateTime

interface WaitingQueueManager {
    fun addToQueue(queueToken: WaitingQueue): WaitingQueue

    fun findPosition(queueToken: String): Int

    fun findByToken(token: String): WaitingQueue?

    fun findByStatus(
        status: QueueStatus,
        limit: Int,
    ): List<WaitingQueue>

    fun update(queueToken: WaitingQueue)

    fun findExpiredTokens(now: LocalDateTime): List<WaitingQueue>
}
