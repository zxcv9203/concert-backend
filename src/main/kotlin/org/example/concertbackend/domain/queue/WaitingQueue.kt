package org.example.concertbackend.domain.queue

import java.time.LocalDateTime

class WaitingQueue(
    val token: String,
    var status: QueueStatus = QueueStatus.WAITING,
    var expiresAt: LocalDateTime? = null,
    val id: Long = 0,
) {
    fun active(expiresAt: LocalDateTime) {
        this.status = QueueStatus.ACTIVE
        this.expiresAt = expiresAt.plusMinutes(5)
    }
}
