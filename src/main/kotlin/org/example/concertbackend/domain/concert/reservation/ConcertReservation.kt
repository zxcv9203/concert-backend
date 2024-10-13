package org.example.concertbackend.domain.concert.reservation

import java.time.LocalDateTime

class ConcertReservation(
    val userId: Long,
    val totalPrice: Long,
    val status: ConcertReservationStatus = ConcertReservationStatus.PENDING,
    val expiresAt: LocalDateTime,
    val id: Long = 0,
)
