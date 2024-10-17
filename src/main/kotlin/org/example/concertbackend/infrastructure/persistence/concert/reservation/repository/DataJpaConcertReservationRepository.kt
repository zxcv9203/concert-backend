package org.example.concertbackend.infrastructure.persistence.concert.reservation.repository

import jakarta.persistence.LockModeType
import org.example.concertbackend.domain.concert.reservation.ConcertReservationStatus
import org.example.concertbackend.infrastructure.persistence.concert.reservation.entity.ConcertReservationJpaEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Lock
import java.time.LocalDateTime

interface DataJpaConcertReservationRepository : JpaRepository<ConcertReservationJpaEntity, Long> {
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    fun findAllByExpiresAtBeforeAndStatus(
        now: LocalDateTime,
        status: ConcertReservationStatus,
    ): List<ConcertReservationJpaEntity>

    fun findByIdAndStatus(
        id: Long,
        status: ConcertReservationStatus,
    ): ConcertReservationJpaEntity?
}
