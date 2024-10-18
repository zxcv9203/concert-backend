package org.example.concertbackend.domain.concert.reservation

import java.time.LocalDateTime

interface ConcertReservationRepository {
    fun save(concertReservation: ConcertReservation): ConcertReservation

    fun findExpiredReservations(now: LocalDateTime): List<ConcertReservation>

    fun update(it: ConcertReservation): ConcertReservation

    fun findByIdAndStatus(
        id: Long,
        status: ConcertReservationStatus,
    ): ConcertReservation?

    fun findById(id: Long): ConcertReservation?
}
