package org.example.concertbackend.api.response

data class RegisterQueueResponse(
    val token: String,
    val status: String,
    val waitingNumber: Int
)
