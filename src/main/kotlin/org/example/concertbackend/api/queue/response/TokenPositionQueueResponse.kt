package org.example.concertbackend.api.queue.response

data class TokenPositionQueueResponse(
    val token: String,
    val status: String,
    val waitingNumber: Int,
)
