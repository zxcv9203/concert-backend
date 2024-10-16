package org.example.concertbackend.application.queue.scheduler

import org.example.concertbackend.common.util.TimeProvider
import org.example.concertbackend.domain.queue.QueueStatus
import org.example.concertbackend.domain.queue.WaitingQueueManager
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component

@Component
class WaitingQueueScheduler(
    private val waitingQueueManager: WaitingQueueManager,
    private val timeProvider: TimeProvider,
) {
    @Scheduled(fixedRate = 60000)
    fun activeWaitingTokens() {
        waitingQueueManager
            .findByStatus(QueueStatus.WAITING, 100)
            .forEach {
                it.active(timeProvider.now())
                waitingQueueManager.update(it)
            }
    }
}
