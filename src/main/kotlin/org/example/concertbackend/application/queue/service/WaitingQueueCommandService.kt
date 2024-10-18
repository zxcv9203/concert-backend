package org.example.concertbackend.application.queue.service

import org.example.concertbackend.domain.queue.WaitingQueue
import org.example.concertbackend.domain.queue.WaitingQueueManager
import org.springframework.stereotype.Service

@Service
class WaitingQueueCommandService(
    private val waitingQueueManager: WaitingQueueManager,
) {
    fun addToQueue(queueToken: String): WaitingQueue {
        val queueInToken = WaitingQueue(queueToken)
        return waitingQueueManager.addToQueue(queueInToken)
    }
}
