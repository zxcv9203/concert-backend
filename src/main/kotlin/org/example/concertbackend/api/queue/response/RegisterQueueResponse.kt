package org.example.concertbackend.api.queue.response

data class RegisterQueueResponse(
    val token: String,
    val status: String,
    val waitingNumber: Int
)
