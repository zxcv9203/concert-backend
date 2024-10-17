package org.example.concertbackend.infrastructure.persistence.concert.reservation.mapper

import org.example.concertbackend.domain.concert.reservation.ConcertReservationItem
import org.example.concertbackend.infrastructure.persistence.concert.reservation.entity.ConcertReservationItemJpaEntity

fun ConcertReservationItem.toJpaEntity() =
    ConcertReservationItemJpaEntity(
        id = id,
        concertSeatId = concertSeatId,
        concertReservationId = concertReservationId,
    )

fun ConcertReservationItemJpaEntity.toDomain() =
    ConcertReservationItem(
        id = id,
        concertSeatId = concertSeatId,
        concertReservationId = concertReservationId,
    )
