package org.example.concertbackend.infrastructure.persistence.concert.reservation.repository

import org.example.concertbackend.infrastructure.persistence.concert.reservation.entity.ConcertReservationJpaEntity
import org.springframework.data.jpa.repository.JpaRepository
import java.time.LocalDateTime

interface DataJpaConcertReservationRepository : JpaRepository<ConcertReservationJpaEntity, Long> {
    fun findAllByExpiresAtBefore(now: LocalDateTime): List<ConcertReservationJpaEntity>
}
