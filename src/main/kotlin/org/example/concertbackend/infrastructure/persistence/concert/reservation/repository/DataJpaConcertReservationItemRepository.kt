package org.example.concertbackend.infrastructure.persistence.concert.reservation.repository

import org.example.concertbackend.infrastructure.persistence.concert.reservation.entity.ConcertReservationItemJpaEntity
import org.springframework.data.jpa.repository.JpaRepository

interface DataJpaConcertReservationItemRepository : JpaRepository<ConcertReservationItemJpaEntity, Long> {
    fun findAllByConcertReservationId(id: Long): List<ConcertReservationItemJpaEntity>
}
