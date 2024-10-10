package org.example.concertbackend.api.response

data class TokenPositionQueueResponse(
    val token: String,
    val status: String,
    val waitingNumber: Int,
)
