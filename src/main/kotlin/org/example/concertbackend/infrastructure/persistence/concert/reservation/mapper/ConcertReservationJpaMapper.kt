package org.example.concertbackend.infrastructure.persistence.concert.reservation.mapper

import org.example.concertbackend.domain.concert.reservation.ConcertReservation
import org.example.concertbackend.infrastructure.persistence.concert.reservation.entity.ConcertReservationJpaEntity

fun ConcertReservation.toJpaEntity() =
    ConcertReservationJpaEntity(
        id = id,
        userId = userId,
        totalPrice = totalPrice,
        expiresAt = expiresAt,
        status = status,
    )

fun ConcertReservationJpaEntity.toDomain() =
    ConcertReservation(
        id = id,
        userId = userId,
        totalPrice = totalPrice,
        expiresAt = expiresAt,
        status = status,
    )
