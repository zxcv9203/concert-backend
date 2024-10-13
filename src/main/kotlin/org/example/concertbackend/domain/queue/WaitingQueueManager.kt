package org.example.concertbackend.domain.queue

interface WaitingQueueManager {
    fun addToQueue(queue: WaitingQueue): WaitingQueue

    fun findPosition(queueToken: String): Int
}
