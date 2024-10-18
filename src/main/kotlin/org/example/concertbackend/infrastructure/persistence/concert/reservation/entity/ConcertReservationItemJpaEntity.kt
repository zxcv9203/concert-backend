package org.example.concertbackend.infrastructure.persistence.concert.reservation.entity

import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table
import org.example.concertbackend.infrastructure.persistence.model.BaseTimeEntity

@Entity
@Table(name = "concert_reservation_items")
class ConcertReservationItemJpaEntity(
    val concertReservationId: Long,
    val concertSeatId: Long,
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,
) : BaseTimeEntity()
