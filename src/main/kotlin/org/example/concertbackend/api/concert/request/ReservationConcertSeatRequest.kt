package org.example.concertbackend.api.concert.request

data class ReservationConcertSeatRequest(
    val seats: List<Long>,
)
