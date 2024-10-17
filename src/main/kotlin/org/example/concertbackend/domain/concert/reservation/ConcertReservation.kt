package org.example.concertbackend.domain.concert.reservation

import java.time.LocalDateTime

class ConcertReservation(
    val userId: Long,
    val totalPrice: Long,
    val expiresAt: LocalDateTime,
    var status: ConcertReservationStatus = ConcertReservationStatus.PENDING,
    val id: Long = 0,
) {
    fun expire() {
        status = ConcertReservationStatus.CANCEL
    }
}
