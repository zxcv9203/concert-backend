package org.example.concertbackend.infrastructure.persistence.concert.reservation.entity

import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table
import org.example.concertbackend.domain.concert.reservation.ConcertReservationStatus
import org.example.concertbackend.infrastructure.persistence.model.BaseTimeEntity
import java.time.LocalDateTime

@Entity
@Table(name = "concert_reservations")
class ConcertReservationJpaEntity(
    val userId: Long,
    val totalPrice: Long,
    val expiresAt: LocalDateTime,
    val status: ConcertReservationStatus = ConcertReservationStatus.PENDING,
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,
) : BaseTimeEntity()
