package org.example.concertbackend.domain.queue

interface WaitingQueueManager {
    fun addToQueue(queueToken: WaitingQueue): WaitingQueue

    fun findPosition(queueToken: String): Int

    fun findByToken(token: String): WaitingQueue?

    fun findByStatus(
        status: QueueStatus,
        limit: Int,
    ): List<WaitingQueue>

    fun update(queueToken: WaitingQueue)
}
