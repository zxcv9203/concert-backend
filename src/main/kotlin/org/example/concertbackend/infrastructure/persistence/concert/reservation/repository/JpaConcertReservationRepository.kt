package org.example.concertbackend.infrastructure.persistence.concert.reservation.repository

import org.example.concertbackend.domain.concert.reservation.ConcertReservation
import org.example.concertbackend.domain.concert.reservation.ConcertReservationRepository
import org.example.concertbackend.infrastructure.persistence.concert.reservation.mapper.toDomain
import org.example.concertbackend.infrastructure.persistence.concert.reservation.mapper.toJpaEntity
import org.springframework.stereotype.Repository

@Repository
class JpaConcertReservationRepository(
    private val dataJpaConcertReservationRepository: DataJpaConcertReservationRepository,
) : ConcertReservationRepository {
    override fun save(concertReservation: ConcertReservation): ConcertReservation =
        dataJpaConcertReservationRepository
            .save(concertReservation.toJpaEntity())
            .toDomain()
}
