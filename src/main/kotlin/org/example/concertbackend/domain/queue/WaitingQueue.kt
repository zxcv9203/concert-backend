package org.example.concertbackend.domain.queue

class WaitingQueue(
    val token: String,
    val status: QueueStatus = QueueStatus.WAITING,
    val id: Long = 0,
)
