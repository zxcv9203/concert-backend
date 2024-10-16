package org.example.concertbackend.api.queue.response

import org.example.concertbackend.domain.queue.QueueStatus

data class TokenPositionQueueResponse(
    val token: String,
    val status: QueueStatus,
    val waitingNumber: Int,
)
