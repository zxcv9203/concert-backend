package org.example.concertbackend.api.concert.response

data class ConcertSeatResponse(
    val id: Long,
    val seatNumber: String,
    val status: String,
    val price: Long,
)
