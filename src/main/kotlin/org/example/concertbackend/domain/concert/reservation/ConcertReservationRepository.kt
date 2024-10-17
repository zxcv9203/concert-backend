package org.example.concertbackend.domain.concert.reservation

interface ConcertReservationRepository {
    fun save(concertReservation: ConcertReservation): ConcertReservation
}
