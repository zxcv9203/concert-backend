package org.example.concertbackend.infrastructure.persistence.concert.reservation.repository

import org.example.concertbackend.domain.concert.reservation.ConcertReservation
import org.example.concertbackend.domain.concert.reservation.ConcertReservationRepository
import org.example.concertbackend.domain.concert.reservation.ConcertReservationStatus
import org.example.concertbackend.infrastructure.persistence.concert.reservation.mapper.toDomain
import org.example.concertbackend.infrastructure.persistence.concert.reservation.mapper.toJpaEntity
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Repository
import java.time.LocalDateTime

@Repository
class JpaConcertReservationRepository(
    private val dataJpaConcertReservationRepository: DataJpaConcertReservationRepository,
) : ConcertReservationRepository {
    override fun save(concertReservation: ConcertReservation): ConcertReservation =
        dataJpaConcertReservationRepository
            .save(concertReservation.toJpaEntity())
            .toDomain()

    override fun findExpiredReservations(now: LocalDateTime): List<ConcertReservation> =
        dataJpaConcertReservationRepository
            .findAllByExpiresAtBeforeAndStatus(now, ConcertReservationStatus.PENDING)
            .map { it.toDomain() }

    override fun update(it: ConcertReservation): ConcertReservation =
        dataJpaConcertReservationRepository
            .save(it.toJpaEntity())
            .toDomain()

    override fun findByIdAndStatus(
        id: Long,
        status: ConcertReservationStatus,
    ): ConcertReservation? =
        dataJpaConcertReservationRepository
            .findByIdAndStatus(id, status)
            ?.toDomain()

    override fun findById(id: Long): ConcertReservation? =
        dataJpaConcertReservationRepository
            .findByIdOrNull(id)
            ?.toDomain()
}
