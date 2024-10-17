package org.example.concertbackend.infrastructure.persistence.concert.reservation.repository

import org.example.concertbackend.domain.concert.reservation.ConcertReservationItem
import org.example.concertbackend.domain.concert.reservation.ConcertReservationItemRepository
import org.example.concertbackend.infrastructure.persistence.concert.reservation.mapper.toDomain
import org.example.concertbackend.infrastructure.persistence.concert.reservation.mapper.toJpaEntity
import org.springframework.stereotype.Repository

@Repository
class JpaConcertReservationItemRepository(
    private val dataJpaConcertReservationItemRepository: DataJpaConcertReservationItemRepository,
) : ConcertReservationItemRepository {
    override fun save(concertReservationItem: ConcertReservationItem): ConcertReservationItem =
        dataJpaConcertReservationItemRepository
            .save(concertReservationItem.toJpaEntity())
            .toDomain()

    override fun findByReservationId(id: Long): List<ConcertReservationItem> {
        return dataJpaConcertReservationItemRepository
            .findAllByConcertReservationId(id)
            .map { it.toDomain() }
    }
}
